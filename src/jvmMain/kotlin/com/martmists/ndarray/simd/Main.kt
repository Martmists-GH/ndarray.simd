package com.martmists.ndarray.simd

import kotlin.random.Random

fun main() {
    val arr = DoubleArray(23) { Random.nextDouble() }
    val f64Array = F64Array.of(arr)

    println(f64Array::class.simpleName)

    println((-f64Array).toDoubleArray().contentToString())
    println(f64Array.abs().toDoubleArray().contentToString())
}
