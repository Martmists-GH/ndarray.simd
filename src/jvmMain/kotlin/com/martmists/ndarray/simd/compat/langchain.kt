package com.martmists.ndarray.simd.compat

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64Array.Companion.invoke
import com.martmists.ndarray.simd.F64FlatArray
import dev.langchain4j.data.embedding.Embedding

/**
 * Converts an [Embedding] to an [F64FlatArray].
 * @since 1.4.0
 */
fun Embedding.toF64Array(): F64FlatArray {
    val arr = this.vector().map { it.toDouble() }.toDoubleArray()
    return F64Array(arr.size) { arr[it] }.flatten()
}

/**
 * Converts an [F64FlatArray] to an [Embedding].
 * @since 1.4.0
 */
fun F64FlatArray.toEmbedding(): Embedding {
    val arr = this.toDoubleArray()
    val floatArr = FloatArray(arr.size) { arr[it].toFloat() }
    return Embedding.from(floatArr)
}
