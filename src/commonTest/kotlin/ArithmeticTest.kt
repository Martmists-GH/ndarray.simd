import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.impl.F64LargeDenseFlatArrayImpl
import kotlin.test.Test
import kotlin.test.assertContentEquals

class ArithmeticTest {
    @Test
    fun `Test Array + Array`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 1.0 }, 0, F64Array.simdSize)
        val arr2 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 1.0 }, 0, F64Array.simdSize)
        val arr3 = arr1 + arr2
        assertContentEquals(arr3.toDoubleArray(), DoubleArray(F64Array.simdSize) { 2.0 })
    }

    @Test
    fun `Test Array + Scalar`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 0.0 }, 0, F64Array.simdSize)
        val scalar = 3.0
        val arr2 = arr1 + scalar
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { 3.0 })
    }

    @Test
    fun `Test Array - Array`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 0.0 }, 0, F64Array.simdSize)
        val arr2 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 1.0 }, 0, F64Array.simdSize)
        val arr3 = arr1 - arr2
        assertContentEquals(arr3.toDoubleArray(), DoubleArray(F64Array.simdSize) { -1.0 })
    }

    @Test
    fun `Test Array - Scalar`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 0.0 }, 0, F64Array.simdSize)
        val scalar = 3.0
        val arr2 = arr1 - scalar
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { -3.0 })
    }

    @Test
    fun `Test Array x Array`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 2.0 }, 0, F64Array.simdSize)
        val arr2 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 3.0 }, 0, F64Array.simdSize)
        val result = arr1 * arr2
        assertContentEquals(result.toDoubleArray(), DoubleArray(F64Array.simdSize) { 6.0 })
    }

    @Test
    fun `Test Array x Scalar`() {
        val arr = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 1.0 }, 0, F64Array.simdSize)
        val scalar = 3.0
        val result = arr * scalar
        assertContentEquals(result.toDoubleArray(), DoubleArray(F64Array.simdSize) { 3.0 })
    }

    @Test
    fun `Test Array div Array`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 6.0 }, 0, F64Array.simdSize)
        val arr2 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 3.0 }, 0, F64Array.simdSize)
        val result = arr1 / arr2
        assertContentEquals(result.toDoubleArray(), DoubleArray(F64Array.simdSize) { 2.0 })
    }

    @Test
    fun `Test Array div Scalar`() {
        val arr = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 6.0 }, 0, F64Array.simdSize)
        val scalar = 3.0
        val result = arr / scalar
        assertContentEquals(result.toDoubleArray(), DoubleArray(F64Array.simdSize) { 2.0 })
    }

    @Test
    fun `Test Array negate`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 1.0 }, 0, F64Array.simdSize)
        val result = -arr1
        assertContentEquals(result.toDoubleArray(), DoubleArray(F64Array.simdSize) { -1.0 })
    }

    @Test
    fun `Test Array abs`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 1.0 else -1.0 }, 0, F64Array.simdSize)
        val result = arr1.abs()
        assertContentEquals(result.toDoubleArray(), DoubleArray(F64Array.simdSize) { 1.0 })
    }
}
