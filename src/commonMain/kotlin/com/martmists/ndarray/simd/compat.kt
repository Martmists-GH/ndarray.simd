package com.martmists.ndarray.simd

/**
 * @see F64Array.plus
 */
operator fun Double.plus(arr: F64Array): F64Array = arr.plus(this)

/**
 * @see F64Array.minus
 */
operator fun Double.minus(arr: F64Array): F64Array = arr.minus(this)

/**
 * @see F64Array.times
 */
operator fun Double.times(arr: F64Array): F64Array = arr.times(this)

/**
 * @see F64Array.div
 */
operator fun Double.div(arr: F64Array): F64Array = arr.div(1 / this)

/**
 * @see F64Array.expBase
 */
fun Double.pow(arr: F64Array): F64Array = arr.expBase(this)

/**
 * Converts a [DoubleArray] to an [F64Array].
 */
fun DoubleArray.toF64Array() = F64Array.of(this)
