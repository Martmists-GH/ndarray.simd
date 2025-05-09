package com.martmists.ndarray.simd.compat

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64ImageArray
import java.io.File

/**
 * Reads an image file into an [F64Array].
 *
 * The resulting [F64Array] will have shape `[width, height, 4]`,
 *  where the 3rd dimension is in order RGBA. All values are in range `[0..1]`
 *
 * @param file The image file to read.
 * @return The [F64Array] read from the image file.
 * @since 1.4.0 (common with Android since 1.7.0)
 */
expect fun F64Array.Companion.fromImage(file: File): F64ImageArray

/**
 * Reads an image file into an [F64Array].
 *
 * The resulting [F64Array] will have shape `[width, height, 4]`,
 *  where the 3rd dimension is in order RGBA. All values are in range `[0..1]`
 *
 * @param file The image bytes to read.
 * @return The [F64Array] read from the image file.
 * @since 1.7.0
 */
expect fun F64Array.Companion.fromImage(file: ByteArray): F64ImageArray
