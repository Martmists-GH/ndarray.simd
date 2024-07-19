package com.martmists.ndarray.simd

import com.martmists.ndarray.simd.impl.create
import com.martmists.ndarray.simd.impl.unsupported

/**
 * A 1D specialization type for [F64Array].
 */
interface F64FlatArray : F64Array {
    override val isFlattenable: Boolean
        get() = true

    override fun checkShape(other: F64Array): F64FlatArray {
        check(this === other || (other is F64FlatArray && shape[0] == other.shape[0])) {
            "operands shapes do not match: ${shape.contentToString()} vs ${other.shape.contentToString()}"
        }
        return other as F64FlatArray
    }

    override fun flatten(): F64FlatArray = this

    operator fun get(pos: Int): Double
    operator fun set(pos: Int, value: Double)

    fun argMin(): Int = (0 until length).minBy(::get)
    fun argMax(): Int = (0 until length).maxBy(::get)
    fun <T> map(transform: (Double) -> T): Sequence<T> = (0 until length).asSequence().map { transform(get(it)) }

    override fun copy(): F64FlatArray
    override fun slice(from: Int, to: Int, step: Int, axis: Int): F64FlatArray
    override fun cumSum(): F64FlatArray = copy().apply { cumSumInPlace() }
    override fun coerce(min: Double, max: Double): F64FlatArray = copy().apply { coerceInPlace(min, max) }
    override fun transform(transform: (Double) -> Double): F64FlatArray = copy().apply { transformInPlace(transform) }
    override fun zipTransform(other: F64Array, transform: (Double, Double) -> Double): F64FlatArray = copy().apply { zipTransformInPlace(other, transform) }
    override fun exp(): F64FlatArray = copy().apply { expInPlace() }
    override fun expm1(): F64FlatArray = copy().apply { expm1InPlace() }
    override fun log(): F64FlatArray = copy().apply { logInPlace() }
    override fun log1p(): F64FlatArray = copy().apply { log1pInPlace() }
    override fun log2(): F64FlatArray = copy().apply { log2InPlace() }
    override fun log10(): F64FlatArray = copy().apply { log10InPlace() }
    override fun logBase(base: Double): F64FlatArray = copy().apply { logBaseInPlace(base) }
    override fun sqrt(): F64FlatArray = copy().apply { sqrtInPlace() }
    override fun pow(power: Double): F64FlatArray = copy().apply { powInPlace(power) }
    override fun ipow(base: Double): F64FlatArray = copy().apply { ipowInPlace(base) }
    override fun unaryPlus(): F64FlatArray = this
    override fun unaryMinus(): F64FlatArray = copy().apply { unaryMinusInPlace() }
    override fun plus(other: F64Array): F64FlatArray = copy().apply { plusAssign(other) }
    override fun plus(other: Double): F64FlatArray = copy().apply { plusAssign(other) }
    override fun minus(other: F64Array): F64FlatArray = copy().apply { minusAssign(other) }
    override fun minus(other: Double): F64FlatArray = copy().apply { minusAssign(other) }
    override fun times(other: F64Array): F64FlatArray = copy().apply { timesAssign(other) }
    override fun times(other: Double): F64FlatArray = copy().apply { timesAssign(other) }
    override fun div(other: F64Array): F64FlatArray = copy().apply { divAssign(other) }
    override fun div(other: Double): F64FlatArray = copy().apply { divAssign(other) }
    override fun abs(): F64FlatArray = copy().apply { absInPlace() }
    override fun lt(other: F64Array): F64FlatArray = copy().apply { ltInPlace(other) }
    override fun lt(other: Double): F64FlatArray = copy().apply { ltInPlace(other) }
    override fun gt(other: F64Array): F64FlatArray = copy().apply { gtInPlace(other) }
    override fun gt(other: Double): F64FlatArray = copy().apply { gtInPlace(other) }
    override fun lte(other: F64Array): F64FlatArray = copy().apply { lteInPlace(other) }
    override fun lte(other: Double): F64FlatArray = copy().apply { lteInPlace(other) }
    override fun gte(other: F64Array): F64FlatArray = copy().apply { gteInPlace(other) }
    override fun gte(other: Double): F64FlatArray = copy().apply { gteInPlace(other) }
    override fun eq(other: F64Array): F64FlatArray = copy().apply { eqInPlace(other) }
    override fun eq(other: Double): F64FlatArray = copy().apply { eqInPlace(other) }
    override fun neq(other: F64Array): F64FlatArray = copy().apply { neqInPlace(other) }
    override fun neq(other: Double): F64FlatArray = copy().apply { neqInPlace(other) }
    override fun and(other: F64Array): F64FlatArray = copy().apply { andInPlace(other) }
    override fun and(other: Int): F64FlatArray = copy().apply { andInPlace(other) }
    override fun or(other: F64Array): F64FlatArray = copy().apply { orInPlace(other) }
    override fun or(other: Int): F64FlatArray = copy().apply { orInPlace(other) }
    override fun xor(other: F64Array): F64FlatArray = copy().apply { xorInPlace(other) }
    override fun xor(other: Int): F64FlatArray = copy().apply { xorInPlace(other) }
    override fun not(): F64FlatArray = copy().apply { notInPlace() }
    override fun shl(other: F64Array): F64FlatArray = copy().apply { shlInPlace(other) }
    override fun shl(other: Int): F64FlatArray = copy().apply { shlInPlace(other) }
    override fun shr(other: F64Array): F64FlatArray = copy().apply { shrInPlace(other) }
    override fun shr(other: Int): F64FlatArray = copy().apply { shrInPlace(other) }
    override fun sin(): F64FlatArray = copy().apply { sinInPlace() }
    override fun cos(): F64FlatArray = copy().apply { cosInPlace() }
    override fun tan(): F64FlatArray = copy().apply { tanInPlace() }
    override fun asin(): F64FlatArray = copy().apply { asinInPlace() }
    override fun acos(): F64FlatArray = copy().apply { acosInPlace() }
    override fun atan(): F64FlatArray = copy().apply { atanInPlace() }
    override fun atan2(other: F64Array): F64FlatArray = copy().apply { atan2InPlace(other) }
    override fun sinh(): F64FlatArray = copy().apply { sinhInPlace() }
    override fun cosh(): F64FlatArray = copy().apply { coshInPlace() }
    override fun tanh(): F64FlatArray = copy().apply { tanhInPlace() }
    override fun asinh(): F64FlatArray = copy().apply { asinhInPlace() }
    override fun acosh(): F64FlatArray = copy().apply { acoshInPlace() }
    override fun atanh(): F64FlatArray = copy().apply { atanhInPlace() }
    override fun hypot(other: F64Array): F64FlatArray = copy().apply { hypotInPlace(other) }

    override fun diagonal(): F64FlatArray = unsupported()

    companion object {
        fun of(data: DoubleArray): F64FlatArray = F64FlatArray.create(data)
    }
}
