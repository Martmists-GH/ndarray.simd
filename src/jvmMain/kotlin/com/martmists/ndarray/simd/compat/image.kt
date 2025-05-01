@file:JvmName("ImageJvmKt")

package com.martmists.ndarray.simd.compat

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64Array.Companion.invoke
import com.martmists.ndarray.simd.F64ImageArray
import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.nio.JpegWriter
import com.sksamuel.scrimage.nio.PngWriter
import com.sksamuel.scrimage.webp.WebpWriter
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.io.File

private fun Int.asColorDouble(): Double = (if (this < 0) 256 + this else this) / 255.0

/**
 * Reads an image file into an [F64Array].
 *
 * The resulting [F64Array] will have shape `[width, height, 4]`,
 *  where the 3rd dimension is in order RGBA. All values are in range `[0..1]`
 *
 * @param file The image file to read.
 * @return The [F64Array] read from the image file.
 * @since 1.4.0
 */
fun F64Array.Companion.fromImage(file: File): F64ImageArray {
    val img = ImmutableImage.loader().fromFile(file)
    val w = img.width
    val h = img.height
    val arr = F64Array(w, h, 4).image
    for (y in 0 until h) {
        for (x in 0 until w) {
            val px = img.pixel(x, y)
            arr[x, y, 0] = px.red().asColorDouble()
            arr[x, y, 1] = px.green().asColorDouble()
            arr[x, y, 2] = px.blue().asColorDouble()
            arr[x, y, 3] = px.alpha().asColorDouble()
        }
    }
    return arr
}

/**
 * Writes an [F64Array] to an image file.
 *
 * @receiver The image array. See [F64Array.fromImage][F64Array.Companion.fromImage] for the format.
 * @param file The image file to write to. Valid file extensions are `.png`, `.jpg`, and `.webp`.
 * @since 1.4.1
 */
fun F64ImageArray.toImage(file: File) {
    val img = ImmutableImage.create(width, height)
    for (y in 0 until height) {
        for (x in 0 until width) {
            var px = img.pixel(x, y)
            px = px.red((this[x, y, 0] * 255).toInt())
            px = px.green((this[x, y, 1] * 255).toInt())
            px = px.blue((this[x, y, 2] * 255).toInt())
            px = if (shape[2] == 4) {
                px.alpha((this[x, y, 3] * 255).toInt())
            } else {
                px.alpha(255)
            }
            img.setPixel(px)
        }
    }

    val writer = when (file.extension.lowercase()) {
        "png" -> PngWriter.MinCompression
        "jpg", "jpeg" -> JpegWriter.Default
        "webp" -> WebpWriter.DEFAULT
        else -> throw IllegalArgumentException("Unsupported image format: ${file.extension}")
    }

    file.writeBytes(img.bytes(writer))
}


/**
 * Writes an [F64Array] to an image file.
 *
 * @receiver The image array. See [F64Array.fromImage][F64Array.Companion.fromImage] for the format.
 * @param file The image file to write to. Valid file extensions are `.png`, `.jpg`, and `.webp`.
 * @since 1.4.0
 */
@Deprecated("Use array.image instead", replaceWith = ReplaceWith("image.toImage(file)"))
fun F64Array.toImage(file: File) {
    image.toImage(file)
}
