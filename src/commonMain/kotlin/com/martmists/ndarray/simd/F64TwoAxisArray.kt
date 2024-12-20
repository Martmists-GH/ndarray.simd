package com.martmists.ndarray.simd

import com.martmists.ndarray.simd.impl.create
import com.martmists.ndarray.simd.impl.unsupported

/**
 * A 2D specialization type for [F64Array].
 * @since 1.2.0
 */
interface F64TwoAxisArray : F64Array {
    /**
     * @see F64Array.copy
     */
    override fun copy(): F64TwoAxisArray = F64Array.zeros(shape[0], shape[1]).also(this::copyTo)

    /**
     * @see F64Array.slice
     */
    override fun slice(from: Int, to: Int, step: Int, axis: Int): F64TwoAxisArray

    /**
     * @see F64Array.transpose
     */
    override fun transpose(ax1: Int, ax2: Int): F64TwoAxisArray

    /**
     * @see F64Array.cumSum
     */
    override fun cumSum(): F64TwoAxisArray = copy().apply { cumSumInPlace() }

    /**
     * @see F64Array.coerce
     */
    override fun coerce(min: Double, max: Double): F64TwoAxisArray = copy().apply { coerceInPlace(min, max) }

    /**
     * @see F64Array.transform
     */
    override fun transform(transform: (Double) -> Double): F64TwoAxisArray = copy().apply { transformInPlace(transform) }

    /**
     * @see F64Array.zipTransform
     */
    override fun zipTransform(other: F64Array, transform: (Double, Double) -> Double): F64TwoAxisArray = copy().apply { zipTransformInPlace(other, transform) }

    /**
     * @see F64Array.exp
     */
    override fun exp(): F64TwoAxisArray = copy().apply { expInPlace() }

    /**
     * @see F64Array.expm1
     */
    override fun expm1(): F64TwoAxisArray = copy().apply { expm1InPlace() }

    /**
     * @see F64Array.expBase
     */
    override fun expBase(base: Double): F64TwoAxisArray = copy().apply { expBaseInPlace(base) }

    /**
     * @see F64Array.log
     */
    override fun log(): F64TwoAxisArray = copy().apply { logInPlace() }

    /**
     * @see F64Array.log1p
     */
    override fun log1p(): F64TwoAxisArray = copy().apply { log1pInPlace() }

    /**
     * @see F64Array.log2
     */
    override fun log2(): F64TwoAxisArray = copy().apply { log2InPlace() }

    /**
     * @see F64Array.log10
     */
    override fun log10(): F64TwoAxisArray = copy().apply { log10InPlace() }

    /**
     * @see F64Array.logBase
     */
    override fun logBase(base: Double): F64TwoAxisArray = copy().apply { logBaseInPlace(base) }

    /**
     * @see F64Array.sqrt
     */
    override fun sqrt(): F64TwoAxisArray = copy().apply { sqrtInPlace() }

    /**
     * @see F64Array.pow
     */
    override fun pow(power: Double): F64TwoAxisArray = copy().apply { powInPlace(power) }

    /**
     * @see F64Array.unaryPlus
     */
    override fun unaryPlus(): F64TwoAxisArray = this

    /**
     * @see F64Array.unaryMinus
     */
    override fun unaryMinus(): F64TwoAxisArray = copy().apply { unaryMinusInPlace() }

    /**
     * @see F64Array.plus
     */
    override fun plus(other: F64Array): F64TwoAxisArray = copy().apply { plusAssign(other) }

    /**
     * @see F64Array.plus
     */
    override fun plus(other: Double): F64TwoAxisArray = copy().apply { plusAssign(other) }

    /**
     * @see F64Array.minus
     */
    override fun minus(other: F64Array): F64TwoAxisArray = copy().apply { minusAssign(other) }

    /**
     * @see F64Array.minus
     */
    override fun minus(other: Double): F64TwoAxisArray = copy().apply { minusAssign(other) }

    /**
     * @see F64Array.times
     */
    override fun times(other: F64Array): F64TwoAxisArray = copy().apply { timesAssign(other) }

    /**
     * @see F64Array.times
     */
    override fun times(other: Double): F64TwoAxisArray = copy().apply { timesAssign(other) }

    /**
     * @see F64Array.div
     */
    override fun div(other: F64Array): F64TwoAxisArray = copy().apply { divAssign(other) }

    /**
     * @see F64Array.div
     */
    override fun div(other: Double): F64TwoAxisArray = copy().apply { divAssign(other) }

    /**
     * @see F64Array.rem
     */
    override fun rem(other: F64Array): F64TwoAxisArray = copy().apply { remAssign(other) }

    /**
     * @see F64Array.rem
     */
    override fun rem(other: Double): F64TwoAxisArray = copy().apply { remAssign(other) }

    /**
     * @see F64Array.abs
     */
    override fun abs(): F64TwoAxisArray = copy().apply { absInPlace() }

    /**
     * @see F64Array.lt
     */
    override fun lt(other: F64Array): F64TwoAxisArray = copy().apply { ltInPlace(other) }

    /**
     * @see F64Array.lt
     */
    override fun lt(other: Double): F64TwoAxisArray = copy().apply { ltInPlace(other) }

    /**
     * @see F64Array.gt
     */
    override fun gt(other: F64Array): F64TwoAxisArray = copy().apply { gtInPlace(other) }

    /**
     * @see F64Array.gt
     */
    override fun gt(other: Double): F64TwoAxisArray = copy().apply { gtInPlace(other) }

    /**
     * @see F64Array.lte
     */
    override fun lte(other: F64Array): F64TwoAxisArray = copy().apply { lteInPlace(other) }

    /**
     * @see F64Array.lte
     */
    override fun lte(other: Double): F64TwoAxisArray = copy().apply { lteInPlace(other) }

    /**
     * @see F64Array.gte
     */
    override fun gte(other: F64Array): F64TwoAxisArray = copy().apply { gteInPlace(other) }

    /**
     * @see F64Array.gte
     */
    override fun gte(other: Double): F64TwoAxisArray = copy().apply { gteInPlace(other) }

    /**
     * @see F64Array.eq
     */
    override fun eq(other: F64Array): F64TwoAxisArray = copy().apply { eqInPlace(other) }

    /**
     * @see F64Array.eq
     */
    override fun eq(other: Double): F64TwoAxisArray = copy().apply { eqInPlace(other) }

    /**
     * @see F64Array.neq
     */
    override fun neq(other: F64Array): F64TwoAxisArray = copy().apply { neqInPlace(other) }

    /**
     * @see F64Array.neq
     */
    override fun neq(other: Double): F64TwoAxisArray = copy().apply { neqInPlace(other) }

    /**
     * @see F64Array.and
     */
    override fun and(other: F64Array): F64TwoAxisArray = copy().apply { andInPlace(other) }

    /**
     * @see F64Array.and
     */
    override fun and(other: Int): F64TwoAxisArray = copy().apply { andInPlace(other) }

    /**
     * @see F64Array.or
     */
    override fun or(other: F64Array): F64TwoAxisArray = copy().apply { orInPlace(other) }

    /**
     * @see F64Array.or
     */
    override fun or(other: Int): F64TwoAxisArray = copy().apply { orInPlace(other) }

    /**
     * @see F64Array.xor
     */
    override fun xor(other: F64Array): F64TwoAxisArray = copy().apply { xorInPlace(other) }

    /**
     * @see F64Array.xor
     */
    override fun xor(other: Int): F64TwoAxisArray = copy().apply { xorInPlace(other) }

    /**
     * @see F64Array.not
     */
    override fun not(): F64TwoAxisArray = copy().apply { notInPlace() }

    /**
     * @see F64Array.shl
     */
    override fun shl(other: F64Array): F64TwoAxisArray = copy().apply { shlInPlace(other) }

    /**
     * @see F64Array.shl
     */
    override fun shl(other: Int): F64TwoAxisArray = copy().apply { shlInPlace(other) }

    /**
     * @see F64Array.shr
     */
    override fun shr(other: F64Array): F64TwoAxisArray = copy().apply { shrInPlace(other) }

    /**
     * @see F64Array.shr
     */
    override fun shr(other: Int): F64TwoAxisArray = copy().apply { shrInPlace(other) }

    /**
     * @see F64Array.sin
     */
    override fun sin(): F64TwoAxisArray = copy().apply { sinInPlace() }

    /**
     * @see F64Array.cos
     */
    override fun cos(): F64TwoAxisArray = copy().apply { cosInPlace() }

    /**
     * @see F64Array.tan
     */
    override fun tan(): F64TwoAxisArray = copy().apply { tanInPlace() }

    /**
     * @see F64Array.asin
     */
    override fun asin(): F64TwoAxisArray = copy().apply { asinInPlace() }

    /**
     * @see F64Array.acos
     */
    override fun acos(): F64TwoAxisArray = copy().apply { acosInPlace() }

    /**
     * @see F64Array.atan
     */
    override fun atan(): F64TwoAxisArray = copy().apply { atanInPlace() }

    /**
     * @see F64Array.atan2
     */
    override fun atan2(other: F64Array): F64TwoAxisArray = copy().apply { atan2InPlace(other) }

    /**
     * @see F64Array.sinh
     */
    override fun sinh(): F64TwoAxisArray = copy().apply { sinhInPlace() }

    /**
     * @see F64Array.cosh
     */
    override fun cosh(): F64TwoAxisArray = copy().apply { coshInPlace() }

    /**
     * @see F64Array.tanh
     */
    override fun tanh(): F64TwoAxisArray = copy().apply { tanhInPlace() }

    /**
     * @see F64Array.asinh
     */
    override fun asinh(): F64TwoAxisArray = copy().apply { asinhInPlace() }

    /**
     * @see F64Array.acosh
     */
    override fun acosh(): F64TwoAxisArray = copy().apply { acoshInPlace() }

    /**
     * @see F64Array.atanh
     */
    override fun atanh(): F64TwoAxisArray = copy().apply { atanhInPlace() }

    /**
     * @see F64Array.hypot
     */
    override fun hypot(other: F64Array): F64TwoAxisArray = copy().apply { hypotInPlace(other) }

    /**
     * Returns the diagonal of the array.
     *
     * @return the diagonal
     */
    fun diagonal(): F64FlatArray

    /**
     * Returns the determination of this matrix.
     *
     * @return the determinant
     * @since 1.1.1
     */
    fun determinant(): Double

    /**
     * Returns the inverse matrix.
     * Note: for MxN matrices, it assumes a full-rank matrix.
     *
     * @return the inverse matrix
     * @since 1.1.1
     */
    fun inverse(): F64TwoAxisArray

    /**
     * Computes the matrix multiplication of this array with another array.
     * This requires `this.shape[1] == other.shape[0]`
     */
    infix fun matmul(other: F64TwoAxisArray): F64TwoAxisArray

    /**
     * Calculates the eigenvalues and eigenvectors of this [F64TwoAxisArray] using the QR algorithm.
     *
     * @return a pair of eigenvalues as [F64FlatArray] and eigenvectors as [F64TwoAxisArray].
     * @since 1.2.0
     */
    fun eigen(): Pair<F64FlatArray, F64TwoAxisArray>

    /**
     * Performs a Complex-to-Complex FFT on this array in-place.
     * The second axis has to be [real, imag].
     * The resulting array is not normalized.
     *
     * @since 1.3.0
     */
    fun fftC2CInPlace()

    /**
     * Performs a Complex-to-Complex FFT on this array.
     * The second axis has to be [real, imag].
     * The resulting array is not normalized.
     *
     * @return the FFT result
     * @since 1.3.0
     */
    fun fftC2C() = copy().apply { fftC2CInPlace() }

    companion object {
        /**
         * Creates a [F64TwoAxisArray] from a [DoubleArray].
         */
        fun of(rows: Int, data: DoubleArray): F64TwoAxisArray = F64TwoAxisArray.create(rows, data)
    }
}
