@file:Suppress("DEPRECATION")

package com.martmists.ndarray.simd.impl

import com.martmists.ndarray.simd.*
import com.martmists.ndarray.simd.compat.image

internal class F64ImageArrayImpl internal constructor(private val backing: F64Array) : F64Array by backing, F64ImageArray {
    override val width = shape[0]
    override val height = shape[1]
    override val channels = shape[2]

    override fun copy(): F64ImageArray {
        return F64ImageArrayImpl(F64Array.zeros(shape[0], shape[1], channels).also(this::copyTo))
    }

    override fun convertRGBtoHSV(): F64ImageArray {
        require(channels == 3 || channels == 4) { "Image must have at least 3 channels" }

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

    override fun convertHSVtoRGB(): F64ImageArray {
        require(channels == 3 || channels == 4) { "Image must have at least 3 channels" }

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

    override fun convolve(kernel: F64TwoAxisArray): F64ImageArray {
        val kernelSize = kernel.shape[0]
        val kernelCenter = kernelSize / 2

        return F64Array(shape[0], shape[1], shape[2]) { x, y, z ->
            var sum = 0.0
            for (i in 0 until kernelSize) {
                for (j in 0 until kernelSize) {
                    val xIndex = x + i - kernelCenter
                    val yIndex = y + j - kernelCenter
                    if (xIndex in 0 until shape[0] && yIndex in 0 until shape[1]) {
                        sum += this[xIndex, yIndex, z] * kernel[i, j]
                    }
                }
            }
            sum
        }.image
    }

    override fun toString() = toString(8)
}
