@file:JvmName("ImageDesktopKt")

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

fun F64ImageArray.toBufferedImage(): BufferedImage {
    if ()
}
