@file:Suppress("DEPRECATION")

package com.martmists.ndarray.simd.impl

import com.martmists.ndarray.simd.*
import kotlin.math.abs

internal open class F64ArrayImpl internal constructor(
    @get:Deprecated("This should not be accessed directly")
    final override val data: DoubleArray,
    @get:Deprecated("This should not be accessed directly")
    final override val offset: Int,
    @get:Deprecated("This should not be accessed directly")
    final override val strides: IntArray,
    final override val shape: IntArray,
    @get:Deprecated("This should not be accessed directly")
    final override val unrollDim: Int,
    @get:Deprecated("This should not be accessed directly")
    final override val unrollStride: Int,
    @get:Deprecated("This should not be accessed directly")
    final override val unrollSize: Int
) : F64Array {
    override val isFlattenable = unrollDim == nDim

    internal inline fun F64Array.unsafeIndex(r: Int, c: Int): Int {
        return offset + r * strides[0] + c * strides[1]
    }

    internal inline fun F64Array.unsafeIndex(d: Int, r: Int, c: Int): Int {
        return offset + d * strides[0] + r * strides[1] + c * strides[2]
    }

    internal inline fun F64Array.unsafeIndex(indices: IntArray): Int {
        return strides.foldIndexed(offset) { i, acc, stride -> acc + indices[i] * stride }
    }

    override fun get(vararg indices: Int): Double {
        check(indices.size == nDim) { "broadcasting get is not supported" }
        for (d in 0 until nDim) {
            checkIndex("index", indices[d], shape[d])
        }
        return data[unsafeIndex(indices)]
    }

    override fun get(r: Int, c: Int): Double {
        check(nDim == 2) { "broadcasting get is not supported" }
        checkIndex("row", r, shape[0])
        checkIndex("column", c, shape[1])
        return data[unsafeIndex(r, c)]
    }

    override fun get(d: Int, r: Int, c: Int): Double {
        check(nDim == 3) { "broadcasting get is not supported" }
        checkIndex("depth", d, shape[0])
        checkIndex("row", r, shape[1])
        checkIndex("column", c, shape[2])
        return data[unsafeIndex(d, r, c)]
    }

    override fun set(vararg indices: Int, value: Double) {
        check(indices.size == nDim) { "broadcasting set is not supported" }
        for (d in 0 until nDim) {
            checkIndex("index", indices[d], shape[d])
        }
        data[unsafeIndex(indices)] = value
    }

    override operator fun set(r: Int, c: Int, value: Double) {
        check(nDim == 2) { "broadcasting set is not supported" }
        checkIndex("row", r, shape[0])
        checkIndex("column", c, shape[1])
        data[unsafeIndex(r, c)] = value
    }

    override operator fun set(d: Int, r: Int, c: Int, value: Double) {
        check(nDim == 3) { "broadcasting set is not supported" }
        checkIndex("depth", d, shape[0])
        checkIndex("row", r, shape[1])
        checkIndex("column", c, shape[2])
        data[unsafeIndex(d, r, c)] = value
    }

    override fun view(index: Int, axis: Int): F64Array {
        checkIndex("axis", axis, nDim)
        checkIndex("index", index, shape[axis])
        return F64Array.create(
            data, offset + strides[axis] * index,
            strides.remove(axis), shape.remove(axis)
        )
    }

    override val V: Viewer by lazy(LazyThreadSafetyMode.PUBLICATION) { ViewerImpl(this) }

    private class ViewerImpl(private val a: F64Array) : Viewer {
        override fun get(vararg indices: Int): F64Array = a.view0(indices)

        override fun get(any: _I, c: Int): F64Array = a.view(c, axis=1)

        override fun set(vararg indices: Int, other: F64Array) {
            other.copyTo(a.view0(indices))
        }

        override fun set(vararg indices: Int, init: Double) {
            a.view0(indices).fill(init)
        }

        override fun set(any: _I, other: F64Array) {
            other.copyTo(a)
        }

        override fun set(any: _I, other: Double) {
            a.fill(other)
        }

        override fun set(any: _I, c: Int, other: F64Array) {
            other.copyTo(a.view(c, axis=1))
        }

        override fun set(any: _I, c: Int, init: Double) {
            a.view(c, axis=1).fill(init)
        }
    }

    override fun copy(): F64Array {
        return F64Array.zeros(*shape).also(this::copyTo)
    }

    override fun copyTo(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::copyTo)
    }

    override fun flatten(): F64FlatArray {
        check(isFlattenable) { "array can't be flattened" }
        return F64FlatArray.create(data, offset, unrollStride, unrollSize)
    }

    override fun slice(from: Int, to: Int, step: Int, axis: Int): F64Array {
        checkAxis(axis)
        require(step > 0) { "slicing step must be positive, but was $step" }
        require(from >= 0) { "slicing start index must be positive, but was $from" }
        val actualTo = if (to != -1) {
            require(to > from) { "slicing end index $to must be greater than start index $from" }
            check(to <= shape[axis]) { "slicing end index out of bounds: $to > ${shape[axis]}" }
            to
        } else {
            check(shape[axis] > from) { "slicing start index out of bounds: $from >= ${shape[axis]}" }
            shape[axis]
        }

        val sliceStrides = strides.copyOf().apply { this[axis] *= step }
        val sliceShape = shape.copyOf().apply {
            this[axis] = (actualTo - from + step - 1) / step
        }
        return F64Array.create(data, offset + from * strides[axis], sliceStrides, sliceShape)
    }

    override fun contains(other: Double): Boolean = unrollToFlat().any { it.contains(other) }

    override fun fill(value: Double) = flatten().fill(value)

    override fun reorder(indices: IntArray, axis: Int) {
        reorderInternal(
            this, indices, axis,
            get = { pos -> view(pos, axis).copy() },
            set = { pos, value -> value.copyTo(view(pos, axis)) }
        )
    }

    override fun transpose(ax1: Int, ax2: Int): F64Array {
        check(ax1 in 0 until nDim) { "axis 1 out of bounds: $ax1" }
        check(ax2 in 0 until nDim) { "axis 2 out of bounds: $ax2" }
        check(ax1 != ax2) { "axes must be different" }

        val newStrides = strides.copyOf()
        newStrides[ax1] = strides[ax2]
        newStrides[ax2] = strides[ax1]

        val newShape = shape.copyOf()
        newShape[ax1] = shape[ax2]
        newShape[ax2] = shape[ax1]

        return F64Array.create(data, offset, newStrides, newShape)
    }

    override fun sum(): Double = unrollToFlat().map(F64FlatArray::sum).sum()

    override fun min(): Double = unrollToFlat().map(F64FlatArray::min).minOrNull() ?: Double.POSITIVE_INFINITY

    override fun max(): Double = unrollToFlat().map(F64FlatArray::max).maxOrNull() ?: Double.NEGATIVE_INFINITY

    override fun product(): Double = unrollToFlat().map(F64FlatArray::product).reduce(Double::times)

    override fun coerceInPlace(min: Double, max: Double) {
        unrollToFlat().forEach { it.coerceInPlace(min, max) }
    }

    override fun transformInPlace(transform: (Double) -> Double) {
        unrollToFlat().forEach { it.transformInPlace(transform) }
    }

    override fun zipTransformInPlace(other: F64Array, transform: (Double, Double) -> Double) {
        commonUnrollToFlat(other) { a, b -> a.zipTransformInPlace(b, transform) }
    }

    override fun <T> fold(initial: T, operation: (acc: T, Double) -> T): T {
        if (isFlattenable) {
            return flatten().fold(initial, operation)
        }
        return unrollToFlat().fold(initial) { acc, f64FlatArray -> f64FlatArray.fold(acc, operation) }
    }

    override fun reduce(operation: (Double, Double) -> Double): Double {
        if (isFlattenable) {
            return flatten().reduce(operation)
        }
        val sequence = unrollToFlat()
        val initial = sequence.first().reduce(operation)
        return sequence.drop(1).fold(initial) { acc, f64FlatArray -> f64FlatArray.fold(acc, operation) }
    }

    override fun reduce(axis: Int, operation: (Double, Double) -> Double): F64Array {
        checkAxis(axis)

        val items = along(axis)
        val base = items.first().copy()
        for (next in items) {
            base.zipTransformInPlace(next, operation)
        }
        return base
    }

    override fun scan(operation: (Double, Double) -> Double) {
        if (isFlattenable) {
            return flatten().scan(operation)
        }

        var tmp: Double? = null
        transformInPlace {
            if (tmp == null) {
                tmp = it
                it
            } else {
                tmp = operation(tmp!!, it)
                tmp!!
            }
        }
    }

    override fun scan(axis: Int, operation: (Double, Double) -> Double) {
        checkAxis(axis)
        val items = along(axis)
        var tmp = items.first().copy()
        for (next in items) {
            next.zipTransformInPlace(tmp, operation)
            tmp = next
        }
    }

    override fun expInPlace() {
        unrollToFlat().forEach(F64FlatArray::expInPlace)
    }

    override fun expm1InPlace() {
        unrollToFlat().forEach(F64FlatArray::expm1InPlace)
    }

    override fun logInPlace() {
        unrollToFlat().forEach(F64FlatArray::logInPlace)
    }

    override fun log1pInPlace() {
        unrollToFlat().forEach(F64FlatArray::log1pInPlace)
    }

    override fun log2InPlace() {
        unrollToFlat().forEach(F64FlatArray::log2InPlace)
    }

    override fun log10InPlace() {
        unrollToFlat().forEach(F64FlatArray::log10InPlace)
    }

    override fun logBaseInPlace(base: Double) {
        unrollToFlat().forEach { it.logBaseInPlace(base) }
    }

    override fun sqrtInPlace() {
        unrollToFlat().forEach(F64FlatArray::sqrtInPlace)
    }

    override fun powInPlace(power: Double) {
        unrollToFlat().forEach { it.powInPlace(power) }
    }

    override fun expBaseInPlace(base: Double) {
        unrollToFlat().forEach { it.expBaseInPlace(base) }
    }

    override fun unaryMinusInPlace() {
        unrollToFlat().forEach(F64FlatArray::unaryMinusInPlace)
    }

    override fun plusAssign(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::plusAssign)
    }

    override fun plusAssign(other: Double) {
        unrollToFlat().forEach { it.plusAssign(other) }
    }

    override fun minusAssign(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::minusAssign)
    }

    override fun minusAssign(other: Double) {
        unrollToFlat().forEach { it.minusAssign(other) }
    }

    override fun timesAssign(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::timesAssign)
    }

    override fun timesAssign(other: Double) {
        unrollToFlat().forEach { it.timesAssign(other) }
    }

    override fun divAssign(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::divAssign)
    }

    override fun divAssign(other: Double) {
        unrollToFlat().forEach { it.divAssign(other) }
    }

    override fun remAssign(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::divAssign)
    }

    override fun remAssign(other: Double) {
        unrollToFlat().forEach { it.remAssign(other) }
    }

    override fun absInPlace() {
        unrollToFlat().forEach(F64FlatArray::absInPlace)
    }

    override fun ltInPlace(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::ltInPlace)
    }

    override fun ltInPlace(other: Double) {
        unrollToFlat().forEach { it.ltInPlace(other) }
    }

    override fun lteInPlace(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::lteInPlace)
    }

    override fun lteInPlace(other: Double) {
        unrollToFlat().forEach { it.lteInPlace(other) }
    }

    override fun gtInPlace(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::gtInPlace)
    }

    override fun gtInPlace(other: Double) {
        unrollToFlat().forEach { it.gtInPlace(other) }
    }

    override fun gteInPlace(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::gteInPlace)
    }

    override fun gteInPlace(other: Double) {
        unrollToFlat().forEach { it.gteInPlace(other) }
    }

    override fun eqInPlace(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::eqInPlace)
    }

    override fun eqInPlace(other: Double) {
        unrollToFlat().forEach { it.eqInPlace(other) }
    }

    override fun neqInPlace(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::neqInPlace)
    }

    override fun neqInPlace(other: Double) {
        unrollToFlat().forEach { it.neqInPlace(other) }
    }

    override fun isNanInPlace() {
        unrollToFlat().forEach(F64FlatArray::isNanInPlace)
    }

    override fun isInfInPlace() {
        unrollToFlat().forEach(F64FlatArray::isInfInPlace)
    }

    override fun isFiniteInPlace() {
        unrollToFlat().forEach(F64FlatArray::isFiniteInPlace)
    }

    override fun andInPlace(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::andInPlace)
    }

    override fun andInPlace(other: Int) {
        unrollToFlat().forEach { it.andInPlace(other) }
    }

    override fun orInPlace(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::orInPlace)
    }

    override fun orInPlace(other: Int) {
        unrollToFlat().forEach { it.orInPlace(other) }
    }

    override fun xorInPlace(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::xorInPlace)
    }

    override fun xorInPlace(other: Int) {
        unrollToFlat().forEach { it.xorInPlace(other) }
    }

    override fun notInPlace() {
        unrollToFlat().forEach(F64FlatArray::notInPlace)
    }

    override fun shlInPlace(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::shlInPlace)
    }

    override fun shlInPlace(other: Int) {
        unrollToFlat().forEach { it.shlInPlace(other) }
    }

    override fun shrInPlace(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::shrInPlace)
    }

    override fun shrInPlace(other: Int) {
        unrollToFlat().forEach { it.shrInPlace(other) }
    }

    override fun floorInPlace() {
        unrollToFlat().forEach(F64FlatArray::floorInPlace)
    }

    override fun ceilInPlace() {
        unrollToFlat().forEach(F64FlatArray::ceilInPlace)
    }

    override fun truncInPlace() {
        unrollToFlat().forEach(F64FlatArray::truncInPlace)
    }

    override fun roundInPlace() {
        unrollToFlat().forEach(F64FlatArray::roundInPlace)
    }

    override fun sinInPlace() {
        unrollToFlat().forEach(F64FlatArray::sinInPlace)
    }

    override fun cosInPlace() {
        unrollToFlat().forEach(F64FlatArray::cosInPlace)
    }

    override fun tanInPlace() {
        unrollToFlat().forEach(F64FlatArray::tanInPlace)
    }

    override fun asinInPlace() {
        unrollToFlat().forEach(F64FlatArray::asinInPlace)
    }

    override fun acosInPlace() {
        unrollToFlat().forEach(F64FlatArray::acosInPlace)
    }

    override fun atanInPlace() {
        unrollToFlat().forEach(F64FlatArray::atanInPlace)
    }

    override fun atan2InPlace(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::atan2InPlace)
    }

    override fun sinhInPlace() {
        unrollToFlat().forEach(F64FlatArray::sinhInPlace)
    }

    override fun coshInPlace() {
        unrollToFlat().forEach(F64FlatArray::coshInPlace)
    }

    override fun tanhInPlace() {
        unrollToFlat().forEach(F64FlatArray::tanhInPlace)
    }

    override fun asinhInPlace() {
        unrollToFlat().forEach(F64FlatArray::asinhInPlace)
    }

    override fun acoshInPlace() {
        unrollToFlat().forEach(F64FlatArray::acoshInPlace)
    }

    override fun atanhInPlace() {
        unrollToFlat().forEach(F64FlatArray::atanhInPlace)
    }

    override fun hypotInPlace(other: F64Array) {
        commonUnrollToFlat(other, F64FlatArray::hypotInPlace)
    }

    override fun toString(
        maxDisplay: Int,
    ): String {
        val sb = StringBuilder()
        sb.append('[')
        if (maxDisplay < length) {
            for (r in 0 until maxDisplay / 2) {
                sb.append(V[r].toString(maxDisplay)).append(", ")
            }

            sb.append("..., ")

            val leftover = maxDisplay - maxDisplay / 2
            for (r in length - leftover until length) {
                sb.append(V[r].toString(maxDisplay))
                if (r < length - 1) {
                    sb.append(", ")
                }
            }
        } else {
            for (r in 0 until length) {
                sb.append(V[r].toString(maxDisplay))
                if (r < length - 1) {
                    sb.append(", ")
                }
            }
        }

        sb.append(']')
        return sb.toString()
    }

    override fun toString() = toString(8)

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is F64Array -> false
        !shape.contentEquals(other.shape) -> false
        else -> (0 until length).all { view(it) == other.view(it) }
    }

    override fun hashCode(): Int = (0 until length).fold(1) { acc, r ->
        31 * acc + view(r).hashCode()
    }
}
