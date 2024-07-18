package com.martmists.ndarray.simd

operator fun Double.plus(arr: F64Array): F64Array = arr.plus(this)
operator fun Double.minus(arr: F64Array): F64Array = arr.minus(this)
operator fun Double.times(arr: F64Array): F64Array = arr.times(this)
operator fun Double.div(arr: F64Array): F64Array = arr.div(this)

fun Double.pow(arr: F64Array): F64Array = arr.ipow(this)

fun DoubleArray.toF64Array() = F64Array.of(this)
