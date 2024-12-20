package com.martmists.ndarray.simd.impl

import com.martmists.ndarray.simd.F64Array

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
 * Checks if the axis is valid.
 *
 * @param axis The axis to check
 * @throws IndexOutOfBoundsException If the index is out of bounds.
 */
inline fun F64Array.checkAxis(axis: Int) {
    checkIndex("axis", axis, shape.size)
}

/**
 * Returns the product of all elements in the array.
 *
 * @receiver The array.
 * @return The product of all elements.
 */
inline fun IntArray.product() = fold(1, Int::times)
