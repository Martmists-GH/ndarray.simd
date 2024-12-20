package com.martmists.ndarray.simd

import com.martmists.ndarray.simd.impl.create
import com.martmists.ndarray.simd.impl.unsupported
import kotlin.math.sqrt

/**
 * A 1D specialization type for [F64Array].
 */
interface F64FlatArray : F64Array {
    /**
     * @see F64Array.isFlattenable
     */
    override val isFlattenable: Boolean
        get() = true

    /**
     * @see F64Array.checkShape
     */
    override fun checkShape(other: F64Array): F64FlatArray {
        check(this === other || (other is F64FlatArray && shape[0] == other.shape[0])) {
            "operands shapes do not match: ${shape.contentToString()} vs ${other.shape.contentToString()}"
        }
        return other as F64FlatArray
    }

    /**
     * @see F64Array.flatten
     */
    override fun flatten(): F64FlatArray = this

    /**
     * 1D-specific Indexing operators
     */
    operator fun get(pos: Int): Double
    operator fun set(pos: Int, value: Double)

    /**
     * Retrieves the index of the minimum value in the array.
     *
     * @return The index of the minimum value.
     */
    fun argMin(): Int = (0 until length).minBy(::get)

    /**
     * Retrieves the index of the maximum value in the array.
     *
     * @return The index of the maximum value.
     */
    fun argMax(): Int = (0 until length).maxBy(::get)

    /**
     * Maps the array to a sequence with the given transform.
     *
     * @param transform The transform to apply to each element.
     * @return The sequence of transformed elements.
     */
    fun <T> map(transform: (Double) -> T): Sequence<T> = (0 until length).asSequence().map { transform(get(it)) }

    /**
     * Calculates the dot product of this array with another.
     *
     * @param other The other array to calculate the dot product with.
     * @return The dot product of the two arrays.
     */
    infix fun dot(other: F64Array): Double = (this * other).sum()

    /**
     * Calculates the outer product of this array with another.
     *
     * @param other The other array to calculate the outer product with.
     * @return The outer product of the two arrays.
     * @since 1.0.7
     */
    infix fun outer(other: F64FlatArray): F64Array

    /**
     * Calculates the norm of the array.
     */
    fun norm(): Double = sqrt((this * this).sum())

    /**
     * @see F64Array.copy
     */
    override fun copy(): F64FlatArray

    /**
     * @see F64Array.slice
     */
    override fun slice(from: Int, to: Int, step: Int, axis: Int): F64FlatArray

    /**
     * @see F64Array.cumSum
     */
    override fun cumSum(): F64FlatArray = copy().apply { cumSumInPlace() }

    /**
     * @see F64Array.coerce
     */
    override fun coerce(min: Double, max: Double): F64FlatArray = copy().apply { coerceInPlace(min, max) }

    /**
     * @see F64Array.transform
     */
    override fun transform(transform: (Double) -> Double): F64FlatArray = copy().apply { transformInPlace(transform) }

    /**
     * @see F64Array.zipTransform
     */
    override fun zipTransform(other: F64Array, transform: (Double, Double) -> Double): F64FlatArray = copy().apply { zipTransformInPlace(other, transform) }

    /**
     * @see F64Array.exp
     */
    override fun exp(): F64FlatArray = copy().apply { expInPlace() }

    /**
     * @see F64Array.expm1
     */
    override fun expm1(): F64FlatArray = copy().apply { expm1InPlace() }

    /**
     * @see F64Array.expBase
     */
    override fun expBase(base: Double): F64FlatArray = copy().apply { expBaseInPlace(base) }

    /**
     * @see F64Array.log
     */
    override fun log(): F64FlatArray = copy().apply { logInPlace() }

    /**
     * @see F64Array.log1p
     */
    override fun log1p(): F64FlatArray = copy().apply { log1pInPlace() }

    /**
     * @see F64Array.log2
     */
    override fun log2(): F64FlatArray = copy().apply { log2InPlace() }

    /**
     * @see F64Array.log10
     */
    override fun log10(): F64FlatArray = copy().apply { log10InPlace() }

    /**
     * @see F64Array.logBase
     */
    override fun logBase(base: Double): F64FlatArray = copy().apply { logBaseInPlace(base) }

    /**
     * @see F64Array.sqrt
     */
    override fun sqrt(): F64FlatArray = copy().apply { sqrtInPlace() }

    /**
     * @see F64Array.pow
     */
    override fun pow(power: Double): F64FlatArray = copy().apply { powInPlace(power) }

    /**
     * @see F64Array.unaryPlus
     */
    override fun unaryPlus(): F64FlatArray = this

    /**
     * @see F64Array.unaryMinus
     */
    override fun unaryMinus(): F64FlatArray = copy().apply { unaryMinusInPlace() }

    /**
     * @see F64Array.plus
     */
    override fun plus(other: F64Array): F64FlatArray = copy().apply { plusAssign(other) }

    /**
     * @see F64Array.plus
     */
    override fun plus(other: Double): F64FlatArray = copy().apply { plusAssign(other) }

    /**
     * @see F64Array.minus
     */
    override fun minus(other: F64Array): F64FlatArray = copy().apply { minusAssign(other) }

    /**
     * @see F64Array.minus
     */
    override fun minus(other: Double): F64FlatArray = copy().apply { minusAssign(other) }

    /**
     * @see F64Array.times
     */
    override fun times(other: F64Array): F64FlatArray = copy().apply { timesAssign(other) }

    /**
     * @see F64Array.times
     */
    override fun times(other: Double): F64FlatArray = copy().apply { timesAssign(other) }

    /**
     * @see F64Array.div
     */
    override fun div(other: F64Array): F64FlatArray = copy().apply { divAssign(other) }

    /**
     * @see F64Array.div
     */
    override fun div(other: Double): F64FlatArray = copy().apply { divAssign(other) }

    /**
     * @see F64Array.rem
     */
    override fun rem(other: F64Array): F64FlatArray = copy().apply { remAssign(other) }

    /**
     * @see F64Array.rem
     */
    override fun rem(other: Double): F64FlatArray = copy().apply { remAssign(other) }

    /**
     * @see F64Array.abs
     */
    override fun abs(): F64FlatArray = copy().apply { absInPlace() }

    /**
     * @see F64Array.lt
     */
    override fun lt(other: F64Array): F64FlatArray = copy().apply { ltInPlace(other) }

    /**
     * @see F64Array.lt
     */
    override fun lt(other: Double): F64FlatArray = copy().apply { ltInPlace(other) }

    /**
     * @see F64Array.gt
     */
    override fun gt(other: F64Array): F64FlatArray = copy().apply { gtInPlace(other) }

    /**
     * @see F64Array.gt
     */
    override fun gt(other: Double): F64FlatArray = copy().apply { gtInPlace(other) }

    /**
     * @see F64Array.lte
     */
    override fun lte(other: F64Array): F64FlatArray = copy().apply { lteInPlace(other) }

    /**
     * @see F64Array.lte
     */
    override fun lte(other: Double): F64FlatArray = copy().apply { lteInPlace(other) }

    /**
     * @see F64Array.gte
     */
    override fun gte(other: F64Array): F64FlatArray = copy().apply { gteInPlace(other) }

    /**
     * @see F64Array.gte
     */
    override fun gte(other: Double): F64FlatArray = copy().apply { gteInPlace(other) }

    /**
     * @see F64Array.eq
     */
    override fun eq(other: F64Array): F64FlatArray = copy().apply { eqInPlace(other) }

    /**
     * @see F64Array.eq
     */
    override fun eq(other: Double): F64FlatArray = copy().apply { eqInPlace(other) }

    /**
     * @see F64Array.neq
     */
    override fun neq(other: F64Array): F64FlatArray = copy().apply { neqInPlace(other) }

    /**
     * @see F64Array.neq
     */
    override fun neq(other: Double): F64FlatArray = copy().apply { neqInPlace(other) }

    /**
     * @see F64Array.and
     */
    override fun and(other: F64Array): F64FlatArray = copy().apply { andInPlace(other) }

    /**
     * @see F64Array.and
     */
    override fun and(other: Int): F64FlatArray = copy().apply { andInPlace(other) }

    /**
     * @see F64Array.or
     */
    override fun or(other: F64Array): F64FlatArray = copy().apply { orInPlace(other) }

    /**
     * @see F64Array.or
     */
    override fun or(other: Int): F64FlatArray = copy().apply { orInPlace(other) }

    /**
     * @see F64Array.xor
     */
    override fun xor(other: F64Array): F64FlatArray = copy().apply { xorInPlace(other) }

    /**
     * @see F64Array.xor
     */
    override fun xor(other: Int): F64FlatArray = copy().apply { xorInPlace(other) }

    /**
     * @see F64Array.not
     */
    override fun not(): F64FlatArray = copy().apply { notInPlace() }

    /**
     * @see F64Array.shl
     */
    override fun shl(other: F64Array): F64FlatArray = copy().apply { shlInPlace(other) }

    /**
     * @see F64Array.shl
     */
    override fun shl(other: Int): F64FlatArray = copy().apply { shlInPlace(other) }

    /**
     * @see F64Array.shr
     */
    override fun shr(other: F64Array): F64FlatArray = copy().apply { shrInPlace(other) }

    /**
     * @see F64Array.shr
     */
    override fun shr(other: Int): F64FlatArray = copy().apply { shrInPlace(other) }

    /**
     * @see F64Array.sin
     */
    override fun sin(): F64FlatArray = copy().apply { sinInPlace() }

    /**
     * @see F64Array.cos
     */
    override fun cos(): F64FlatArray = copy().apply { cosInPlace() }

    /**
     * @see F64Array.tan
     */
    override fun tan(): F64FlatArray = copy().apply { tanInPlace() }

    /**
     * @see F64Array.asin
     */
    override fun asin(): F64FlatArray = copy().apply { asinInPlace() }

    /**
     * @see F64Array.acos
     */
    override fun acos(): F64FlatArray = copy().apply { acosInPlace() }

    /**
     * @see F64Array.atan
     */
    override fun atan(): F64FlatArray = copy().apply { atanInPlace() }

    /**
     * @see F64Array.atan2
     */
    override fun atan2(other: F64Array): F64FlatArray = copy().apply { atan2InPlace(other) }

    /**
     * @see F64Array.sinh
     */
    override fun sinh(): F64FlatArray = copy().apply { sinhInPlace() }

    /**
     * @see F64Array.cosh
     */
    override fun cosh(): F64FlatArray = copy().apply { coshInPlace() }

    /**
     * @see F64Array.tanh
     */
    override fun tanh(): F64FlatArray = copy().apply { tanhInPlace() }

    /**
     * @see F64Array.asinh
     */
    override fun asinh(): F64FlatArray = copy().apply { asinhInPlace() }

    /**
     * @see F64Array.acosh
     */
    override fun acosh(): F64FlatArray = copy().apply { acoshInPlace() }

    /**
     * @see F64Array.atanh
     */
    override fun atanh(): F64FlatArray = copy().apply { atanhInPlace() }

    /**
     * @see F64Array.hypot
     */
    override fun hypot(other: F64Array): F64FlatArray = copy().apply { hypotInPlace(other) }

    /**
     * Performs a Real-to-Complex FFT on this array.
     * The output array is NOT normalized.
     *
     * @return The FFT result, where the second axis is [real, imag]
     * @since 1.3.0
     */
    fun fftR2C(): F64TwoAxisArray = F64Array(shape[0], 2) { i, x -> if (x == 1) 0.0 else this[i] }.apply { fftC2CInPlace() }

    companion object {
        /**
         * Creates a [F64FlatArray] from a [DoubleArray].
         */
        fun of(data: DoubleArray): F64FlatArray = F64FlatArray.create(data)
    }
}
