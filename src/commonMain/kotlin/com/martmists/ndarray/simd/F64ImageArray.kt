package com.martmists.ndarray.simd

import com.martmists.ndarray.simd.compat.image

/**
 * A specialization type for [F64Array] representing an image with 1, 3 or 4 channels.
 * @since 1.4.1
 */
interface F64ImageArray : F64Array {
    val channels: Int

    /**
     * @see F64Array.copy
     */
    override fun copy(): F64ImageArray = F64Array.zeros(shape[0], shape[1]).also(this::copyTo).image

    /**
     * Adds the alpha channel to a new array if missing.
     * If alpha is already present, returns self
     */
    fun withAlphaChannel(): F64ImageArray {
        if (channels == 3) {
            return F64Array.concat(this, F64Array.ones(shape[0], shape[1], 1), axis = 2).image
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
     */
    fun convertRGBtoHSV(): F64ImageArray

    /**
     * Converts an HSV(A) image to RGB(A)
     *
     * @receiver The image array. See [F64Array.fromImage][F64Array.Companion.fromImage] for the format.
     * @return The image converted to RBB.
     */
    fun convertHSVtoRGB(): F64ImageArray
}
