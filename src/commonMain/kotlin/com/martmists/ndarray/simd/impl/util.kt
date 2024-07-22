package com.martmists.ndarray.simd.impl

/**
 * Checks if the index is in bounds.
 *
 * @param label The label of the index.
 * @param pos The index.
 * @param size The size of the array.
 * @throws IndexOutOfBoundsException If the index is out of bounds.
 */
inline fun checkIndex(label: String, pos: Int, size: Int) {
    if (pos < 0 || pos >= size) {
        throw IndexOutOfBoundsException("$label must be in [0, $size), but was $pos")
    }
}

/**
 * Returns the product of all elements in the array.
 *
 * @receiver The array.
 * @return The product of all elements.
 */
inline fun IntArray.product() = fold(1, Int::times)
