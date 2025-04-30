@file:Suppress("DEPRECATION")

package com.martmists.ndarray.simd.impl

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64FlatArray
import com.martmists.ndarray.simd.F64TwoAxisArray
import com.martmists.ndarray.simd.NativeSpeedup
import kotlin.math.min

internal fun F64Array.view0(indices: IntArray): F64Array {
    require(indices.size < nDim) { "too many indices" }
    return indices.fold(this) { m, pos -> m.view(pos) }
}

internal fun F64Array.unrollOnce(n: Int = unrollDim): Sequence<F64Array> {
    val newStrides = strides.slice(n until nDim).toIntArray()
    val newShape = shape.slice(n until nDim).toIntArray()
    val currentUnrollStride = if (n == unrollDim) unrollStride else run {
        var nonTrivialN = n - 1
        while (nonTrivialN >= 0 && shape[nonTrivialN] <= 1) nonTrivialN--
        if (nonTrivialN >= 0) strides[nonTrivialN] else 0
    }
    val currentUnrollSize = if (n == unrollDim) unrollSize else shape.slice(0 until n).toIntArray().product()

    return (0 until currentUnrollSize).asSequence().map { i ->
        F64Array.create(data, offset + currentUnrollStride * i, newStrides, newShape)
    }
}

internal fun F64Array.unrollToFlat(): Sequence<F64FlatArray> {
    if (isFlattenable) return sequenceOf(flatten())
    return unrollOnce().flatMap { it.unrollToFlat() }
}

internal fun F64Array.commonUnrollToFlat(
    other: F64Array,
    action: (F64FlatArray, F64FlatArray) -> Unit
) {
    checkShape(other)
    val commonUnrollDim = min(unrollDim, other.unrollDim)
    if (commonUnrollDim == nDim) {
        action(flatten(), other.flatten())
    } else {
        unrollOnce(commonUnrollDim).zip(other.unrollOnce(commonUnrollDim)).forEach { (a, b) ->
            a.commonUnrollToFlat(b, action)
        }
    }
}

internal data class Unroll(val dim: Int, val stride: Int, val size: Int)

internal fun calculateUnroll(strides: IntArray, shape: IntArray): Unroll {
    var prevStride = 0
    var unrollable = true
    var d = 0
    var s = 0
    for (i in strides.indices) {
        if (shape[i] == 1) {
            if (unrollable) d = i + 1
            continue
        }
        if (unrollable && (prevStride == 0 || prevStride == strides[i] * shape[i])) {
            d = i + 1
            s = strides[i]
        } else {
            unrollable = false
        }
        prevStride = strides[i]
    }
    return Unroll(d, s, shape.slice(0 until d).toIntArray().product())
}

internal fun IntArray.remove(pos: Int) = when (pos) {
    0 -> sliceArray(1..lastIndex)
    lastIndex -> sliceArray(0 until lastIndex)
    else -> sliceArray(0 until pos) + sliceArray(pos + 1..lastIndex)
}

internal inline fun unsupported(): Nothing = throw UnsupportedOperationException()

internal inline fun <T> reorderInternal(
    a: F64Array,
    indices: IntArray,
    axis: Int,
    get: (Int) -> T,
    set: (Int, T) -> Unit
) {
    require(indices.size == a.shape[axis])

    val copy = indices.copyOf()
    for (pos in 0 until a.shape[axis]) {
        val value = get(pos)
        var j = pos
        while (true) {
            val k = copy[j]
            copy[j] = j
            if (k == pos) {
                set(j, value)
                break
            } else {
                set(j, get(k))
                j = k
            }
        }
    }
}

internal fun F64Array.Companion.create(
    data: DoubleArray,
    offset: Int,
    strides: IntArray,
    shape: IntArray,
): F64Array {
    require(strides.size == shape.size) { "strides and shape size don't match" }
    require(shape.isNotEmpty()) { "empty arrays are not supported" }
    return when (shape.size) {
        1 -> {
            F64FlatArray.create(data, offset, strides.single(), shape.single())
        }
        2 -> {
            F64TwoAxisArray.create(shape[0], data, offset, strides, shape.product())
        }
        else -> {
            val (unrollDim, unrollStride, unrollSize) = calculateUnroll(strides, shape)
            F64ArrayImpl(data, offset, strides, shape, unrollDim, unrollStride, unrollSize)
        }
    }
}

internal fun F64FlatArray.Companion.create(
    data: DoubleArray,
    offset: Int = 0,
    stride: Int = 1,
    size: Int = data.size
): F64FlatArray {
    require(size > 0) { "empty arrays not supported" }
    return if (stride == 1) {
        if (size < F64Array.simdSize || !NativeSpeedup.getSimdAvailable()) {
            F64SmallDenseFlatArrayImpl(data, offset, size)
        } else {
            F64LargeDenseFlatArrayImpl(data, offset, size)
        }
    } else {
        F64FlatArrayImpl(data, offset, stride, size)
    }
}


internal fun F64TwoAxisArray.Companion.create(
    rows: Int,
    data: DoubleArray,
    offset: Int = 0,
    strides: IntArray = intArrayOf(data.size / rows, 1),
    size: Int = data.size
): F64TwoAxisArray {
    require(size > 0) { "empty arrays not supported" }
    require(rows > 0) { "number of rows must be positive" }
    require(size % rows == 0) { "data must fully cover the number of rows" }
    require(strides.size == 2) { "data must have 2 strides" }

    val shape = intArrayOf(rows, size / rows)
    val (unrollDim, unrollStride, unrollSize) = calculateUnroll(strides, shape)
    return F64TwoAxisArrayImpl(data, offset, strides, shape, unrollDim, unrollStride, unrollSize)
}

internal fun Int.reverse(): Int {
    var v = this
    v = (v and 0x55555555 shl 1) or (v ushr 1 and 0x55555555)
    v = (v and 0x33333333 shl 2) or (v ushr 2 and 0x33333333)
    v = (v and 0x0f0f0f0f shl 4) or (v ushr 4 and 0x0f0f0f0f)
    v = (v shl 24) or (v and 0xff00 shl 8) or (v ushr 8 and 0xff00) or (v ushr 24)
    return v
}
