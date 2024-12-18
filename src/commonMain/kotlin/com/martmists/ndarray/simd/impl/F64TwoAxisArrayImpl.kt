@file:Suppress("DEPRECATION")

package com.martmists.ndarray.simd.impl

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64Array.Companion.zeros
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

    override fun matmul(other: F64Array): F64TwoAxisArray {
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
            super<F64ArrayImpl>.matmul(other)
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
