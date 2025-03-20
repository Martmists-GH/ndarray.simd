package com.martmists.ndarray.simd.math

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64ImageArray
import com.martmists.ndarray.simd.F64TwoAxisArray

/**
 * Convolves this array with some [kernel].
 *
 * @param kernel The kernel to convolve with.
 * @return The convolved array, with the same shape as this array.
 * @since 1.4.1
 */
fun F64ImageArray.convolve(kernel: F64TwoAxisArray): F64Array {
    val kernelSize = kernel.shape[0]
    val kernelCenter = kernelSize / 2

    val result = if (shape.size == 2) {
        F64Array(shape[0], shape[1]) { x, y ->
            var sum = 0.0
            for (i in 0 until kernelSize) {
                for (j in 0 until kernelSize) {
                    val xIndex = x + i - kernelCenter
                    val yIndex = y + j - kernelCenter
                    if (xIndex in 0 until shape[0] && yIndex in 0 until shape[1]) {
                        sum += this[xIndex, yIndex] * kernel[i, j]
                    }
                }
            }
            sum
        }
    } else {
        F64Array(shape[0], shape[1], shape[2]) { x, y, z ->
            var sum = 0.0
            for (i in 0 until kernelSize) {
                for (j in 0 until kernelSize) {
                    val xIndex = x + i - kernelCenter
                    val yIndex = y + j - kernelCenter
                    if (xIndex in 0 until shape[0] && yIndex in 0 until shape[1]) {
                        sum += this[xIndex, yIndex, z] * kernel[i, j]
                    }
                }
            }
            sum
        }
    }
    return result
}
