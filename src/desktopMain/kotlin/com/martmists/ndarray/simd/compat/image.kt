@file:JvmName("ImageDesktopKt")

package com.martmists.ndarray.simd.compat

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64ImageArray
import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.nio.JpegWriter
import com.sksamuel.scrimage.nio.PngWriter
import com.sksamuel.scrimage.webp.WebpWriter
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.awt.image.DataBufferInt
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

/**
 * Reads a BufferedImage into an [F64Array].
 *
 * The resulting [F64Array] will have shape `[width, height, 4]`,
 *  where the 3rd dimension is in order RGBA. All values are in range `[0..1]`
 *
 * @param img The image to read from.
 * @return The [F64Array] read from the image.
 * @since 1.4.1
 */
fun F64Array.Companion.fromImage(img: BufferedImage): F64ImageArray {
    val w = img.width
    val h = img.height
    val hasAlpha = img.alphaRaster != null
    val pxData = (img.raster.dataBuffer as DataBufferByte).data
    val (r, g, b) = when (img.type) {
        BufferedImage.TYPE_INT_RGB, BufferedImage.TYPE_INT_ARGB -> Triple(0, 1, 2)
        BufferedImage.TYPE_INT_BGR, BufferedImage.TYPE_3BYTE_BGR, BufferedImage.TYPE_4BYTE_ABGR -> Triple(2, 1, 0)
        else -> throw UnsupportedOperationException("Unknown image type ${img.type}, please open an issue!")
    }
    val arr = F64Array(w, h, 4).image
    val pxSize = if (hasAlpha) 4 else 3

    for (y in 0 until h) {
        for (x in 0 until w) {
            val idx = (x + y * w) * pxSize
            if (hasAlpha) {
                arr[x, y, 3] = pxData[idx].toInt().asColorDouble()
                arr[x, y, r] = pxData[idx + 1].toInt().asColorDouble()
                arr[x, y, g] = pxData[idx + 2].toInt().asColorDouble()
                arr[x, y, b] = pxData[idx + 3].toInt().asColorDouble()
            } else {
                arr[x, y, 3] = 1.0
                arr[x, y, r] = pxData[idx].toInt().asColorDouble()
                arr[x, y, g] = pxData[idx + 1].toInt().asColorDouble()
                arr[x, y, b] = pxData[idx + 2].toInt().asColorDouble()
            }
        }
    }

    return arr
}

/**
 * Creates a [BufferedImage] from the [F64Array]
 *
 * @receiver the image array. See [F64Array.fromImage][F64Array.Companion.fromImage] for the format.
 * @return the [BufferedImage] created from the array.
 * @since 1.5.3
 */
fun F64ImageArray.toBufferedImage(): BufferedImage {
    val hasAlpha = channels == 4
    val img = BufferedImage(width, height, if (hasAlpha) BufferedImage.TYPE_INT_ARGB else BufferedImage.TYPE_INT_RGB)
    val pxData = (img.raster.dataBuffer as DataBufferInt).data
    val pxSize = if (hasAlpha) 4 else 3
    for (y in 0 until height) {
        for (x in 0 until width) {
            val idx = (x + y * width) * pxSize
            var pxInt = if (hasAlpha) ((this[x, y, 3] * 255).toInt().coerceIn(0, 255) shl 24) else 0
            pxInt = pxInt or ((this[x, y, 0] * 255).toInt().coerceIn(0, 255) shl 16)
            pxInt = pxInt or ((this[x, y, 1] * 255).toInt().coerceIn(0, 255) shl 8)
            pxInt = pxInt or ((this[x, y, 2] * 255).toInt().coerceIn(0, 255))
            pxData[idx] = pxInt
        }
    }
    return img
}
