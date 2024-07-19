package com.martmists.ndarray.simd.impl

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64FlatArray
import com.martmists.ndarray.simd.NativeSpeedup

// TODO: Dense array for ND?
internal abstract class F64DenseFlatArrayBase(
    data: DoubleArray,
    offset: Int,
    size: Int
) : F64FlatArrayImpl(data, offset, 1, size) {
    override fun fill(init: Double) = data.fill(init, offset, offset + length)

    override fun copy(): F64FlatArray {
        val copyData = DoubleArray(length)
        data.copyInto(copyData, 0, offset, offset + length)
        return F64FlatArray.create(copyData, 0)
    }

    override fun copyTo(other: F64Array) {
        if (other is F64DenseFlatArrayBase) {
            checkShape(other)
            NativeSpeedup.vecCopy(other.data, other.offset, length, data, offset)
        } else {
            super.copyTo(other)
        }
    }

    override fun toDoubleArray(): DoubleArray {
        return data.copyOfRange(offset, offset + length)
    }

    override fun transformInPlace(transform: (Double) -> Double) {
        var dstOffset = offset
        val dstEnd = dstOffset + length
        while (dstOffset < dstEnd) {
            data[dstOffset] = transform(data[dstOffset])
            dstOffset++
        }
    }

    override fun transform(transform: (Double) -> Double): F64FlatArray {
        val dst = DoubleArray(length)
        var srcOffset = offset
        for (i in 0 until length) {
            dst[i] = transform(data[srcOffset])
            srcOffset++
        }
        return F64FlatArray.create(dst, 0, length)
    }

    override fun zipTransformInPlace(other: F64Array, transform: (Double, Double) -> Double) {
        if (other is F64DenseFlatArrayBase) {
            checkShape(other)
            if (offset == 0 && other.offset == 0) {
                for (i in 0 until length) {
                    this[i] = transform(data[i], other.data[i])
                }
            } else {
                var dstOffset = offset
                var srcOffset = other.offset
                val dstEnd = offset + length
                while (dstOffset < dstEnd) {
                    data[dstOffset] = transform(data[dstOffset], other.data[srcOffset])
                    dstOffset++
                    srcOffset++
                }
            }
        } else {
            super.zipTransformInPlace(other, transform)
        }
    }

    override fun zipTransform(other: F64Array, transform: (Double, Double) -> Double): F64FlatArray {
        if (other is F64DenseFlatArrayBase) {
            checkShape(other)
            val dst = DoubleArray(length)
            if (offset == 0 && other.offset == 0) {
                for (i in 0 until length) {
                    dst[i] = transform(data[i], other.data[i])
                }
            } else {
                var dstOffset = 0
                var srcOffset = offset
                var otherOffset = other.offset
                for (i in 0 until length) {
                    dst[dstOffset] = transform(data[srcOffset], other.data[otherOffset])
                    dstOffset++
                    srcOffset++
                    otherOffset++
                }
            }
            return F64FlatArray.create(dst, 0, length)
        } else {
            return super.zipTransform(other, transform)
        }
    }
}
