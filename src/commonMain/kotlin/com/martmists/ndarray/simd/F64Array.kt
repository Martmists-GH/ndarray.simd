package com.martmists.ndarray.simd

import com.martmists.ndarray.simd.impl.create
import com.martmists.ndarray.simd.impl.product
import com.martmists.ndarray.simd.impl.remove
import com.martmists.ndarray.simd.impl.unsupported
import kotlin.jvm.JvmName
import kotlin.jvm.JvmStatic
import kotlin.math.*


/**
 * The default methods are naive implementations for anyone wishing to implement their own NDArrays.
 * However, I would recommend using delegation to [F64ArrayImpl] for most use-cases.
 */
interface F64Array {
    val data: DoubleArray
    val offset: Int
    val strides: IntArray
    val shape: IntArray
    val unrollDim: Int
    val unrollStride: Int
    val unrollSize: Int

    val nDim: Int
        get() = shape.size
    val length: Int
        get() = shape[0]
    val isFlattenable: Boolean

    fun checkShape(other: F64Array): F64Array {
        check(this === other || shape.contentEquals(other.shape)) {
            "operands shapes do not match: ${shape.contentToString()} vs ${other.shape.contentToString()}"
        }
        return other
    }

    operator fun get(vararg indices: Int): Double
    operator fun get(r: Int, c: Int): Double
    operator fun get(d: Int, r: Int, c: Int): Double

    operator fun set(vararg indices: Int, value: Double)
    operator fun set(r: Int, c: Int, value: Double)
    operator fun set(d: Int, r: Int, c: Int, value: Double)

    fun along(axis: Int): Sequence<F64Array> = (0 until shape[axis]).asSequence().map { view(it, axis) }
    fun view(index: Int, axis: Int = 0): F64Array = unsupported()

    val V: Viewer

    fun copy(): F64Array = F64Array.create(data.copyOf(), offset, strides.copyOf(), shape.copyOf())
    fun copyTo(other: F64Array) = other.zipTransformInPlace(this) { _, d -> d }
    fun reshape(vararg shape: Int): F64Array = flatten().reshape(*shape)
    fun flatten(): F64FlatArray = unsupported()
    fun slice(from: Int = 0, to: Int = -1, step: Int = 1, axis: Int = 0): F64Array
    operator fun contains(other: Double): Boolean
    fun fill(value: Double) = transformInPlace { value }
    fun reorder(indices: IntArray, axis: Int = 0): Unit = unsupported()
    infix fun dot(other: F64Array): Double = unsupported()
    fun sum(): Double = reduce { acc, d -> acc + d }
    fun min(): Double = fold(Double.POSITIVE_INFINITY) { acc, d -> if (d < acc) d else acc }
    fun max(): Double = fold(Double.NEGATIVE_INFINITY) { acc, d -> if (d > acc) d else acc }
    fun product(): Double = reduce { acc, d -> acc * d }
    fun mean(): Double = sum() / shape.product()
    fun variance(): Double = fold(0.0) { acc, d -> acc + (d - mean()).pow(2) } / shape.product()
    fun stdDev(): Double = sqrt(variance())
    fun cumSumInPlace() {
        var sum = 0.0
        transformInPlace { sum += it; sum }
    }
    fun cumSum(): F64Array = copy().apply { cumSumInPlace() }
    fun coerceInPlace(min: Double, max: Double) = transformInPlace { it.coerceIn(min, max) }
    fun coerce(min: Double, max: Double): F64Array = copy().apply { coerceInPlace(min, max) }
    fun transformInPlace(transform: (Double) -> Double)
    fun transform(transform: (Double) -> Double): F64Array = copy().apply { transformInPlace(transform) }
    fun zipTransformInPlace(other: F64Array, transform: (Double, Double) -> Double)
    fun zipTransform(other: F64Array, transform: (Double, Double) -> Double): F64Array = copy().apply { zipTransformInPlace(other, transform) }
    fun <T> fold(initial: T, operation: (acc: T, Double) -> T): T
    fun reduce(operation: (Double, Double) -> Double): Double
    fun expInPlace() = transformInPlace(::exp)
    fun exp(): F64Array = copy().apply { expInPlace() }
    fun expm1InPlace() = transformInPlace(::expm1)
    fun expm1(): F64Array = copy().apply { expm1InPlace() }
    fun logInPlace() = transformInPlace(::ln)
    fun log(): F64Array = copy().apply { logInPlace() }
    fun log1pInPlace() = transformInPlace { ln(1 + it) }
    fun log1p(): F64Array = copy().apply { log1pInPlace() }
    fun log2InPlace() = transformInPlace(::log2)
    fun log2(): F64Array = copy().apply { log2InPlace() }
    fun log10InPlace() = transformInPlace(::log10)
    fun log10(): F64Array = copy().apply { log10InPlace() }
    fun logBaseInPlace(base: Double) = transformInPlace { log2(it) / log2(base) }  // On some systems this is fastest, on others it's slowest?
    fun logBase(base: Double): F64Array = copy().apply { logBaseInPlace(base) }
    fun sqrtInPlace() = transformInPlace(::sqrt)
    fun sqrt(): F64Array = copy().apply { sqrtInPlace() }
    fun powInPlace(power: Double) = transformInPlace { it.pow(power) }
    fun pow(power: Double): F64Array = copy().apply { powInPlace(power) }
    fun ipowInPlace(base: Double) = transformInPlace { base.pow(it) }
    fun ipow(base: Double): F64Array = copy().apply { ipowInPlace(base) }

    operator fun unaryPlus(): F64Array = this
    fun unaryMinusInPlace() = transformInPlace(Double::unaryMinus)
    operator fun unaryMinus(): F64Array = copy().apply { unaryMinusInPlace() }
    operator fun plusAssign(other: F64Array) = zipTransformInPlace(other) { a, b -> a + b }
    operator fun plus(other: F64Array): F64Array = copy().apply { plusAssign(other) }
    operator fun plusAssign(other: Double) = transformInPlace { it + other }
    operator fun plus(other: Double): F64Array = copy().apply { plusAssign(other) }
    operator fun minusAssign(other: F64Array) = zipTransformInPlace(other) { a, b -> a - b }
    operator fun minus(other: F64Array): F64Array = copy().apply { minusAssign(other) }
    operator fun minusAssign(other: Double) = transformInPlace { it - other }
    operator fun minus(other: Double): F64Array = copy().apply { minusAssign(other) }
    operator fun timesAssign(other: F64Array) = zipTransformInPlace(other) { a, b -> a * b }
    operator fun times(other: F64Array): F64Array = copy().apply { timesAssign(other) }
    operator fun timesAssign(other: Double) = transformInPlace { it * other }
    operator fun times(other: Double): F64Array = copy().apply { timesAssign(other) }
    operator fun divAssign(other: F64Array) = zipTransformInPlace(other) { a, b -> a / b }
    operator fun div(other: F64Array): F64Array = copy().apply { divAssign(other) }
    operator fun divAssign(other: Double) = transformInPlace { it / other }
    operator fun div(other: Double): F64Array = copy().apply { divAssign(other) }
    fun absInPlace() = transformInPlace(::abs)
    fun abs(): F64Array = copy().apply { absInPlace() }

    fun ltInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a < b) 1.0 else 0.0 }
    infix fun lt(other: F64Array): F64Array = copy().apply { ltInPlace(other) }
    fun ltInPlace(other: Double) = transformInPlace { if (it < other) 1.0 else 0.0 }
    infix fun lt(other: Double): F64Array = copy().apply { ltInPlace(other) }
    fun lteInPlace(other: F64Array) {
        gtInPlace(other)
        xorInPlace(1)
    }
    infix fun lte(other: F64Array): F64Array = copy().apply { lteInPlace(other) }
    fun lteInPlace(other: Double) {
        gtInPlace(other)
        xorInPlace(1)
    }
    infix fun lte(other: Double): F64Array = copy().apply { lteInPlace(other) }
    fun gtInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a > b) 1.0 else 0.0 }
    infix fun gt(other: F64Array): F64Array = copy().apply { gtInPlace(other) }
    fun gtInPlace(other: Double) = transformInPlace { if (it > other) 1.0 else 0.0 }
    infix fun gt(other: Double): F64Array = copy().apply { gtInPlace(other) }
    fun gteInPlace(other: F64Array) {
        ltInPlace(other)
        xorInPlace(1)
    }
    infix fun gte(other: F64Array): F64Array = copy().apply { gteInPlace(other) }
    fun gteInPlace(other: Double) {
        ltInPlace(other)
        xorInPlace(1)
    }
    infix fun gte(other: Double): F64Array = copy().apply { gteInPlace(other) }
    fun eqInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a == b) 1.0 else 0.0 }
    infix fun eq(other: F64Array): F64Array = copy().apply { eqInPlace(other) }
    fun eqInPlace(other: Double) = transformInPlace { if (it == other) 1.0 else 0.0 }
    infix fun eq(other: Double): F64Array = copy().apply { eqInPlace(other) }
    fun neqInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a != b) 1.0 else 0.0 }
    infix fun neq(other: F64Array): F64Array = copy().apply { neqInPlace(other) }
    fun neqInPlace(other: Double) = transformInPlace { if (it != other) 1.0 else 0.0 }
    infix fun neq(other: Double): F64Array = copy().apply { neqInPlace(other) }

    fun andInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a != 0.0 && b != 0.0) 1.0 else 0.0 }
    infix fun and(other: F64Array): F64Array = copy().apply { andInPlace(other) }
    fun andInPlace(other: Int) = transformInPlace { (it.toInt() and other).toDouble() }
    infix fun and(other: Int): F64Array = copy().apply { andInPlace(other) }
    fun orInPlace(other: F64Array) = transformInPlace { (it.toInt() or other[it.toInt()].toInt()).toDouble() }
    infix fun or(other: F64Array): F64Array = copy().apply { orInPlace(other) }
    fun orInPlace(other: Int) = transformInPlace { (it.toInt() or other).toDouble() }
    infix fun or(other: Int): F64Array = copy().apply { orInPlace(other) }
    fun xorInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a != b) 1.0 else 0.0 }
    infix fun xor(other: F64Array): F64Array = copy().apply { xorInPlace(other) }
    fun xorInPlace(other: Int) = transformInPlace { (it.toInt() xor other).toDouble() }
    infix fun xor(other: Int): F64Array = copy().apply { xorInPlace(other) }
    fun notInPlace() = transformInPlace { it.toInt().inv().toDouble() }
    fun not(): F64Array = copy().apply { notInPlace() }
    fun shlInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> (a.toInt() shl b.toInt()).toDouble() }
    infix fun shl(other: F64Array): F64Array = copy().apply { shlInPlace(other) }
    fun shlInPlace(other: Int) = transformInPlace { (it.toInt() shl other).toDouble() }
    infix fun shl(other: Int): F64Array = copy().apply { shlInPlace(other) }
    fun shrInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> (a.toInt() shr b.toInt()).toDouble() }
    infix fun shr(other: F64Array): F64Array = copy().apply { shrInPlace(other) }
    fun shrInPlace(other: Int) = transformInPlace { (it.toInt() shr other).toDouble() }
    infix fun shr(other: Int): F64Array = copy().apply { shrInPlace(other) }

    fun sinInPlace() = transformInPlace(::sin)
    fun sin(): F64Array = copy().apply { sinInPlace() }
    fun cosInPlace() = transformInPlace(::cos)
    fun cos(): F64Array = copy().apply { cosInPlace() }
    fun tanInPlace() = transformInPlace(::tan)
    fun tan(): F64Array = copy().apply { tanInPlace() }
    fun asinInPlace() = transformInPlace(::asin)
    fun asin(): F64Array = copy().apply { asinInPlace() }
    fun acosInPlace() = transformInPlace(::acos)
    fun acos(): F64Array = copy().apply { acosInPlace() }
    fun atanInPlace() = transformInPlace(::atan)
    fun atan(): F64Array = copy().apply { atanInPlace() }
    fun atan2InPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> atan2(a, b) }
    fun atan2(other: F64Array): F64Array = copy().apply { atan2InPlace(other) }
    fun sinhInPlace() = transformInPlace(::sinh)
    fun sinh(): F64Array = copy().apply { sinhInPlace() }
    fun coshInPlace() = transformInPlace(::cosh)
    fun cosh(): F64Array = copy().apply { coshInPlace() }
    fun tanhInPlace() = transformInPlace(::tanh)
    fun tanh(): F64Array = copy().apply { tanhInPlace() }
    fun asinhInPlace() = transformInPlace(::asinh)
    fun asinh(): F64Array = copy().apply { asinhInPlace() }
    fun acoshInPlace() = transformInPlace(::acosh)
    fun acosh(): F64Array = copy().apply { acoshInPlace() }
    fun atanhInPlace() = transformInPlace(::atanh)
    fun atanh(): F64Array = copy().apply { atanhInPlace() }
    fun hypotInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> hypot(a, b) }
    fun hypot(other: F64Array): F64Array = copy().apply { hypotInPlace(other) }

    fun diagonal(): F64FlatArray = unsupported()

    infix fun matmul(other: F64Array): F64Array = unsupported()

    fun toDoubleArray(): DoubleArray = unsupported()

    fun toString(maxDisplay: Int): String = unsupported()

    companion object {
        val simdSize by lazy { NativeSpeedup.getSimdSize() * 2 }

        @JvmStatic
        @JvmName("create")
        operator fun invoke(vararg shape: Int) = F64FlatArray.create(DoubleArray(shape.product())).reshape(*shape)

        @JvmStatic
        @JvmName("createArray")
        operator fun invoke(size: Int, init: (Int) -> Double) = F64Array(size).apply {
            for (i in 0 until size) {
                this[i] = init(i)
            }
        }

        @JvmStatic
        @JvmName("create2D")
        operator fun invoke(numRows: Int, numColumns: Int, init: (Int, Int) -> Double) = F64Array(numRows, numColumns).apply {
            for (r in 0 until numRows) {
                for (c in 0 until numColumns) {
                    this[r, c] = init(r, c)
                }
            }
        }

        @JvmStatic
        @JvmName("create3D")
        operator fun invoke(x: Int, y: Int, z: Int, init: (Int, Int, Int) -> Double) = F64Array(x, y, z).apply {
            for (d in 0 until x) {
                for (r in 0 until y) {
                    for (c in 0 until z) {
                        this[d, r, c] = init(d, r, c)
                    }
                }
            }
        }

        @JvmStatic
        fun of(vararg values: Double) = F64FlatArray.of(values)
        @JvmStatic
        @JvmName("ofArray")
        fun of(data: DoubleArray) = F64FlatArray.of(data)

        @JvmStatic
        fun full(vararg shape: Int, init: Double): F64Array = full(shape, init)
        @JvmStatic
        @JvmName("fullArray")
        fun full(shape: IntArray, init: Double): F64Array {
            return F64FlatArray.create(DoubleArray(shape.product()).apply { fill(init) }).reshape(*shape)
        }

        @JvmStatic
        fun identity(n: Int): F64Array = zeros(intArrayOf(n, n)).apply {
            for (i in 0 until n) {
                this[i, i] = 1.0
            }
        }

        @JvmStatic
        fun diagonal(values: DoubleArray): F64Array {
            val n = values.size
            val result = zeros(intArrayOf(n, n))
            for (i in 0 until n) {
                result[i, i] = values[i]
            }
            return result
        }

        @JvmStatic
        fun zeros(vararg shape: Int): F64Array = full(shape, 0.0)
        @JvmStatic
        @JvmName("zerosArray")
        fun zeros(shape: IntArray): F64Array = full(shape, 0.0)

        @JvmStatic
        fun ones(vararg shape: Int): F64Array = full(shape, 1.0)
        @JvmStatic
        @JvmName("onesArray")
        fun ones(shape: IntArray): F64Array = full(shape, 1.0)

        @JvmStatic
        fun ofRows(rows: List<DoubleArray>): F64Array = ofRows(rows.map { F64FlatArray.of(it) })
        @JvmStatic
        @JvmName("ofRowsArray")
        fun ofRows(rows: List<F64Array>): F64Array {
            val args = rows.map { it.reshape(1, *it.shape) }
            return concat(args[0], *args.slice(1 until args.size).toTypedArray(), axis = 0)
        }

        @JvmStatic
        fun concat(first: F64Array, vararg rest: F64Array, axis: Int = 0): F64Array {
            for (other in rest) {
                require(other.shape.remove(axis).contentEquals(first.shape.remove(axis))) {
                    "input array shapes must be exactly equal for all dimensions except $axis"
                }
            }

            val shape = first.shape.copyOf().apply {
                this[axis] = first.shape[axis] + rest.sumOf { it.shape[axis] }
            }

            val result = invoke(*shape)
            var offset = 0
            for (a in arrayOf(first, *rest)) {
                if (a.length > 0) {
                    a.copyTo(result.slice(offset, offset + a.shape[axis], axis = axis))
                    offset += a.shape[axis]
                }
            }

            return result
        }
    }
}
