package com.martmists.ndarray.simd.compat

import com.martmists.ndarray.simd.F64Array
import nu.pattern.OpenCV
import org.opencv.core.CvType
import org.opencv.core.Mat

/**
 * Helper function to load OpenCV.
 * Currently only calls a single function.
 */
private fun usesOpenCV() {
    OpenCV.loadLocally()
}

/**
 * Helper function for conversions.
 *
 * @return Pair<alpha, beta>
 */
private fun getScaleInfo(type: Int): Pair<Double, Double> {
    return when (CvType.depth(type)) {
        CvType.CV_8U -> 255.0 to 0.0
        CvType.CV_8S -> 255.0 to 128.0
        CvType.CV_16U -> 65535.0 to 0.0
        CvType.CV_16S -> 65535.0 to 32768.0
        CvType.CV_32S -> 4294967295.0 to 2147483648.0
        CvType.CV_16F, CvType.CV_32F, CvType.CV_64F -> 1.0 to 0.0
        else -> throw NotImplementedError("Unsupported type: ${CvType.typeToString(type)}")
    }
}

/**
 * Converts a [Mat] to an [F64Array].
 *
 * The resulting F64Array will always contain 4 channels.
 *
 * @return The [F64Array] converted from the [Mat]. See [F64Array.fromImage][F64Array.Companion.fromImage] for the format.
 * @since 1.4.0
 */
fun Mat.toF64Array(): F64Array {
    usesOpenCV()

    val typ = if (type() == CvType.CV_64FC4) this else {
        Mat(size(), CvType.CV_64FC4).also {
            val (ai, bi) = getScaleInfo(type())
            convertTo(it, it.type(), 1 / ai, bi)
        }
    }

    val w = size(0)
    val h = size(1)
    val ch = CvType.channels(type())
    return F64Array(w, h, 4).apply {
        for (x in 0 until w) {
            for (y in 0 until h) {
                val chunk = typ[intArrayOf(x, y)]
                for (c in 0 until 4) {
                    if (c < ch) {
                        this[x, y, c] = chunk[c]
                    } else {
                        this[x, y, c] = if (c == 3) 1.0 else chunk[0]  // Handle missing channels
                    }
                }
            }
        }
    }
}

/**
 * Converts an [F64Array] to a [Mat].
 *
 * The required shape must have either 1, 3 or 4 channels in the 3rd dimension:
 * If 1 channel, shape must be [width, height, 1]
 * If 3 channels, shape must be [width, height, rgb]
 * If 4 channels, shape must be [width, height, rgba]
 *
 * All values in this array are expected to be in range `[0..1]`.
 * If values are outside this range, unexpected behavior may occur.
 *
 * @receiver The image array.
 * @param type A type from [CvType], representing the data type and channels.
 * @return The OpenCV Mat containing the given data.
 * @since 1.4.0
 */
fun F64Array.toOpenCVMat(type: Int = CvType.CV_64FC4): Mat {
    usesOpenCV()

    require(shape.size == 3) { "Expected 3D array, got ${shape.size}D" }
    require(shape[2] in arrayOf(1, 3, 4)) { "Array must be greyscale, rgb or rgba, got ${shape[2]} channels." }

    val w = shape[0]
    val h = shape[1]
    val ch = shape[2]

    val resChannels = CvType.channels(type)
    val inType = CvType.CV_64FC(resChannels)

    val tmp = Mat(w, h, inType).apply {
        for (x in 0 until w) {
            for (y in 0 until h) {
                val buf = if (resChannels == 1) {
                    if (ch > 1) {
                        val data = (0 until 3).map { this@toOpenCVMat[x, y, it] }.average()
                        doubleArrayOf(data)
                    } else {
                        doubleArrayOf(this@toOpenCVMat[x, y, 0])
                    }
                } else {
                    (0 until resChannels).map {
                        when {
                            it < ch -> this@toOpenCVMat[x, y, it]
                            it == 3 -> 1.0  // Alpha missing in input
                            else -> this@toOpenCVMat[x, y, 0]  // Monochrome
                        }
                    }.toDoubleArray()
                }
                put(x, y, *buf)
            }
        }
    }

    val out = Mat(w, h, type)
    val (a, b) = getScaleInfo(type)
    tmp.convertTo(out, type, a, -b)
    return out
}
