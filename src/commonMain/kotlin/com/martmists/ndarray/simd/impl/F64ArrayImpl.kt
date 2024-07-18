package com.martmists.ndarray.simd.impl

import com.martmists.ndarray.simd.*

internal open class F64ArrayImpl internal constructor(
        override val data: DoubleArray,
        override val offset: Int,
        override val strides: IntArray,
        override val shape: IntArray,
        override val unrollDim: Int,
        override val unrollStride: Int,
        override val unrollSize: Int
) : F64Array {
    override val isFlattenable = unrollDim == nDim

    protected inline fun F64Array.unsafeIndex(r: Int, c: Int): Int {
        return offset + r * strides[0] + c * strides[1]
    }

    protected inline fun F64Array.unsafeIndex(d: Int, r: Int, c: Int): Int {
        return offset + d * strides[0] + r * strides[1] + c * strides[2]
    }

    protected inline fun F64Array.unsafeIndex(indices: IntArray): Int {
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
        return F64Array.full(shape, 0.0).also { this.copyTo(it) }
    }

    override fun copyTo(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.copyTo(b) }
    }

    override fun flatten(): F64FlatArray {
        check(isFlattenable) { "array can't be flattened" }
        return F64FlatArray.create(data, offset, unrollStride, unrollSize)
    }

    override fun slice(from: Int, to: Int, step: Int, axis: Int): F64Array {
        require(step > 0) { "slicing step must be positive, but was $step" }
        require(axis in 0 until nDim) { "axis out of bounds: $axis" }
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

    override fun sum(): Double = unrollToFlat().map { it.sum() }.sum()

    override fun min(): Double = unrollToFlat().map { it.min() }.minOrNull() ?: Double.POSITIVE_INFINITY

    override fun max(): Double = unrollToFlat().map { it.max() }.maxOrNull() ?: Double.NEGATIVE_INFINITY

    override fun product(): Double = unrollToFlat().map { it.product() }.reduce(Double::times)

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

    override fun expInPlace() {
        unrollToFlat().forEach { it.expInPlace() }
    }

    override fun expm1InPlace() {
        unrollToFlat().forEach { it.expm1InPlace() }
    }

    override fun logInPlace() {
        unrollToFlat().forEach { it.logInPlace() }
    }

    override fun log1pInPlace() {
        unrollToFlat().forEach { it.log1pInPlace() }
    }

    override fun log2InPlace() {
        unrollToFlat().forEach { it.log2InPlace() }
    }

    override fun log10InPlace() {
        unrollToFlat().forEach { it.log10InPlace() }
    }

    override fun logBaseInPlace(base: Double) {
        unrollToFlat().forEach { it.logBaseInPlace(base) }
    }

    override fun sqrtInPlace() {
        unrollToFlat().forEach { it.sqrtInPlace() }
    }

    override fun powInPlace(power: Double) {
        unrollToFlat().forEach { it.powInPlace(power) }
    }

    override fun ipowInPlace(base: Double) {
        unrollToFlat().forEach { it.ipowInPlace(base) }
    }

    override fun unaryMinusInPlace() {
        unrollToFlat().forEach { it.unaryMinusInPlace() }
    }

    override fun plusAssign(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.plusAssign(b) }
    }

    override fun plusAssign(other: Double) {
        unrollToFlat().forEach { it.plusAssign(other) }
    }

    override fun minusAssign(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.minusAssign(b) }
    }

    override fun minusAssign(other: Double) {
        unrollToFlat().forEach { it.minusAssign(other) }
    }

    override fun timesAssign(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.timesAssign(b) }
    }

    override fun timesAssign(other: Double) {
        unrollToFlat().forEach { it.timesAssign(other) }
    }

    override fun divAssign(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.divAssign(b) }
    }

    override fun divAssign(other: Double) {
        unrollToFlat().forEach { it.divAssign(other) }
    }

    override fun absInPlace() {
        unrollToFlat().forEach { it.absInPlace() }
    }

    override fun ltInPlace(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.ltInPlace(b) }
    }

    override fun ltInPlace(other: Double) {
        unrollToFlat().forEach { it.ltInPlace(other) }
    }

    override fun gtInPlace(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.gtInPlace(b) }
    }

    override fun gtInPlace(other: Double) {
        unrollToFlat().forEach { it.gtInPlace(other) }
    }

    override fun eqInPlace(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.eqInPlace(b) }
    }

    override fun eqInPlace(other: Double) {
        unrollToFlat().forEach { it.eqInPlace(other) }
    }

    override fun neqInPlace(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.neqInPlace(b) }
    }

    override fun neqInPlace(other: Double) {
        unrollToFlat().forEach { it.neqInPlace(other) }
    }

    override fun andInPlace(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.andInPlace(b) }
    }

    override fun andInPlace(other: Int) {
        unrollToFlat().forEach { it.andInPlace(other) }
    }

    override fun orInPlace(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.orInPlace(b) }
    }

    override fun orInPlace(other: Int) {
        unrollToFlat().forEach { it.orInPlace(other) }
    }

    override fun xorInPlace(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.xorInPlace(b) }
    }

    override fun xorInPlace(other: Int) {
        unrollToFlat().forEach { it.xorInPlace(other) }
    }

    override fun notInPlace() {
        unrollToFlat().forEach { it.notInPlace() }
    }

    override fun shlInPlace(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.shlInPlace(b) }
    }

    override fun shlInPlace(other: Int) {
        unrollToFlat().forEach { it.shlInPlace(other) }
    }

    override fun shrInPlace(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.shrInPlace(b) }
    }

    override fun shrInPlace(other: Int) {
        unrollToFlat().forEach { it.shrInPlace(other) }
    }

    override fun sinInPlace() {
        unrollToFlat().forEach { it.sinInPlace() }
    }

    override fun cosInPlace() {
        unrollToFlat().forEach { it.cosInPlace() }
    }

    override fun tanInPlace() {
        unrollToFlat().forEach { it.tanInPlace() }
    }

    override fun asinInPlace() {
        unrollToFlat().forEach { it.asinInPlace() }
    }

    override fun acosInPlace() {
        unrollToFlat().forEach { it.acosInPlace() }
    }

    override fun atanInPlace() {
        unrollToFlat().forEach { it.atanInPlace() }
    }

    override fun atan2InPlace(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.atan2InPlace(b) }
    }

    override fun sinhInPlace() {
        unrollToFlat().forEach { it.sinhInPlace() }
    }

    override fun coshInPlace() {
        unrollToFlat().forEach { it.coshInPlace() }
    }

    override fun tanhInPlace() {
        unrollToFlat().forEach { it.tanhInPlace() }
    }

    override fun asinhInPlace() {
        unrollToFlat().forEach { it.asinhInPlace() }
    }

    override fun acoshInPlace() {
        unrollToFlat().forEach { it.acoshInPlace() }
    }

    override fun atanhInPlace() {
        unrollToFlat().forEach { it.atanhInPlace() }
    }

    override fun hypotInPlace(other: F64Array) {
        commonUnrollToFlat(other) { a, b -> a.hypotInPlace(b) }
    }

    override fun matmul(other: F64Array): F64Array {
        check(nDim == 2) { "matmul is only supported for 2D arrays" }
        check(other.nDim == 2) { "matmul is only supported for 2D arrays" }
        check(shape[1] == other.shape[0]) {
            "matmul dimensions do not match: ${shape[1]} != ${other.shape[0]}"
        }
        val resultShape = intArrayOf(shape[0], other.shape[1])
        val result = F64Array.full(resultShape, 0.0)
        for (i in 0 until shape[0]) {
            for (j in 0 until other.shape[1]) {
                for (k in 0 until shape[1]) {
                    result[i, j] += this[i, k] * other[k, j]
                }
            }
        }
        return result
    }

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
