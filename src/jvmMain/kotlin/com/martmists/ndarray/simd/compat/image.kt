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
            arr[x, y, 0] = px.red().toDouble() / 255
            arr[x, y, 1] = px.green().toDouble() / 255
            arr[x, y, 2] = px.blue().toDouble() / 255
            arr[x, y, 3] = px.alpha().toDouble() / 255
        }
    }
    return arr
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
    val arr = F64Array(w, h, 4).image
    val pxSize = if (hasAlpha) 3 else 4

    for (y in 0 until h) {
        for (x in 0 until w) {
            val idx = (x + y * w) * pxSize
            if (hasAlpha) {
                arr[x, y, 3] = (pxData[idx].toInt() + 128) / 255.0
                arr[x, y, 0] = (pxData[idx + 1].toInt() + 128) / 255.0
                arr[x, y, 1] = (pxData[idx + 2].toInt() + 128) / 255.0
                arr[x, y, 2] = (pxData[idx + 3].toInt() + 128) / 255.0
            } else {
                arr[x, y, 3] = 1.0
                arr[x, y, 0] = (pxData[idx].toInt() + 128) / 255.0
                arr[x, y, 1] = (pxData[idx + 1].toInt() + 128) / 255.0
                arr[x, y, 2] = (pxData[idx + 2].toInt() + 128) / 255.0
            }
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
    val w = shape[0]
    val h = shape[1]
    val img = ImmutableImage.create(w, h)
    for (y in 0 until h) {
        for (x in 0 until w) {
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
