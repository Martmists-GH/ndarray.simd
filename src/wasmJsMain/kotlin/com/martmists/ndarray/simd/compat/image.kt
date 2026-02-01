package com.martmists.ndarray.simd.compat

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64ImageArray
import org.khronos.webgl.Int8Array
import org.khronos.webgl.Uint8ClampedArray
import org.khronos.webgl.get
import org.khronos.webgl.set
import org.w3c.dom.ImageData
import kotlin.apply

fun ImageData.toArray(): F64ImageArray {
    return F64Array(width, height, 4).image.apply {
        var idx = 0
        for (j in 0 until shape[1]) {
            for (i in 0 until shape[0]) {
                for (c in 0 until shape[2]) {
                    this[i, j, c] = this@toArray.data[idx++].toInt() / 255.0
                }
            }
        }
    }
}

fun F64ImageArray.toImageData(): ImageData {
    var out = copy()
    if (out.channels == 3) {
        out = F64Array.concat(out, F64Array.ones(out.width, out.height), axis = 2).image
    }

    out *= 255.0
    out.roundInPlace()
    out.coerceInPlace(0.0, 255.0)

    val data = Int8Array(width * height * 4)
    var idx = 0
    for (j in 0 until shape[1]) {
        for (i in 0 until shape[0]) {
            for (c in 0 until shape[2]) {
                data[idx++] = out[i, j, c].toInt().toByte()
            }
        }
    }
    val clamped = Uint8ClampedArray(data.buffer, data.byteOffset, data.byteLength)
    return ImageData(clamped, shape[0], shape[1])
}
