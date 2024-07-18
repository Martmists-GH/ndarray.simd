package com.martmists.ndarray.simd

interface Viewer {
    operator fun get(vararg indices: Int): F64Array
    operator fun set(vararg indices: Int, other: F64Array)
    operator fun set(vararg indices: Int, init: Double)
    operator fun set(any: _I, other: F64Array)
    operator fun set(any: _I, other: Double)
    operator fun get(any: _I, c: Int): F64Array
    operator fun set(any: _I, c: Int, other: F64Array)
    operator fun set(any: _I, c: Int, init: Double)
}
