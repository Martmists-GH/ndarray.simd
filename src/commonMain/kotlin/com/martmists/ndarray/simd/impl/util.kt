package com.martmists.ndarray.simd.impl

inline fun checkIndex(label: String, pos: Int, size: Int) {
    if (pos < 0 || pos >= size) {
        throw IndexOutOfBoundsException("$label must be in [0, $size), but was $pos")
    }
}

inline fun IntArray.product() = fold(1, Int::times)
