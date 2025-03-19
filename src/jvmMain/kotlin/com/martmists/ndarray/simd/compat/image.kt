package com.martmists.ndarray.simd.compat

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64Array.Companion.invoke
import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.nio.JpegWriter
import com.sksamuel.scrimage.nio.PngWriter
import com.sksamuel.scrimage.webp.WebpWriter
import java.io.File

// TODO: Consider adding an [F64Array.image] attribute for these operations?
// Maybe allows for other compat to register their utils under namespaces as well

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
fun F64Array.Companion.fromImage(file: File): F64Array {
    val img = ImmutableImage.loader().fromFile(file)
    val w = img.width
    val h = img.height
    val arr = F64Array(w, h, 4)
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
 * Writes an [F64Array] to an image file.
 *
 * @receiver The image array. See [F64Array.fromImage][F64Array.Companion.fromImage] for the format.
 * @param file The image file to write to. Valid file extensions are `.png`, `.jpg`, and `.webp`.
 * @since 1.4.0
 */
fun F64Array.toImage(file: File) {
    require(shape.size == 3) { "Expected 3D array, got ${shape.size}D" }
    require(shape[2] == 3 || shape[2] == 4) { "Expected 3 or 4 channels, got ${shape[2]} channels" }
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
fun F64Array.convertRGBtoHSV(): F64Array {
    require(shape.size == 3) { "Expected 3D array, got ${shape.size}D" }
    require(shape[2] == 3 || shape[2] == 4) { "Expected 3 or 4 channels, got ${shape[2]} channels" }

    return copy().apply {
        for (x in 0 until shape[0]) {
            for (y in 0 until shape[1]) {
                var r = this[x, y, 0].coerceIn(0.0 .. 1.0)
                var g = this[x, y, 1].coerceIn(0.0 .. 1.0)
                var b = this[x, y, 2].coerceIn(0.0 .. 1.0)

                r *= 255
                g *= 255
                b *= 255

                val max = maxOf(r, g, b)
                val min = minOf(r, g, b)
                val delta = max - min

                var h = when {
                    delta == 0.0 -> 0.0
                    max == r -> 60.0 * ((g - b) / delta % 6)
                    max == g -> 60.0 * ((b - r) / delta + 2)
                    else -> 60.0 * ((r - g) / delta + 4)
                }

                if (h < 0.0) h += 360.0

                val s = if (max == 0.0) 0.0 else delta / max
                val v = max / 255.0

                this[x, y, 0] = h
                this[x, y, 1] = s
                this[x, y, 2] = v
            }
        }
    }
}


/**
 * Converts an HSV(A) image to RGB(A)
 *
 * @receiver The image array. See [F64Array.fromImage][F64Array.Companion.fromImage] for the format.
 * @return The image converted to RBB.
 * @since 1.4.0
 */
fun F64Array.convertHSVtoRGB(): F64Array {
    require(shape.size == 3) { "Expected 3D array, got ${shape.size}D" }
    require(shape[2] == 3 || shape[2] == 4) { "Expected 3 or 4 channels, got ${shape[2]} channels" }

    return copy().apply {
        for (x in 0 until shape[0]) {
            for (y in 0 until shape[1]) {
                val h = this[x, y, 0].coerceIn(0.0 .. 360.0)
                val s = this[x, y, 1].coerceIn(0.0 .. 1.0)
                val v = this[x, y, 2].coerceIn(0.0 .. 1.0)

                fun component(n: Int): Double {
                    val k = (n + h / 60) % 6
                    return v - (v * s * maxOf(0.0, minOf(k, 4 - k, 1.0)))
                }

                val r = component(5)
                val g = component(3)
                val b = component(1)

                this[x, y, 0] = r
                this[x, y, 1] = g
                this[x, y, 2] = b
            }
        }
    }
}
