package com.martmists.ndarray.simd

import com.martmists.ndarray.simd.impl.create

/**
 * A 1D specialization type for [F64Array].
 */
interface F64FlatArray : F64Array {
    override val isFlattenable: Boolean
        get() = true

    override fun checkShape(other: F64Array): F64FlatArray {
        check(this === other || (other is F64FlatArray && shape[0] == other.shape[0])) {
            "operands shapes do not match: ${shape.contentToString()} vs ${other.shape.contentToString()}"
        }
        return other as F64FlatArray
    }

    override fun flatten(): F64FlatArray = this

    operator fun get(pos: Int): Double
    operator fun set(pos: Int, value: Double)

    fun argMin(): Int = (0 until length).minBy(::get)
    fun argMax(): Int = (0 until length).maxBy(::get)

    companion object {
        fun of(data: DoubleArray): F64FlatArray = F64FlatArray.create(data)
    }
}
