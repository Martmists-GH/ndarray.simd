@file:JvmName("ImageAndroidKt")

package com.martmists.ndarray.simd.compat

import android.graphics.Bitmap
import android.graphics.ImageDecoder
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
 * Reads a Bitmap into an [F64Array].
 *
 * The resulting [F64Array] will have shape `[width, height, 4]`,
 *  where the 3rd dimension is in order RGBA. All values are in range `[0..1]`
 *
 * @param img The image to read from.
 * @return The [F64Array] read from the image.
 * @since 1.5.0
 */
fun F64Array.Companion.fromImage(img: Bitmap): F64ImageArray {
    val w = img.width
    val h = img.height
    val hasAlpha = img.hasAlpha()
    val arr = F64Array(w, h, 4).image

    // FIXME: Use IntArray for access instead for speed
    for (y in 0 until h) {
        for (x in 0 until w) {
            val px = img.getPixel(x, y)
            val a = px shr 24
            val r = (px shr 16) and 0xff
            val g = (px shr 8) and 0xff
            val b = px and 0xff
            if (hasAlpha) {
                arr[x, y, 3] = a.asColorDouble()
            } else {
                arr[x, y, 3] = 1.0
            }
            arr[x, y, 0] = r.asColorDouble()
            arr[x, y, 1] = g.asColorDouble()
            arr[x, y, 2] = b.asColorDouble()
        }
    }

    return arr
}

/**
 * Creates a [Bitmap] from the [F64Array]
 *
 * @receiver the image array. See [F64Array.fromImage][F64Array.Companion.fromImage] for the format.
 * @return the [Bitmap] created from the array.
 * @since 1.5.3
 */
fun F64ImageArray.toBitmap(): Bitmap {
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val hasAlpha = channels == 4

    // FIXME: Use IntArray for access instead for speed
    for (y in 0 until height) {
        for (x in 0 until width) {
            var pxInt = if (hasAlpha) ((this[x, y, 3] * 255).toInt().coerceIn(0, 255) shl 24) else 0xFF000000.toInt()
            pxInt = pxInt or ((this[x, y, 0] * 255).toInt().coerceIn(0, 255) shl 16)
            pxInt = pxInt or ((this[x, y, 1] * 255).toInt().coerceIn(0, 255) shl 8)
            pxInt = pxInt or ((this[x, y, 2] * 255).toInt().coerceIn(0, 255))
            bmp.setPixel(x, y, pxInt)
        }
    }

    return bmp
}

/**
 * Reads an image file into an [F64Array].
 *
 * The resulting [F64Array] will have shape `[width, height, 4]`,
 *  where the 3rd dimension is in order RGBA. All values are in range `[0..1]`
 *
 * @param file The image file to read.
 * @return The [F64Array] read from the image file.
 * @since 1.7.0
 */
actual fun F64Array.Companion.fromImage(file: File): F64ImageArray {
    return fromImage(ImageDecoder.decodeBitmap(ImageDecoder.createSource(file)))
}

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
actual fun F64Array.Companion.fromImage(file: ByteArray): F64ImageArray {
    return fromImage(ImageDecoder.decodeBitmap(ImageDecoder.createSource(file)))
}
