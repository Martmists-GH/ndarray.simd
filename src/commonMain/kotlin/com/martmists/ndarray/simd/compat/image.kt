@file:JvmName("ImageCommonKt")

package com.martmists.ndarray.simd.compat

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64ImageArray
import com.martmists.ndarray.simd.impl.F64ImageArrayImpl
import kotlin.jvm.JvmName

/**
 * A wrapper for image operations.
 * The receiver array must have shape `[width, height, channels]` where channels is 3 or 4.
 *
 * @since 1.4.1
 */
val F64Array.image: F64ImageArray
    get() {
        return when {
            this is F64ImageArray -> this
            shape.size == 2 -> reshape(shape[0], shape[1], 1).image
            shape.size == 3 -> when {
                shape[2] == 1 -> F64Array.concat(this, this, this, axis = 2).image
                shape[2] == 3 || shape[2] == 4 -> F64ImageArrayImpl(this)
                else -> throw IllegalArgumentException("Array is not an image! (expected 1, 3 or 4 channels, got ${shape[2]})")
            }
            else -> throw IllegalArgumentException("Array is not an image! (expected 2 or 3 dimensions, got ${shape.size})")
        }
    }

/**
 * Converts an RGB(A) image to HSV(A)
 *
 * H in `[0..360]`
 * S in `[0..1]`
 * V in `[0..1]`
 *
 * @receiver The image array. See [F64Array.fromImage][F64Array.Companion.fromImage] for the format.
 * @return The image converted to HSV.
 * @since 1.4.0
 */
@Deprecated("Use array.image instead", replaceWith = ReplaceWith("image.convertRGBtoHSV()"))
fun F64Array.convertRGBtoHSV(): F64ImageArray {
    return image.convertRGBtoHSV()
}

/**
 * Converts an HSV(A) image to RGB(A)
 *
 * @receiver The image array. See [F64Array.fromImage][F64Array.Companion.fromImage] for the format.
 * @return The image converted to RBB.
 * @since 1.4.0
 */
@Deprecated("Use array.image instead", replaceWith = ReplaceWith("image.convertHSVtoRGB()"))
fun F64Array.convertHSVtoRGB(): F64ImageArray {
    return image.convertHSVtoRGB()
}
