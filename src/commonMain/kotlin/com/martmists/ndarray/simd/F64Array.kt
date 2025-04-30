@file:Suppress("DEPRECATION")

package com.martmists.ndarray.simd

import com.martmists.ndarray.simd.impl.create
import com.martmists.ndarray.simd.impl.product
import com.martmists.ndarray.simd.impl.remove
import com.martmists.ndarray.simd.impl.unsupported
import kotlin.jvm.JvmName
import kotlin.jvm.JvmStatic
import kotlin.math.*
import com.martmists.ndarray.simd.impl.F64ArrayImpl
import kotlin.jvm.JvmOverloads
import kotlin.random.Random


/**
 * The default methods are naive implementations for anyone wishing to implement their own NDArrays.
 * However, I would recommend using delegation to [F64ArrayImpl] or one of the constructors in the companion object.
 */
interface F64Array {
    /**
     * The underlying data of the array. This should not typically be accessed directly.
     */
    @get:Deprecated("This should not be accessed directly")
    val data: DoubleArray

    /**
     * The offset into the data array where the array starts.
     */
    @get:Deprecated("This should not be accessed directly")
    val offset: Int

    /**
     * The strides of the array.
     */
    @get:Deprecated("This should not be accessed directly")
    val strides: IntArray

    /**
     * The shape of the array.
     */
    val shape: IntArray

    /**
     * The unrolling dimension of the array.
     */
    @get:Deprecated("This should not be accessed directly")
    val unrollDim: Int

    /**
     * The stride of the unrolling dimension.
     */
    @get:Deprecated("This should not be accessed directly")
    val unrollStride: Int

    /**
     * The size of the unrolling dimension.
     */
    @get:Deprecated("This should not be accessed directly")
    val unrollSize: Int

    /**
     * The number of dimensions of the array.
     */
    val nDim: Int
        get() = shape.size

    /**
     * The length of the array in the first dimension.
     */
    val length: Int
        get() = shape[0]

    /**
     * Whether the array can be flattened.
     */
    val isFlattenable: Boolean

    /**
     * Checks if the shape of the array matches the shape of another array.
     *
     * @param other the other array
     * @return the other array if the shapes match, cast to [F64FlatArray] if both arrays are flat
     * @throws IllegalArgumentException if the shapes do not match
     */
    fun checkShape(other: F64Array): F64Array {
        check(this === other || shape.contentEquals(other.shape)) {
            "operands shapes do not match: ${shape.contentToString()} vs ${other.shape.contentToString()}"
        }
        return other
    }

    /**
     * Indexing operators
     */
    operator fun get(vararg indices: Int): Double
    operator fun get(r: Int, c: Int): Double
    operator fun get(d: Int, r: Int, c: Int): Double

    operator fun set(vararg indices: Int, value: Double)
    operator fun set(r: Int, c: Int, value: Double)
    operator fun set(d: Int, r: Int, c: Int, value: Double)

    /**
     * Iterates over the array along the specified axis.
     *
     * @param axis the axis to iterate along
     * @return a sequence of views along the specified axis
     */
    fun along(axis: Int): Sequence<F64Array> = (0 until shape[axis]).asSequence().map { view(it, axis) }

    /**
     * Returns a view of the array along the specified axis.
     *
     * @param index the index along the axis
     * @param axis the axis to view along
     * @return a view of the array along the specified axis
     */
    fun view(index: Int, axis: Int = 0): F64Array = unsupported()

    /**
     * Returns an indexable viewer for the array to avoid chaining [view] calls.
     *
     * @see Viewer
     */
    @Suppress("PropertyName")
    val V: Viewer

    /**
     * Creates a copy of the array.
     *
     * @return a copy of the array
     */
    fun copy(): F64Array = F64Array.create(data.copyOf(), offset, strides.copyOf(), shape.copyOf())

    /**
     * Copies the array to another array.
     *
     * @param other the array to copy to, must have the same shape as this array
     */
    fun copyTo(other: F64Array) = other.zipTransformInPlace(this) { _, d -> d }

    /**
     * Reshapes the array to the specified shape.
     *
     * @param shape the new shape
     * @return the reshaped array
     */
    fun reshape(vararg shape: Int): F64Array = flatten().reshape(*shape)

    /**
     * Reshapes the array to the specified shape.
     *
     * @param rows the number of rows
     * @param cols the number of columns
     * @return the reshaped array
     * @since 1.2.0
     */
    fun reshape(rows: Int, cols: Int): F64TwoAxisArray = flatten().reshape(rows, cols)

    /**
     * Flattens the array to a 1D array.
     *
     * @return a flattened [F64FlatArray]
     */
    fun flatten(): F64FlatArray = unsupported()

    /**
     * Slices the array along the specified axis.
     *
     * @param from the starting index
     * @param to the ending index
     * @param step the step size
     * @param axis the axis to slice along
     * @return a slice of the array along the specified axis
     */
    fun slice(from: Int, to: Int, step: Int = 1, axis: Int = 0): F64Array

    /**
     * Checks if the array contains the specified value.
     *
     * @param other the value to check for
     * @return `true` if the value is in the array, `false` otherwise
     */
    operator fun contains(other: Double): Boolean

    /**
     * Fills the array with the specified value.
     *
     * @param value the value to fill the array with
     */
    fun fill(value: Double) = transformInPlace { value }

    /**
     * Reorders the array along the specified axis.
     *
     * @param indices the indices to reorder the array with
     * @param axis the axis to reorder along
     */
    fun reorder(indices: IntArray, axis: Int = 0): Unit = unsupported()

    /**
     * Transposes the array along the specified axes.
     *
     * @param ax1 the first axis
     * @param ax2 the second axis
     * @return the transposed array
     * @since 1.0.7
     */
    fun transpose(ax1: Int = 0, ax2: Int = 1): F64Array

    /**
     * Returns the sum of all elements in the array.
     *
     * @return the sum of all elements
     */
    fun sum(): Double = reduce { acc, d -> acc + d }

    /**
     * Returns the minimum element in the array.
     *
     * @return the minimum element
     */
    fun min(): Double = fold(Double.POSITIVE_INFINITY) { acc, d -> if (d < acc) d else acc }

    /**
     * Returns the maximum element in the array.
     *
     * @return the maximum element
     */
    fun max(): Double = fold(Double.NEGATIVE_INFINITY) { acc, d -> if (d > acc) d else acc }

    /**
     * Returns the product of all elements in the array.
     *
     * @return the product of all elements
     */
    fun product(): Double = reduce { acc, d -> acc * d }

    /**
     * Returns the mean of all elements in the array.
     *
     * @return the mean of all elements
     */
    fun mean(): Double = sum() / shape.product()

    /**
     * Returns the variance of all elements in the array.
     *
     * @return the variance of all elements
     */
    fun variance(): Double = fold(0.0) { acc, d -> acc + (d - mean()).pow(2) } / shape.product()

    /**
     * Returns the standard deviation of all elements in the array.
     *
     * @return the standard deviation of all elements
     */
    fun stdDev(): Double = sqrt(variance())

    /**
     * Calculates the cumulative sum of the array in place.
     */
    fun cumSumInPlace() {
        var sum = 0.0
        transformInPlace { sum += it; sum }
    }

    /**
     * Calculates the cumulative sum of the array.
     *
     * @return the cumulative sum of the array
     */
    fun cumSum(): F64Array = copy().apply { cumSumInPlace() }

    /**
     * Coerces the array to the specified range in place.
     *
     * @param min the minimum value
     * @param max the maximum value
     */
    fun coerceInPlace(min: Double, max: Double) = transformInPlace { it.coerceIn(min, max) }

    /**
     * Coerces the array to the specified range.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the coerced array
     */
    fun coerce(min: Double, max: Double): F64Array = copy().apply { coerceInPlace(min, max) }

    /**
     * Transforms the array in place.
     *
     * @param transform the transformation function
     */
    fun transformInPlace(transform: (Double) -> Double)

    /**
     * Transforms the array.
     *
     * @param transform the transformation function
     * @return the transformed array
     */
    fun transform(transform: (Double) -> Double): F64Array = copy().apply { transformInPlace(transform) }

    /**
     * Zips the array with another array and transforms it in place.
     *
     * @param other the other array, must have the same shape as this array
     * @param transform the transformation function
     */
    fun zipTransformInPlace(other: F64Array, transform: (Double, Double) -> Double)

    /**
     * Zips the array with another array and transforms it.
     *
     * @param other the other array, must have the same shape as this array
     * @param transform the transformation function
     * @return the transformed array
     */
    fun zipTransform(other: F64Array, transform: (Double, Double) -> Double): F64Array = copy().apply { zipTransformInPlace(other, transform) }

    /**
     * Folds the array with an initial value and an operation.
     *
     * @param initial the initial value
     * @param operation the operation
     * @return the folded value
     */
    fun <T> fold(initial: T, operation: (acc: T, Double) -> T): T

    /**
     * Reduces the array with an operation.
     *
     * @param operation the operation
     * @return the reduced value
     */
    fun reduce(operation: (Double, Double) -> Double): Double

    /**
     * Reduces the array with an operation along the given axis.
     *
     * @param axis the axis to reduce
     * @param operation the operation
     * @return a new array containing the reduced values
     * @since 1.1.1
     */
    fun reduce(axis: Int, operation: (Double, Double) -> Double): F64Array

    /**
     * Scans the array in-place with an operation.
     * Scan is like reduce, but it saves the intermediate values.
     * For example, cumsum might be implemented as `scan(Double::plus)`
     *
     * @param operation the operation
     * @since 1.1.1
     */
    fun scan(operation: (Double, Double) -> Double)

    /**
     * Scans the array in-place with an operation along the given axis.
     *
     * Scan is like reduce, but it saves the intermediate values.
     * For example, cumsum might be implemented as `scan(axis, Double::plus)`
     *
     * @param axis the axis to scan along
     * @param operation the operation
     * @since 1.1.1
     */
    fun scan(axis: Int, operation: (Double, Double) -> Double)

    /**
     * Computes e^x for each element in the array in place.
     *
     * @see kotlin.math.exp
     */
    fun expInPlace() = transformInPlace(::exp)

    /**
     * Computes e^x for each element in the array.
     *
     * @return the computed array
     */
    fun exp(): F64Array = copy().apply { expInPlace() }

    /**
     * Computes e^x - 1 for each element in the array in place.
     *
     * @see kotlin.math.expm1
     */
    fun expm1InPlace() = transformInPlace(::expm1)

    /**
     * Computes e^x - 1 for each element in the array.
     *
     * @return the computed array
     */
    fun expm1(): F64Array = copy().apply { expm1InPlace() }

    /**
     * Computes [base]^x for each element in the array in place.
     *
     * @param base the base
     */
    fun expBaseInPlace(base: Double) = transformInPlace { base.pow(it) }

    /**
     * Computes [base]^x for each element in the array.
     *
     * @param base the base
     * @return the computed array
     */
    fun expBase(base: Double): F64Array = copy().apply { expBaseInPlace(base) }

    /**
     * Computes ln(x) for each element in the array in place.
     *
     * @see kotlin.math.ln
     */
    fun logInPlace() = transformInPlace(::ln)
    // Alias
    fun lnInPlace() = logInPlace()

    /**
     * Computes ln(x) for each element in the array.
     *
     * @return the computed array
     */
    fun log(): F64Array = copy().apply { logInPlace() }
    // Alias
    fun ln() = log()

    /**
     * Computes ln(1 + x) for each element in the array in place.
     *
     * @see kotlin.math.ln
     */
    fun log1pInPlace() = transformInPlace { ln(1 + it) }

    /**
     * Computes ln(1 + x) for each element in the array.
     *
     * @return the computed array
     */
    fun log1p(): F64Array = copy().apply { log1pInPlace() }

    /**
     * Computes log2(x) for each element in the array in place.
     *
     * @see kotlin.math.log2
     */
    fun log2InPlace() = transformInPlace(::log2)

    /**
     * Computes log2(x) for each element in the array.
     *
     * @return the computed array
     */
    fun log2(): F64Array = copy().apply { log2InPlace() }

    /**
     * Computes log10(x) for each element in the array in place.
     *
     * @see kotlin.math.log10
     */
    fun log10InPlace() = transformInPlace(::log10)

    /**
     * Computes log10(x) for each element in the array.
     *
     * @return the computed array
     */
    fun log10(): F64Array = copy().apply { log10InPlace() }

    /**
     * Computes log(x) / log(base) for each element in the array in place.
     *
     * @param base the base
     */
    // TODO: On some systems log2 is fastest, on others it's slowest? Needs investigation
    fun logBaseInPlace(base: Double) = transformInPlace { log2(it) / log2(base) }

    /**
     * Computes log(x) / log(base) for each element in the array.
     *
     * @param base the base
     * @return the computed array
     */
    fun logBase(base: Double): F64Array = copy().apply { logBaseInPlace(base) }

    /**
     * Computes the square root of each element in the array in place.
     *
     * @see kotlin.math.sqrt
     */
    fun sqrtInPlace() = transformInPlace(::sqrt)

    /**
     * Computes the square root of each element in the array.
     *
     * @return the computed array
     */
    fun sqrt(): F64Array = copy().apply { sqrtInPlace() }

    /**
     * Computes x^[power] for each element in the array in place.
     *
     * @param power the power
     * @see kotlin.math.pow
     */
    fun powInPlace(power: Double) = transformInPlace { it.pow(power) }

    /**
     * Computes x^[power] for each element in the array.
     *
     * @param power the power
     * @return the computed array
     */
    fun pow(power: Double): F64Array = copy().apply { powInPlace(power) }

    operator fun unaryPlus(): F64Array = this

    /**
     * Negates each element in the array in place.
     */
    fun unaryMinusInPlace() = transformInPlace(Double::unaryMinus)

    /**
     * Negates each element in the array.
     *
     * @return the negated array
     */
    operator fun unaryMinus(): F64Array = copy().apply { unaryMinusInPlace() }

    /**
     * Adds another array to this array in place.
     *
     * @param other the other array, must have the same shape as this array
     */
    operator fun plusAssign(other: F64Array) = zipTransformInPlace(other) { a, b -> a + b }

    /**
     * Adds another array to this array.
     *
     * @param other the other array, must have the same shape as this array
     * @return the sum of the arrays
     */
    operator fun plus(other: F64Array): F64Array = copy().apply { plusAssign(other) }

    /**
     * Adds a scalar to each element in the array in place.
     *
     * @param other the scalar
     */
    operator fun plusAssign(other: Double) = transformInPlace { it + other }

    /**
     * Adds a scalar to each element in the array.
     *
     * @param other the scalar
     * @return the sum of the array and the scalar
     */
    operator fun plus(other: Double): F64Array = copy().apply { plusAssign(other) }

    /**
     * Subtracts another array from this array in place.
     *
     * @param other the other array, must have the same shape as this array
     */
    operator fun minusAssign(other: F64Array) = zipTransformInPlace(other) { a, b -> a - b }

    /**
     * Subtracts another array from this array.
     *
     * @param other the other array, must have the same shape as this array
     * @return the difference of the arrays
     */
    operator fun minus(other: F64Array): F64Array = copy().apply { minusAssign(other) }

    /**
     * Subtracts a scalar from each element in the array in place.
     *
     * @param other the scalar
     */
    operator fun minusAssign(other: Double) = transformInPlace { it - other }

    /**
     * Subtracts a scalar from each element in the array.
     *
     * @param other the scalar
     * @return the difference of the array and the scalar
     */
    operator fun minus(other: Double): F64Array = copy().apply { minusAssign(other) }

    /**
     * Multiplies another array with this array in place.
     * This is element-wise multiplication, not matrix multiplication.
     *
     * @param other the other array, must have the same shape as this array
     */
    operator fun timesAssign(other: F64Array) = zipTransformInPlace(other) { a, b -> a * b }

    /**
     * Multiplies another array with this array.
     * This is element-wise multiplication, not matrix multiplication.
     *
     * @param other the other array, must have the same shape as this array
     * @return the product of the arrays
     */
    operator fun times(other: F64Array): F64Array = copy().apply { timesAssign(other) }

    /**
     * Multiplies each element in the array with a scalar in place.
     *
     * @param other the scalar
     */
    operator fun timesAssign(other: Double) = transformInPlace { it * other }

    /**
     * Multiplies each element in the array with a scalar.
     *
     * @param other the scalar
     * @return the product of the array and the scalar
     */
    operator fun times(other: Double): F64Array = copy().apply { timesAssign(other) }

    /**
     * Divides this array by another array in place.
     *
     * @param other the other array, must have the same shape as this array
     */
    operator fun divAssign(other: F64Array) = zipTransformInPlace(other) { a, b -> a / b }

    /**
     * Divides this array by another array.
     *
     * @param other the other array, must have the same shape as this array
     * @return the quotient of the arrays
     */
    operator fun div(other: F64Array): F64Array = copy().apply { divAssign(other) }

    /**
     * Divides each element in the array by a scalar in place.
     *
     * @param other the scalar
     */
    operator fun divAssign(other: Double) = transformInPlace { it / other }

    /**
     * Divides each element in the array by a scalar.
     *
     * @param other the scalar
     * @return the quotient of the array and the scalar
     */
    operator fun div(other: Double): F64Array = copy().apply { divAssign(other) }

    /**
     * Divides this array by another array in plac and takes the remainder.
     *
     * @param other the other array, must have the same shape as this array
     * @since 1.1.1
     */
    operator fun remAssign(other: F64Array) = zipTransformInPlace(other) { a, b -> a % b }

    /**
     * Divides this array by another array and takes the remainder. Both arrays must have the same shape.
     *
     * @param other the other array, must have the same shape as this array
     * @since 1.1.1
     */
    operator fun rem(other: F64Array) = copy().apply { remAssign(other) }

    /**
     * Divides each element with a scalar and takes the remainder.
     *
     * @param other the scalar
     * @since 1.1.1
     */
    operator fun remAssign(other: Double) = transformInPlace { it % other }

    /**
     * Divides each element with a scalar and takes the remainder.
     *
     * @param other the scalar
     * @since 1.1.1
     */
    operator fun rem(other: Double) = copy().apply { remAssign(other) }

    /**
     * Computes the absolute value of each element in the array in place.
     *
     * @see kotlin.math.abs
     */
    fun absInPlace() = transformInPlace(::abs)

    /**
     * Computes the absolute value of each element in the array.
     *
     * @return the computed array
     */
    fun abs(): F64Array = copy().apply { absInPlace() }

    /**
     * Compares if each element in the array is less than another array in place.
     *
     * @param other the other array, must have the same shape as this array
     */
    fun ltInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a < b) 1.0 else 0.0 }

    /**
     * Compares if each element in the array is less than another array.
     *
     * @param other the other array, must have the same shape as this array
     * @return the computed array, where 1.0 is true and 0.0 is false
     */
    infix fun lt(other: F64Array): F64Array = copy().apply { ltInPlace(other) }

    /**
     * Compares if each element in the array is less than a scalar in place.
     *
     * @param other the scalar
     */
    fun ltInPlace(other: Double) = transformInPlace { if (it < other) 1.0 else 0.0 }

    /**
     * Compares if each element in the array is less than a scalar.
     *
     * @param other the scalar
     * @return the computed array, where 1.0 is true and 0.0 is false
     */
    infix fun lt(other: Double): F64Array = copy().apply { ltInPlace(other) }

    /**
     * Compares if each element in the array is less than or equal to another array in place.
     *
     * @param other the other array, must have the same shape as this array
     */
    fun lteInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a <= b) 1.0 else 0.0 }

    /**
     * Compares if each element in the array is less than or equal to another array.
     *
     * @param other the other array, must have the same shape as this array
     * @return the computed array, where 1.0 is true and 0.0 is false
     */
    infix fun lte(other: F64Array): F64Array = copy().apply { lteInPlace(other) }

    /**
     * Compares if each element in the array is less than or equal to a scalar in place.
     *
     * @param other the scalar
     */
    fun lteInPlace(other: Double) = transformInPlace { if (it <= other) 1.0 else 0.0 }

    /**
     * Compares if each element in the array is less than or equal to a scalar.
     *
     * @param other the scalar
     * @return the computed array, where 1.0 is true and 0.0 is false
     */
    infix fun lte(other: Double): F64Array = copy().apply { lteInPlace(other) }

    /**
     * Compares if each element in the array is greater than another array in place.
     *
     * @param other the other array, must have the same shape as this array
     */
    fun gtInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a > b) 1.0 else 0.0 }

    /**
     * Compares if each element in the array is greater than another array.
     *
     * @param other the other array, must have the same shape as this array
     * @return the computed array, where 1.0 is true and 0.0 is false
     */
    infix fun gt(other: F64Array): F64Array = copy().apply { gtInPlace(other) }

    /**
     * Compares if each element in the array is greater than a scalar in place.
     *
     * @param other the scalar
     */
    fun gtInPlace(other: Double) = transformInPlace { if (it > other) 1.0 else 0.0 }

    /**
     * Compares if each element in the array is greater than a scalar.
     *
     * @param other the scalar
     * @return the computed array, where 1.0 is true and 0.0 is false
     */
    infix fun gt(other: Double): F64Array = copy().apply { gtInPlace(other) }

    /**
     * Compares if each element in the array is greater than or equal to another array in place.
     *
     * @param other the other array, must have the same shape as this array
     */
    fun gteInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a >= b) 1.0 else 0.0 }
    /**
     * Compares if each element in the array is greater than or equal to another array.
     *
     * @param other the other array, must have the same shape as this array
     * @return the computed array, where 1.0 is true and 0.0 is false
     */
    infix fun gte(other: F64Array): F64Array = copy().apply { gteInPlace(other) }

    /**
     * Compares if each element in the array is greater than or equal to a scalar in place.
     *
     * @param other the scalar
     */
    fun gteInPlace(other: Double) = transformInPlace { if (it >= other) 1.0 else 0.0 }

    /**
     * Compares if each element in the array is greater than or equal to a scalar.
     *
     * @param other the scalar
     * @return the computed array, where 1.0 is true and 0.0 is false
     */
    infix fun gte(other: Double): F64Array = copy().apply { gteInPlace(other) }

    /**
     * Compares if each element in the array is equal to another array in place.
     *
     * @param other the other array, must have the same shape as this array
     */
    fun eqInPlace(other: F64Array) {
        val (rtol, atol) = F64Array.tolerance
        val allowNan = F64Array.equalNan
        zipTransformInPlace(other) { a, b -> if (abs(a - b) <= (atol + rtol * abs(b)) || (allowNan && a.isNaN() && b.isNaN())) 1.0 else 0.0 }
    }

    /**
     * Compares if each element in the array is equal to another array.
     *
     * @param other the other array, must have the same shape as this array
     * @return the computed array, where 1.0 is true and 0.0 is false
     */
    infix fun eq(other: F64Array): F64Array = copy().apply { eqInPlace(other) }

    /**
     * Compares if each element in the array is equal to a scalar in place.
     *
     * @param other the scalar
     */
    fun eqInPlace(other: Double) {
        val (rtol, atol) = F64Array.tolerance
        val allowNan = F64Array.equalNan && other.isNaN()
        transformInPlace { a -> if (abs(a - other) <= (atol + rtol * abs(other)) || (allowNan && a.isNaN())) 1.0 else 0.0 }
    }

    /**
     * Compares if each element in the array is equal to a scalar.
     *
     * @param other the scalar
     * @return the computed array, where 1.0 is true and 0.0 is false
     */
    infix fun eq(other: Double): F64Array = copy().apply { eqInPlace(other) }

    /**
     * Compares if each element in the array is not equal to another array in place.
     *
     * @param other the other array, must have the same shape as this array
     */
    fun neqInPlace(other: F64Array) {
        val (rtol, atol) = F64Array.tolerance
        val allowNan = F64Array.equalNan
        zipTransformInPlace(other) { a, b -> if (abs(a - b) <= (atol + rtol * abs(b)) || (allowNan && a.isNaN() && b.isNaN())) 0.0 else 1.0 }
    }

    /**
     * Compares if each element in the array is not equal to another array.
     *
     * @param other the other array, must have the same shape as this array
     * @return the computed array, where 1.0 is true and 0.0 is false
     */
    infix fun neq(other: F64Array): F64Array = copy().apply { neqInPlace(other) }

    /**
     * Compares if each element in the array is not equal to a scalar in place.
     *
     * @param other the scalar
     */
    fun neqInPlace(other: Double) {
        val (rtol, atol) = F64Array.tolerance
        val allowNan = F64Array.equalNan && other.isNaN()
        transformInPlace { a -> if (abs(a - other) <= (atol + rtol * abs(other)) || (allowNan && a.isNaN())) 0.0 else 1.0 }
    }

    /**
     * Compares if each element in the array is not equal to a scalar.
     *
     * @param other the scalar
     * @return the computed array, where 1.0 is true and 0.0 is false
     */
    infix fun neq(other: Double): F64Array = copy().apply { neqInPlace(other) }

    /**
     * Compares if each element in the array is NaN in place.
     *
     * @see kotlin.Double.isNaN
     */
    fun isNanInPlace() = transformInPlace { if (it.isNaN()) 1.0 else 0.0 }

    /**
     * Compares if each element in the array is NaN.
     *
     * @return the computed array, where 1.0 is true and 0.0 is false
     */
    fun isNan(): F64Array = copy().apply { isNanInPlace() }

    /**
     * Compares if each element in the array is infinite in place.
     *
     * @see kotlin.Double.isInfinite
     */
    fun isInfInPlace() = transformInPlace { if (it.isInfinite()) 1.0 else 0.0 }

    /**
     * Compares if each element in the array is infinite.
     *
     * @return the computed array, where 1.0 is true and 0.0 is false
     */
    fun isInf(): F64Array = copy().apply { isInfInPlace() }

    /**
     * Compares if each element in the array is finite in place.
     *
     * @see kotlin.Double.isFinite
     */
    fun isFiniteInPlace() = transformInPlace { if (it.isFinite()) 1.0 else 0.0 }

    /**
     * Compares if each element in the array is finite.
     *
     * @return the computed array, where 1.0 is true and 0.0 is false
     */
    fun isFinite(): F64Array = copy().apply { isFiniteInPlace() }

    /**
     * Computes the bitwise and of each element in the array with another array in place.
     * The values are first cast to integers before the operation.
     *
     * @param other the other array, must have the same shape as this array
     */
    fun andInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a != 0.0 && b != 0.0) 1.0 else 0.0 }

    /**
     * Computes the bitwise and of each element in the array with another array.
     * The values are first cast to integers before the operation.
     *
     * @param other the other array, must have the same shape as this array
     * @return the computed array
     */
    infix fun and(other: F64Array): F64Array = copy().apply { andInPlace(other) }

    /**
     * Computes the bitwise and of each element in the array with a scalar in place.
     * The values are first cast to integers before the operation.
     *
     * @param other the scalar
     */
    fun andInPlace(other: Int) = transformInPlace { (it.toInt() and other).toDouble() }

    /**
     * Computes the bitwise and of each element in the array with a scalar.
     * The values are first cast to integers before the operation.
     *
     * @param other the scalar
     * @return the computed array
     */
    infix fun and(other: Int): F64Array = copy().apply { andInPlace(other) }

    /**
     * Computes the bitwise or of each element in the array with another array in place.
     * The values are first cast to integers before the operation.
     *
     * @param other the other array, must have the same shape as this array
     */
    fun orInPlace(other: F64Array) = transformInPlace { (it.toInt() or other[it.toInt()].toInt()).toDouble() }

    /**
     * Computes the bitwise or of each element in the array with another array.
     * The values are first cast to integers before the operation.
     *
     * @param other the other array, must have the same shape as this array
     * @return the computed array
     */
    infix fun or(other: F64Array): F64Array = copy().apply { orInPlace(other) }

    /**
     * Computes the bitwise or of each element in the array with a scalar in place.
     * The values are first cast to integers before the operation.
     *
     * @param other the scalar
     */
    fun orInPlace(other: Int) = transformInPlace { (it.toInt() or other).toDouble() }

    /**
     * Computes the bitwise or of each element in the array with a scalar.
     * The values are first cast to integers before the operation.
     *
     * @param other the scalar
     * @return the computed array
     */
    infix fun or(other: Int): F64Array = copy().apply { orInPlace(other) }

    /**
     * Computes the bitwise xor of each element in the array with another array in place.
     * The values are first cast to integers before the operation.
     *
     * @param other the other array, must have the same shape as this array
     */
    fun xorInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> if (a != b) 1.0 else 0.0 }

    /**
     * Computes the bitwise xor of each element in the array with another array.
     * The values are first cast to integers before the operation.
     *
     * @param other the other array, must have the same shape as this array
     * @return the computed array
     */
    infix fun xor(other: F64Array): F64Array = copy().apply { xorInPlace(other) }

    /**
     * Computes the bitwise xor of each element in the array with a scalar in place.
     * The values are first cast to integers before the operation.
     *
     * @param other the scalar
     */
    fun xorInPlace(other: Int) = transformInPlace { (it.toInt() xor other).toDouble() }

    /**
     * Computes the bitwise xor of each element in the array with a scalar.
     * The values are first cast to integers before the operation.
     *
     * @param other the scalar
     * @return the computed array
     */
    infix fun xor(other: Int): F64Array = copy().apply { xorInPlace(other) }

    /**
     * Computes the bitwise not of each element in the array in place.
     * The values are first cast to integers before the operation.
     */
    fun notInPlace() = transformInPlace { it.toInt().inv().toDouble() }

    /**
     * Computes the bitwise not of each element in the array.
     * The values are first cast to integers before the operation.
     *
     * @return the computed array
     */
    fun not(): F64Array = copy().apply { notInPlace() }

    /**
     * Shifts each element in the array to the left by the elements in another array in place.
     * The values of both arrays are first cast to integers before the operation.
     *
     * @param other the other array, must have the same shape as this array
     */
    fun shlInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> (a.toInt() shl b.toInt()).toDouble() }

    /**
     * Shifts each element in the array to the left by the elements in another array.
     * The values of both arrays are first cast to integers before the operation.
     *
     * @param other the other array, must have the same shape as this array
     * @return the computed array
     */
    infix fun shl(other: F64Array): F64Array = copy().apply { shlInPlace(other) }

    /**
     * Shifts each element in the array to the left by a scalar in place.
     * The values are first cast to integers before the operation.
     *
     * @param other the scalar
     */
    fun shlInPlace(other: Int) = transformInPlace { (it.toInt() shl other).toDouble() }

    /**
     * Shifts each element in the array to the left by a scalar.
     * The values are first cast to integers before the operation.
     *
     * @param other the scalar
     * @return the computed array
     */
    infix fun shl(other: Int): F64Array = copy().apply { shlInPlace(other) }

    /**
     * Shifts each element in the array to the right by the elements in another array in place.
     * The values of both arrays are first cast to integers before the operation.
     *
     * @param other the other array, must have the same shape as this array
     */
    fun shrInPlace(other: F64Array) = zipTransformInPlace(other) { a, b -> (a.toInt() shr b.toInt()).toDouble() }

    /**
     * Shifts each element in the array to the right by the elements in another array.
     * The values of both arrays are first cast to integers before the operation.
     *
     * @param other the other array, must have the same shape as this array
     * @return the computed array
     */
    infix fun shr(other: F64Array): F64Array = copy().apply { shrInPlace(other) }

    /**
     * Shifts each element in the array to the right by a scalar in place.
     * The values are first cast to integers before the operation.
     *
     * @param other the scalar
     */
    fun shrInPlace(other: Int) = transformInPlace { (it.toInt() shr other).toDouble() }

    /**
     * Shifts each element in the array to the right by a scalar.
     * The values are first cast to integers before the operation.
     *
     * @param other the scalar
     * @return the computed array
     */
    infix fun shr(other: Int): F64Array = copy().apply { shrInPlace(other) }

    /**
     * Computes the floor of each element in the array in place.
     *
     * @see kotlin.math.floor
     */
    fun floorInPlace() = transformInPlace(::floor)

    /**
     * Computes the floor of each element in the array.
     *
     * @return the computed array
     */
    fun floor(): F64Array = copy().apply { floorInPlace() }

    /**
     * Computes the ceiling of each element in the array in place.
     *
     * @see kotlin.math.ceil
     */
    fun ceilInPlace() = transformInPlace(::ceil)

    /**
     * Computes the ceiling of each element in the array.
     *
     * @return the computed array
     */
    fun ceil(): F64Array = copy().apply { ceilInPlace() }

    /**
     * Truncates each element in the array in place.
     *
     * @see kotlin.math.truncate
     */
    fun truncInPlace() = transformInPlace(::truncate)

    /**
     * Truncates each element in the array.
     *
     * @return the computed array
     */
    fun trunc(): F64Array = copy().apply { truncInPlace() }

    /**
     * Rounds each element in the array in place.
     *
     * @see kotlin.math.round
     */
    fun roundInPlace() = transformInPlace(::round)

    /**
     * Rounds each element in the array.
     *
     * @return the computed array
     */
    fun round(): F64Array = copy().apply { roundInPlace() }

    /**
     * Computes the sine of each element in the array in place.
     *
     * @see kotlin.math.sin
     */
    fun sinInPlace() = transformInPlace(::sin)

    /**
     * Computes the sine of each element in the array.
     *
     * @return the computed array
     */
    fun sin(): F64Array = copy().apply { sinInPlace() }

    /**
     * Computes the cosine of each element in the array in place.
     *
     * @see kotlin.math.cos
     */
    fun cosInPlace() = transformInPlace(::cos)

    /**
     * Computes the cosine of each element in the array.
     *
     * @return the computed array
     */
    fun cos(): F64Array = copy().apply { cosInPlace() }

    /**
     * Computes the tangent of each element in the array in place.
     *
     * @see kotlin.math.tan
     */
    fun tanInPlace() = transformInPlace(::tan)

    /**
     * Computes the tangent of each element in the array.
     *
     * @return the computed array
     */
    fun tan(): F64Array = copy().apply { tanInPlace() }

    /**
     * Computes the arcsine of each element in the array in place.
     *
     * @see kotlin.math.asin
     */
    fun asinInPlace() = transformInPlace(::asin)

    /**
     * Computes the arcsine of each element in the array.
     *
     * @return the computed array
     */
    fun asin(): F64Array = copy().apply { asinInPlace() }

    /**
     * Computes the arccosine of each element in the array in place.
     *
     * @see kotlin.math.acos
     */
    fun acosInPlace() = transformInPlace(::acos)

    /**
     * Computes the arccosine of each element in the array.
     *
     * @return the computed array
     */
    fun acos(): F64Array = copy().apply { acosInPlace() }

    /**
     * Computes the arctangent of each element in the array in place.
     *
     * @see kotlin.math.atan
     */
    fun atanInPlace() = transformInPlace(::atan)

    /**
     * Computes the arctangent of each element in the array.
     *
     * @return the computed array
     */
    fun atan(): F64Array = copy().apply { atanInPlace() }

    /**
     * Computes the 2nd-argument arctangent of each element in the array in place.
     *
     * @param other the other array, must have the same shape as this array
     * @see kotlin.math.atan2
     */
    fun atan2InPlace(other: F64Array) = zipTransformInPlace(other, ::atan2)

    /**
     * Computes the 2nd-argument arctangent of each element in the array.
     *
     * @param other the other array, must have the same shape as this array
     * @return the computed array
     * @see kotlin.math.atan2
     */
    fun atan2(other: F64Array): F64Array = copy().apply { atan2InPlace(other) }

    /**
     * Computes the hyperbolic sine of each element in the array in place.
     *
     * @see kotlin.math.sinh
     */
    fun sinhInPlace() = transformInPlace(::sinh)

    /**
     * Computes the hyperbolic sine of each element in the array.
     *
     * @return the computed array
     */
    fun sinh(): F64Array = copy().apply { sinhInPlace() }

    /**
     * Computes the hyperbolic cosine of each element in the array in place.
     *
     * @see kotlin.math.cosh
     */
    fun coshInPlace() = transformInPlace(::cosh)

    /**
     * Computes the hyperbolic cosine of each element in the array.
     *
     * @return the computed array
     */
    fun cosh(): F64Array = copy().apply { coshInPlace() }

    /**
     * Computes the hyperbolic tangent of each element in the array in place.
     *
     * @see kotlin.math.tanh
     */
    fun tanhInPlace() = transformInPlace(::tanh)

    /**
     * Computes the hyperbolic tangent of each element in the array.
     *
     * @return the computed array
     */
    fun tanh(): F64Array = copy().apply { tanhInPlace() }

    /**
     * Computes the hyperbolic arcsine of each element in the array in place.
     *
     * @see kotlin.math.asinh
     */
    fun asinhInPlace() = transformInPlace(::asinh)

    /**
     * Computes the hyperbolic arcsine of each element in the array.
     *
     * @return the computed array
     */
    fun asinh(): F64Array = copy().apply { asinhInPlace() }

    /**
     * Computes the hyperbolic arccosine of each element in the array in place.
     *
     * @see kotlin.math.acosh
     */
    fun acoshInPlace() = transformInPlace(::acosh)

    /**
     * Computes the hyperbolic arccosine of each element in the array.
     *
     * @return the computed array
     */
    fun acosh(): F64Array = copy().apply { acoshInPlace() }

    /**
     * Computes the hyperbolic arctangent of each element in the array in place.
     *
     * @see kotlin.math.atanh
     */
    fun atanhInPlace() = transformInPlace(::atanh)

    /**
     * Computes the hyperbolic arctangent of each element in the array.
     *
     * @return the computed array
     */
    fun atanh(): F64Array = copy().apply { atanhInPlace() }

    /**
     * Computes the hypothenuse of each element in the array with another array in place.
     *
     * @param other the other array, must have the same shape as this array
     * @see kotlin.math.hypot
     */
    fun hypotInPlace(other: F64Array) = zipTransformInPlace(other, ::hypot)

    /**
     * Computes the hypothenuse of each element in the array with another array.
     *
     * @param other the other array, must have the same shape as this array
     * @return the computed array
     */
    fun hypot(other: F64Array): F64Array = copy().apply { hypotInPlace(other) }

    /**
     * Returns the data from the array as a flat array.
     */
    fun toDoubleArray(): DoubleArray = unsupported()

    fun toString(maxDisplay: Int): String = unsupported()

    companion object {
        /**
         * The minimum size of a flat array to use SIMD operations.
         * Depends on the System, but usually 8 or 16.
         */
        val simdSize by lazy {
            if (NativeSpeedup.getSimdAvailable()) {
                NativeSpeedup.getSimdSize() * 2
            } else {
                8  // Fallback for Android Unit Test
            }
        }

        /**
         * The relative and absolute tolerance for eq/neq.
         * This is implemented similar to numpy: `absolute(a - b) <= (atol + rtol * absolute(b))`
         *
         * @since 1.2.2
         */
        var tolerance = 1e-05 to 1e-08

        /**
         * Whether or not nan == nan for eq/neq.
         * @since 1.2.2
         */
        var equalNan = false

        /**
         * Creates a new array with the specified shape.
         *
         * @param shape the shape of the array
         * @return the created array
         */
        @JvmStatic
        @JvmName("create")
        operator fun invoke(vararg shape: Int): F64Array = F64FlatArray.create(DoubleArray(shape.product())).reshape(*shape)

        /**
         * Creates a new array with the specified shape.
         *
         * @param rows the number of rows
         * @param cols the number of columns
         * @since 1.2.0
         */
        @JvmStatic
        @JvmName("create")
        operator fun invoke(rows: Int, cols: Int): F64TwoAxisArray = F64FlatArray.create(DoubleArray(rows * cols)).reshape(rows, cols)

        /**
         * Creates a new flat array with the specified size and initializes it with the given function.
         *
         * @param size the size of the array
         * @param init the initialization function
         * @return the created array
         */
        @JvmStatic
        @JvmName("create1D")
        operator fun invoke(size: Int, init: (Int) -> Double): F64FlatArray = F64FlatArray.of(DoubleArray(size, init))

        /**
         * Creates a new 2D array with the specified shape and initializes it with the given function.
         *
         * @param numRows the number of rows
         * @param numColumns the number of columns
         * @param init the initialization function
         * @return the created array
         */
        @JvmStatic
        @JvmName("create2D")
        operator fun invoke(numRows: Int, numColumns: Int, init: (Int, Int) -> Double): F64TwoAxisArray = F64Array(numRows, numColumns).apply {
            for (r in 0 until numRows) {
                for (c in 0 until numColumns) {
                    this[r, c] = init(r, c)
                }
            }
        }

        /**
         * Creates a new 3D array with the specified shape and initializes it with the given function.
         *
         * @param x the size of the first dimension
         * @param y the size of the second dimension
         * @param z the size of the third dimension
         * @param init the initialization function
         * @return the created array
         */
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

        /**
         * Creates a new flat array from the given values.
         *
         * @param values the values
         * @return the created array
         */
        @JvmStatic
        fun of(vararg values: Double) = F64FlatArray.of(values)

        /**
         * Creates a new flat array from the given values.
         *
         * @param data the values
         * @return the created array
         */
        @JvmStatic
        @JvmName("ofArray")
        fun of(data: DoubleArray) = F64FlatArray.of(data)

        /**
         * Creates a new array with the given shape, filled with the given value.
         *
         * @param shape the shape of the array
         * @param init the initialization value
         * @return the created array
         */
        @JvmStatic
        fun full(vararg shape: Int, init: Double): F64Array {
            return F64FlatArray.create(DoubleArray(shape.product()).apply { fill(init) }).reshape(*shape)
        }

        /**
         * Creates a new array with the given shape, filled with the given value.
         *
         * @param rows the row count
         * @param cols the column count
         * @param init the initialization value
         * @return the created array
         * @since 1.2.0
         */
        @JvmStatic
        fun full(rows: Int, cols: Int, init: Double): F64TwoAxisArray {
            return F64FlatArray.create(DoubleArray(rows * cols).apply { fill(init) }).reshape(rows, cols)
        }

        /**
         * Creates a 2D identity matrix of the given size.
         *
         * @param size the size of the matrix
         * @return the created matrix
         */
        @JvmStatic
        fun identity(size: Int): F64TwoAxisArray = zeros(size, size).apply {
            for (i in 0 until size) {
                this[i, i] = 1.0
            }
        }

        /**
         * Creates a 2D diagonal matrix from the given values.
         *
         * @param values the diagonal values
         * @return the created matrix
         */
        @JvmStatic
        fun diagonal(values: DoubleArray): F64TwoAxisArray {
            val n = values.size
            val result = zeros(n, n)
            for (i in 0 until n) {
                result[i, i] = values[i]
            }
            return result
        }

        /**
         * Creates a 2D diagonal matrix from the given values.
         *
         * @param values the diagonal values
         * @return the created matrix
         */
        @JvmStatic
        fun diagonal(values: F64Array): F64TwoAxisArray {
            val n = values.length
            val result = zeros(n, n)
            for (i in 0 until n) {
                result[i, i] = values[i]
            }
            return result
        }

        /**
         * Creates a new array with the given shape, filled with zeros.
         *
         * @param shape the shape of the array
         * @return the created array
         */
        @JvmStatic
        fun zeros(vararg shape: Int): F64Array = full(*shape, init=0.0)

        /**
         * Creates a new array with the given shape, filled with zeros.
         *
         * @param rows the number of rows
         * @param cols the number of columns
         * @return the created array
         * @since 1.2.0
         */
        @JvmStatic
        fun zeros(rows: Int, cols: Int): F64TwoAxisArray = full(rows, cols, init=0.0)

        /**
         * Creates a new array with the given shape, filled with ones.
         *
         * @param shape the shape of the array
         * @return the created array
         */
        @JvmStatic
        fun ones(vararg shape: Int): F64Array = full(*shape, init=1.0)

        /**
         * Creates a new array with the given shape, filled with ones.
         *
         * @param rows the number of rows
         * @param cols the number of columns
         * @return the created array
         * @since 1.2.0
         */
        @JvmStatic
        fun ones(rows: Int, cols: Int): F64TwoAxisArray = full(rows, cols, init=1.0)

        /**
         * Creates a new array from the given list of rows.
         *
         * @param rows the rows of the array
         * @return the created array
         */
        @JvmStatic
        fun ofRows(rows: List<DoubleArray>): F64TwoAxisArray = ofRows(rows.map { F64FlatArray.of(it) })

        /**
         * Creates a new array from the given list of rows.
         *
         * @param rows the rows of the array
         * @return the created array
         */
        @JvmStatic
        @JvmName("ofRowsArray")
        fun ofRows(rows: List<F64Array>): F64TwoAxisArray {
            val args = rows.map { it.reshape(1, *it.shape) }
            return concat(args[0], *args.slice(1 until args.size).toTypedArray(), axis = 0) as F64TwoAxisArray
        }

        /**
         * Concatenates the given arrays along the specified axis.
         *
         * @param first the first array
         * @param rest the rest of the arrays
         * @param axis the axis to concatenate along
         * @return the concatenated array
         */
        @JvmStatic
        @JvmOverloads
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

        /**
         * Concatenates the given arrays along the specified axis.
         *
         * @param first the first array
         * @param rest the rest of the arrays
         * @param axis the axis to concatenate along
         * @return the concatenated array
         * @since 1.3.0
         */
        @JvmStatic
        @JvmOverloads
        fun concat(first: F64TwoAxisArray, vararg rest: F64TwoAxisArray, axis: Int = 0): F64TwoAxisArray {
            for (other in rest) {
                require(other.shape.remove(axis).contentEquals(first.shape.remove(axis))) {
                    "input array shapes must be exactly equal for all dimensions except $axis"
                }
            }

            val shape = first.shape.copyOf().apply {
                this[axis] = first.shape[axis] + rest.sumOf { it.shape[axis] }
            }

            val result = invoke(shape[0], shape[1])
            var offset = 0
            for (a in arrayOf(first, *rest)) {
                if (a.length > 0) {
                    a.copyTo(result.slice(offset, offset + a.shape[axis], axis = axis))
                    offset += a.shape[axis]
                }
            }

            return result
        }

        /**
         * Creates an array of the given shape with random values.
         * The values are uniformly distributed between 0 (inclusive) and 1 (exclusive).
         *
         * @param shape the shape of the array
         * @param random the random source
         * @return the created array
         * @since 1.0.7
         */
        @JvmStatic
        @JvmOverloads
        fun random(vararg shape: Int, random: Random = Random): F64Array = F64FlatArray.create(DoubleArray(shape.product()) { random.nextDouble() }).reshape(*shape)

        /**
         * Creates an array of the given shape with random values.
         * The values are uniformly distributed between 0 (inclusive) and 1 (exclusive).
         *
         * @param rows the number of rows
         * @param cols the number of columns
         * @param random the random source
         * @return the created array
         * @since 1.2.0
         */
        @JvmStatic
        @JvmOverloads
        fun random(rows: Int, cols: Int, random: Random = Random): F64TwoAxisArray = F64FlatArray.create(DoubleArray(rows * cols) { random.nextDouble() }).reshape(rows, cols)

        /**
         * Creates an array with a linear range of values.
         *
         * @param start the start value
         * @param stop the stop value
         * @param num the number of values
         * @return the created array
         * @since 1.0.7
         */
        @JvmStatic
        @JvmOverloads
        fun linear(start: Double, stop: Double, num: Int = 50): F64FlatArray {
            require(num > 0) { "num must be positive" }
            val step = (stop - start) / (num - 1)
            return F64FlatArray.of(DoubleArray(num) { start + it * step })
        }

        /**
         * Creates an array with a linear range of values.
         *
         * @param range the range of values
         * @return the created array
         * @since 1.1.1
         */
        @JvmStatic
        fun linear(range: IntRange): F64FlatArray {
            require(!range.isEmpty()) { "range must not be empty" }
            return linear(range.first.toDouble(), range.last.toDouble(), range.count())
        }
    }
}
