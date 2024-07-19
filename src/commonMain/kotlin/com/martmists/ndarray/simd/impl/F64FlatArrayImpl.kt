package com.martmists.ndarray.simd.impl

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64FlatArray
import kotlin.math.*

internal open class F64FlatArrayImpl internal constructor(
    data: DoubleArray,
    offset: Int,
    stride: Int,
    size: Int
) : F64ArrayImpl(data, offset, intArrayOf(stride), intArrayOf(size), 1, stride, size), F64FlatArray {
    override val isFlattenable: Boolean = true

    protected val unsafeGet: (Int) -> Double = { data[it * stride + offset] }
    protected val unsafeSet: (Int, Double) -> Unit = { i, v -> data[i * stride + offset] = v }

    override fun flatten(): F64FlatArray = this

    override operator fun get(pos: Int): Double {
        checkIndex("pos", pos, length)
        return unsafeGet(pos)
    }

    override operator fun set(pos: Int, value: Double) {
        checkIndex("pos", pos, length)
        unsafeSet(pos, value)
    }

    override fun contains(other: Double): Boolean {
        for (pos in 0 until length) {
            if (unsafeGet(pos) == other) {
                return true
            }
        }

        return false
    }

    override fun along(axis: Int) = unsupported()

    override fun view(index: Int, axis: Int) = unsupported()

    override fun copyTo(other: F64Array) {
        val o = checkShape(other)
        o as F64FlatArrayImpl
        for (pos in 0 until length) {
            o.unsafeSet(pos, unsafeGet(pos))
        }
    }

    override fun copy(): F64FlatArray = F64FlatArray.create(toDoubleArray(), 0, length)

    override fun reshape(vararg shape: Int): F64Array {
        shape.forEach { require(it > 0) { "shape must be positive but was $it" } }
        check(shape.product() == length) { "total size of the new array must be unchanged" }
        return when {
            this.shape.contentEquals(shape) -> this
            else -> {
                val reshaped = shape.copyOf()
                reshaped[reshaped.lastIndex] = strides.single()
                for (i in reshaped.lastIndex - 1 downTo 0) {
                    reshaped[i] = reshaped[i + 1] * shape[i + 1]
                }
                F64Array.create(data, offset, reshaped, shape)
            }
        }
    }

    override fun slice(from: Int, to: Int, step: Int, axis: Int): F64FlatArray = super.slice(from, to, step, axis) as F64FlatArray

    override fun fill(init: Double) {
        for (pos in 0 until length) {
            unsafeSet(pos, init)
        }
    }

    override fun reorder(indices: IntArray, axis: Int) {
        if (axis == 0) {
            reorderInternal(this, indices, axis,
                get = { pos -> unsafeGet(pos) },
                set = { pos, value -> unsafeSet(pos, value) })
        } else {
            unsupported()
        }
    }

    private inline fun balancedSum(getter: (Int) -> Double): Double {
        var accUnaligned = 0.0
        var remaining = length
        while (remaining % 4 > 0) {
            remaining--
            accUnaligned += getter(remaining)
        }
        val stack = DoubleArray(31 - 2)
        var p = 0
        var i = 0
        while (i < remaining) {
            // Shift.
            var v = getter(i) + getter(i + 1)
            val w = getter(i + 2) + getter(i + 3)
            v += w

            // Reduce.
            var bitmask = 4
            while (i and bitmask != 0) {
                v += stack[--p]
                bitmask = bitmask shl 1
            }
            stack[p++] = v
            i += 4
        }
        var acc = 0.0
        while (p > 0) {
            acc += stack[--p]
        }
        return acc + accUnaligned
    }

    override fun dot(other: F64Array) = balancedSum { unsafeGet(it) * other[it] }

    override fun sum(): Double = balancedSum { unsafeGet(it) }

    override fun min() = unsafeGet(argMin())

    override fun argMin(): Int {
        var minValue = Double.POSITIVE_INFINITY
        var res = 0
        for (pos in 0 until length) {
            val value = unsafeGet(pos)
            if (value <= minValue) {
                minValue = value
                res = pos
            }
        }
        return res
    }

    override fun max() = unsafeGet(argMax())

    override fun argMax(): Int {
        var maxValue = Double.NEGATIVE_INFINITY
        var res = 0
        for (pos in 0 until length) {
            val value = unsafeGet(pos)
            if (value >= maxValue) {
                maxValue = value
                res = pos
            }
        }
        return res
    }

    override fun transformInPlace(transform: (Double) -> Double) {
        println("transformInPlace: $offset $length ${strides[0]}")

        for (pos in 0 until length) {
            unsafeSet(pos, transform.invoke(unsafeGet(pos)))
        }
    }

    override fun transform(transform: (Double) -> Double): F64FlatArray {
        val res = DoubleArray(length)
        for (pos in 0 until length) {
            res[pos] = transform.invoke(unsafeGet(pos))
        }
        return F64FlatArray.create(res)
    }

    override fun zipTransformInPlace(other: F64Array, transform: (Double, Double) -> Double)  {
        val o = checkShape(other)
        o as F64FlatArrayImpl
        for (pos in 0 until length) {
            unsafeSet(pos, transform.invoke(unsafeGet(pos), o.unsafeGet(pos)))
        }
    }

    override fun zipTransform(other: F64Array, transform: (Double, Double) -> Double): F64FlatArray {
        val o = checkShape(other)
        o as F64FlatArrayImpl
        val res = DoubleArray(length)
        for (pos in 0 until length) {
            res[pos] = transform.invoke(unsafeGet(pos), o.unsafeGet(pos))
        }
        return F64FlatArray.create(res, 0, length)
    }

    override fun <T> fold(initial: T, operation: (T, Double) -> T): T {
        var res = initial
        for (pos in 0 until length) {
            res = operation(res, unsafeGet(pos))
        }
        return res
    }

    override fun reduce(operation: (Double, Double) -> Double): Double {
        var res = unsafeGet(0)
        for (pos in 1 until length) {
            res = operation(res, unsafeGet(pos))
        }
        return res
    }

    override fun coerceInPlace(min: Double, max: Double) = transformInPlace { it.coerceIn(min, max) }
    override fun expInPlace() = transformInPlace(::exp)
    override fun expm1InPlace() = transformInPlace(::expm1)
    override fun logInPlace() = transformInPlace(::ln)
    override fun log1pInPlace() = transformInPlace(::ln1p)
    override fun log2InPlace() = transformInPlace(::log2)
    override fun log10InPlace() = transformInPlace(::log10)
    override fun logBaseInPlace(base: Double) = log2(base).let { lb -> transformInPlace { log2(it) / lb } }
    override fun sqrtInPlace() = transformInPlace(::sqrt)
    override fun powInPlace(power: Double) = transformInPlace { it.pow(power) }
    override fun ipowInPlace(base: Double) = transformInPlace { base.pow(it) }
    override fun unaryMinusInPlace() = transformInPlace(Double::unaryMinus)
    override fun plusAssign(other: F64Array) = zipTransformInPlace(other, Double::plus)
    override fun plusAssign(other: Double) = transformInPlace { it + other }
    override fun minusAssign(other: F64Array) = zipTransformInPlace(other, Double::minus)
    override fun minusAssign(other: Double) = transformInPlace { it - other }
    override fun timesAssign(other: F64Array) = zipTransformInPlace(other, Double::times)
    override fun timesAssign(other: Double) = transformInPlace { it * other }
    override fun divAssign(other: F64Array) = zipTransformInPlace(other, Double::div)
    override fun divAssign(other: Double) = transformInPlace { it / other }
    override fun absInPlace() = transformInPlace(Double::absoluteValue)
    override fun ltInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a < b) 1.0 else 0.0 }
    override fun ltInPlace(other: Double) = transformInPlace { if (it < other) 1.0 else 0.0 }
    override fun gtInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a > b) 1.0 else 0.0 }
    override fun gtInPlace(other: Double) = transformInPlace { if (it > other) 1.0 else 0.0 }
    override fun eqInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a == b) 1.0 else 0.0 }
    override fun eqInPlace(other: Double) = transformInPlace { if (it == other) 1.0 else 0.0 }
    override fun neqInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a != b) 1.0 else 0.0 }
    override fun neqInPlace(other: Double) = transformInPlace { if (it != other) 1.0 else 0.0 }
    override fun andInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> (a.toInt() and b.toInt()).toDouble() }
    override fun andInPlace(other: Int) = transformInPlace { (it.toInt() and other).toDouble() }
    override fun orInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> (a.toInt() or b.toInt()).toDouble() }
    override fun orInPlace(other: Int) = transformInPlace { (it.toInt() or other).toDouble() }
    override fun xorInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> (a.toInt() xor b.toInt()).toDouble() }
    override fun xorInPlace(other: Int) = transformInPlace { (it.toInt() xor other).toDouble() }
    override fun notInPlace() = transformInPlace { it.toInt().inv().toDouble() }
    override fun shlInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> (a.toInt() shl b.toInt()).toDouble() }
    override fun shlInPlace(other: Int) = transformInPlace { (it.toInt() shl other).toDouble() }
    override fun shrInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> (a.toInt() shr b.toInt()).toDouble() }
    override fun shrInPlace(other: Int) = transformInPlace { (it.toInt() shr other).toDouble() }
    override fun sinInPlace() = transformInPlace(::sin)
    override fun cosInPlace() = transformInPlace(::cos)
    override fun tanInPlace() = transformInPlace(::tan)
    override fun asinInPlace() = transformInPlace(::asin)
    override fun acosInPlace() = transformInPlace(::acos)
    override fun atanInPlace() = transformInPlace(::atan)
    override fun atan2InPlace(other: F64Array) = zipTransformInPlace(other, ::atan2)
    override fun sinhInPlace() = transformInPlace(::sinh)
    override fun coshInPlace() = transformInPlace(::cosh)
    override fun tanhInPlace() = transformInPlace(::tanh)
    override fun asinhInPlace() = transformInPlace(::asinh)
    override fun acoshInPlace() = transformInPlace(::acosh)
    override fun atanhInPlace() = transformInPlace(::atanh)
    override fun hypotInPlace(other: F64Array) = zipTransformInPlace(other, ::hypot)

    override fun toDoubleArray() = DoubleArray(length) { unsafeGet(it) }

    override fun equals(other: Any?) = when {
        this === other -> true
        other !is F64FlatArrayImpl -> false // an instance of F64Array can't be flat
        length != other.length -> false
        else -> (0 until length).all {
            (unsafeGet(it) - other.unsafeGet(it)).absoluteValue < 1e-10
        }
    }

    override fun hashCode() = (0 until length).fold(1) { acc, pos ->
        // XXX calling #hashCode results in boxing, see KT-7571.
        31 * acc + unsafeGet(pos).hashCode()
    }
}
