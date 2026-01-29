package com.martmists.ndarray.simd.compat

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64ImageArray
import org.khronos.webgl.Int8Array
import org.khronos.webgl.Uint8ClampedArray
import org.khronos.webgl.get
import org.w3c.dom.ImageData
import kotlin.math.roundToInt

fun ImageData.toArray(): F64ImageArray {
    return F64Array.of(DoubleArray(data.length) { data[it].toInt() / 255.0 }).reshape(width, height, 4).image
}

fun F64ImageArray.toImageData(): ImageData {
    val doubles = flatten().toDoubleArray()
    val arr = Array(doubles.size) { (doubles[it] * 255.0).roundToInt().coerceIn(0, 255).toByte() }
    val data = Int8Array(arr)
    val clamped = Uint8ClampedArray(data.buffer, data.byteOffset, data.byteLength)
    return ImageData(clamped, shape[0], shape[1])
}
