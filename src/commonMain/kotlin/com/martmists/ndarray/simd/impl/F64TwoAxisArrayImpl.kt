@file:Suppress("DEPRECATION")

package com.martmists.ndarray.simd.impl

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64FlatArray
import com.martmists.ndarray.simd.F64TwoAxisArray
import com.martmists.ndarray.simd.NativeSpeedup
import kotlin.math.*

internal open class F64TwoAxisArrayImpl internal constructor(
    data: DoubleArray,
    offset: Int,
    strides: IntArray,
    shape: IntArray,
    unrollDim: Int,
    unrollStride: Int,
    unrollSize: Int,
) : F64ArrayImpl(data, offset, strides, shape, unrollDim, unrollStride, unrollSize), F64TwoAxisArray {
    override fun copy(): F64TwoAxisArray {
        return super<F64TwoAxisArray>.copy()
    }

    override fun slice(from: Int, to: Int, step: Int, axis: Int): F64TwoAxisArray {
        require(step > 0) { "slicing step must be positive, but was $step" }
        require(axis in 0..1) { "axis out of bounds: $axis" }
        require(from >= 0) { "slicing start index must be positive, but was $from" }
        val actualTo = if (to != -1) {
            require(to > from) { "slicing end index $to must be greater than start index $from" }
            require(to <= shape[axis]) { "slicing end index out of bounds: $to > ${shape[axis]}" }
            to
        } else {
            require(shape[axis] > from) { "slicing start index out of bounds: $from >= ${shape[axis]}" }
            shape[axis]
        }

        val sliceStrides = strides.copyOf().apply { this[axis] *= step }
        val sliceSize = (actualTo - from + step - 1) / step
        return if (axis == 0) {
            F64TwoAxisArray.create(sliceSize, data, offset + from * strides[axis], sliceStrides, sliceSize * shape[1])
        } else {
            F64TwoAxisArray.create(shape[0], data, offset + from * strides[axis], sliceStrides, shape[0] * sliceSize)
        }
    }

    override fun transpose(ax1: Int, ax2: Int): F64TwoAxisArray {
        check(ax1 in 0..1) { "axis 1 out of bounds: $ax1" }
        check(ax2 in 0..1) { "axis 2 out of bounds: $ax2" }
        check(ax1 != ax2) { "axes must be different" }

        return F64TwoAxisArray.create(shape[1], data, offset, strides.reversedArray())
    }

    override fun matmul(other: F64TwoAxisArray): F64TwoAxisArray {
        check(shape[1] == other.shape[0]) {
            "matmul dimensions do not match: ${shape[1]} != ${other.shape[0]}"
        }

        return if (other is F64TwoAxisArrayImpl && shape[1] >= F64Array.simdSize) {
            val cur = makeDenseForMatmul()
            val t = other.transpose().makeDenseForMatmul()

            val data = NativeSpeedup.vecMatMul(
                cur.data,
                cur.offset,
                cur.shape.product(),
                t.data,
                t.offset,
                shape[0],
                shape[1],
                t.shape[0],
            )

            F64TwoAxisArray.of(shape[0], data)
        } else {
            val result = F64Array.full(shape[0], other.shape[1], init=0.0)
            for (i in 0 until shape[0]) {
                for (j in 0 until other.shape[1]) {
                    for (k in 0 until shape[1]) {
                        result[i, j] += this[i, k] * other[k, j]
                    }
                }
            }
            result
        }
    }

    private fun F64TwoAxisArray.makeDenseForMatmul(): F64TwoAxisArray {
        if (shape[1] == 1 && shape.product() + offset == data.size) {
            return this
        }
        return this.copy().also {
            check(it.strides[1] == 1) { "Array copy returned invalid array!" }
        }
    }

    override fun diagonal(): F64FlatArray {
        check(shape[0] == shape[1]) { "Only square matrices are supported" }

        return F64FlatArray.create(data, offset, strides[0] + strides[1], shape[0])
    }

    internal fun minor(row: Int, col: Int): F64TwoAxisArray {
        val withoutRow = when (row) {
            0 -> slice(1, shape[0])
            shape[0] - 1 -> slice(0, row)
            else -> {
                val top = slice(0, row)
                val bottom = slice(row + 1, shape[0])
                F64Array.concat(top, bottom)
            }
        }
        val withoutCol = when (col) {
            0 -> withoutRow.slice(1, shape[1], axis = 1)
            shape[1] - 1 -> withoutRow.slice(0, shape[1] - 1, axis = 1)
            else -> {
                val left = withoutRow.slice(0, col, axis = 1)
                val right = withoutRow.slice(col + 1, shape[1], axis = 1)
                F64Array.concat(left, right, axis = 1)
            }
        }
        return withoutCol
    }

    override fun determinant(): Double {
        check(shape[0] == shape[1]) { "Only square matrices are supported" }
        check(shape[0] > 1) { "Matrix must be at least 2x2" }

        // TODO: Consider cleaning up with an F64Array
        val n = shape[0]
        val lu = Array(n) { i -> DoubleArray(n) { this[i, it] } }
        var det = 1.0

        // LU Decomposition with partial pivoting
        for (k in 0 until n) {
            var max = 0.0
            var maxRow = k
            for (i in k until n) {
                val abs = abs(lu[i][k])
                if (abs > max) {
                    max = abs
                    maxRow = i
                }
            }

            if (k != maxRow) {
                val tmp = lu[k]
                lu[k] = lu[maxRow]
                lu[maxRow] = tmp

                det *= -1
            }

            det *= lu[k][k]
            if (lu[k][k] == 0.0) return 0.0

            for (i in k + 1 until n) {
                lu[i][k] /= lu[k][k]
                for (j in k + 1 until n) {
                    lu[i][j] -= lu[i][k] * lu[k][j]
                }
            }
        }

        return det
    }

    override fun inverse(): F64TwoAxisArray {
        return when {
            shape[0] == shape[1] -> {
                if (shape[0] == 2) {
                    val det = this[0, 0] * this[1, 1] - this[0, 1] * this[1, 0]
                    val inverse = F64Array(shape[0], shape[1])
                    inverse[0, 0] = this[1, 1]
                    inverse[0, 1] = -this[0, 1]
                    inverse[1, 0] = -this[1, 0]
                    inverse[1, 1] = this[0, 0]
                    inverse *= (1.0 / det)
                    return inverse
                }

                val det = this.determinant()
                val invDet = 1.0 / det
                val res = F64Array.zeros(shape[0], shape[1])
                for (i in 0 until shape[0]) {
                    for (j in 0 until shape[1]) {
                        if ((i + j) % 2 == 0) {
                            val minorMatrix = this.minor(i, j)
                            res[j, i] = invDet * minorMatrix.determinant()
                        } else {
                            val minorMatrix = this.minor(i, j)
                            res[j, i] = -invDet * minorMatrix.determinant()
                        }
                    }
                }
                res
            }
            shape[0] < shape[1] -> {
                val t = transpose()
                return t.matmul(this.matmul(t).inverse())
            }
            else -> {
                val t = transpose()
                return t.matmul(this).inverse().matmul(t)
            }
        }
    }

    override fun eigen(): Pair<F64FlatArray, F64TwoAxisArray> {
        check(shape[0] == shape[1]) { "Only square matrices are supported" }

        var tmp = this.copy()
        val tolerance = 1e-10
        val iterations = 1000

        var total = F64Array.identity(shape[0])

        for (i in 0 until iterations) {
            val (q, r) = tmp.qr()
            tmp = r.matmul(q)
            total = total.matmul(q)
            val offDiagonal = (tmp - F64Array.diagonal(tmp.diagonal().toDoubleArray())).flatten().norm()
            if (offDiagonal < tolerance) {
                break
            }
        }

        val eigenvalues = tmp.diagonal()
        val eigenvectors = total

        return eigenvalues to eigenvectors
    }

    // Can't make this a member property to support third-party impls
    private fun F64TwoAxisArray.qr(): Pair<F64TwoAxisArray, F64TwoAxisArray> {
        var q = F64Array.identity(shape[0])
        var r = this.copy()

        for (i in 0 until shape[0] - 1) {
            val x = r.slice(i, shape[0], axis=0).view(i, axis=1).flatten()
            val e = F64Array.zeros(shape[0] - i)
            e[0] = x.norm()
            val v = (x - e).let { it / it.norm() }
            val qi = F64Array.identity(shape[0])
            val delta = v.outer(v) * 2.0

            for (a in i until shape[0]) {
                for (b in i until shape[0]) {
                    qi[a, b] -= delta[a - i, b - i]
                }
            }

            r = qi matmul r
            q = q matmul qi
        }

        return q to r
    }

    override fun fftC2CInPlace() {
        require(shape[1] == 2) { "Axis 1 should be [real, imag]" }

        val vecSize = shape[0]
        val levels = 31 - vecSize.countLeadingZeroBits()
        require(1 shl levels == vecSize) { "Length must be a power of 2, got $vecSize" }

        val cosLookup = DoubleArray(vecSize / 2) {
            cos(2 * PI * it / vecSize)
        }
        val sinLookup = DoubleArray(vecSize / 2) {
            sin(2 * PI * it / vecSize)
        }

        for (i in 0 until vecSize) {
            val j = i.reverse() ushr 32 - levels
            if (j > i) {
                var temp = this[i, 0]
                this[i, 0] = this[j, 0]
                this[j, 0] = temp
                temp = this[i, 1]
                this[i, 1] = this[j, 1]
                this[j, 1] = temp
            }
        }

        var size = 2
        while (size <= vecSize) {
            val halfSize = size / 2
            val step = vecSize / size
            var i = 0
            while (i < vecSize) {
                var j = i
                var k = 0
                while (j < i + halfSize) {
                    val l = j + halfSize
                    val tmpReal = this[l, 0] * cosLookup[k] + this[l, 1] * sinLookup[k]
                    val tmpImag = -this[l, 0] * sinLookup[k] + this[l, 1] * cosLookup[k]
                    this[l, 0] = this[j, 0] - tmpReal
                    this[l, 1] = this[j, 1] - tmpImag
                    this[j, 0] += tmpReal
                    this[j, 1] += tmpImag
                    j++
                    k += step
                }
                i += size
            }
            if (size == vecSize) {
                break
            }
            size *= 2
        }
    }

    override fun toString(maxDisplay: Int): String {
        val sb = StringBuilder()

        if (shape[0] > maxDisplay) {
            for ((i, row) in this.slice(0, maxDisplay / 2, axis = 0).along(0).withIndex()) {
                val prefix = if (i == 0) '[' else ' '
                sb.append(prefix)
                sb.append(row.toString(maxDisplay))
                sb.append(",\n")
            }
            sb.append(" ...,\n")
            val leftover = maxDisplay - maxDisplay / 2
            val secondHalf = this.slice(length - leftover, shape[0], axis = 0).along(0).toList()
            for ((i, row) in secondHalf.withIndex()) {
                val postfix = if (i == secondHalf.lastIndex) "]" else ",\n"
                sb.append(' ')
                sb.append(row.toString(maxDisplay))
                sb.append(postfix)
            }
        } else {
            for ((i, row) in this.along(0).withIndex()) {
                val prefix = if (i == 0) '[' else ' '
                val postfix = if (i == shape[0] - 1) "]" else ",\n"
                sb.append(prefix)
                sb.append(row.toString(maxDisplay))
                sb.append(postfix)
            }
        }

        return sb.toString()
    }
}
