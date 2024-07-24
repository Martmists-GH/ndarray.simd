package com.martmists.ndarray.simd

fun main() {
    val arr = F64Array.linear(1.0, 100.0, num=100)
    println(arr::class.qualifiedName)
    arr.sqrtInPlace()
    println(arr)
}
