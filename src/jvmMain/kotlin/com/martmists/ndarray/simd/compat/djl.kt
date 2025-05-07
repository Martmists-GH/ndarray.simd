package com.martmists.ndarray.simd.compat

import ai.djl.ndarray.NDArray
import ai.djl.ndarray.NDManager
import ai.djl.ndarray.types.Shape
import com.martmists.ndarray.simd.F64Array

private val fallbackManager by lazy {
    NDManager.newBaseManager()
}

/**
 * Converts an [NDArray] to an [F64Array].
 * @since 1.6.0
 */
fun NDArray.toF64Array(): F64Array {
    val shape = IntArray(shape.dimension()) { shape.get(it).toInt() }
    return F64Array.of(toDoubleArray()).reshape(*shape)
}

/**
 * Converts an [F64Array] to a [NDArray].
 * @param manager An optional manager to use. Falls back to [NDManager.newBaseManager] if null.
 * @since 1.6.0
 */
fun F64Array.toDJL(manager: NDManager? = null): NDArray {
    return (manager ?: fallbackManager).create(flatten().toDoubleArray(), Shape(shape.map(Int::toLong)))
}
