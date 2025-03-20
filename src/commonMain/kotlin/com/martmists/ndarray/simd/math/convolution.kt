package com.martmists.ndarray.simd.math

import com.martmists.ndarray.simd.F64ImageArray
import com.martmists.ndarray.simd.F64TwoAxisArray
import com.martmists.ndarray.simd.compat.image

/**
 * Convolves this array with some [kernel].
 *
 * @param kernel The kernel to convolve with.
 * @return The convolved array, with the same shape as this array.
 * @since 1.4.1
 */
@Suppress("EXTENSION_SHADOWED_BY_MEMBER")  // Only for binary backwards compatibility
@Deprecated("Use array.image instead", replaceWith = ReplaceWith("image.convolve(kernel)"))
fun F64ImageArray.convolve(kernel: F64TwoAxisArray): F64ImageArray {
    return image.convolve(kernel)
}
