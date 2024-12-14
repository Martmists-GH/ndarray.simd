import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.impl.F64LargeDenseFlatArrayImpl
import kotlin.test.Test
import kotlin.test.assertContentEquals

class CompareTest {
    @Test
    fun `Test Array = Array`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 1.0 else 2.0 }, 0, F64Array.simdSize)
        val arr2 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 2.0 }, 0, F64Array.simdSize)
        val arr3 = arr1 eq arr2
        assertContentEquals(arr3.toDoubleArray(), DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 0.0 else 1.0 })
    }

    @Test
    fun `Test Array = Scalar`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 1.0 else 2.0 }, 0, F64Array.simdSize)
        val scalar = 2.0
        val arr2 = arr1 eq scalar
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 0.0 else 1.0 })
    }

    @Test
    fun `Test Array != Array`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 1.0 else 2.0 }, 0, F64Array.simdSize)
        val arr2 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 2.0 }, 0, F64Array.simdSize)
        val arr3 = arr1 neq arr2
        assertContentEquals(arr3.toDoubleArray(), DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 1.0 else 0.0 })
    }

    @Test
    fun `Test Array != Scalar`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 1.0 else 2.0 }, 0, F64Array.simdSize)
        val scalar = 2.0
        val arr2 = arr1 neq scalar
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 1.0 else 0.0 })
    }

    @Test
    fun `Test Array lt Array`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 1.0 else 2.0 }, 0, F64Array.simdSize)
        val arr2 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 1.5 }, 0, F64Array.simdSize)
        val arr3 = arr1 lt arr2
        assertContentEquals(arr3.toDoubleArray(), DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 1.0 else 0.0 })
    }

    @Test
    fun `Test Array lt Scalar`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 1.0 else 2.0 }, 0, F64Array.simdSize)
        val scalar = 1.5
        val arr2 = arr1 lt scalar
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 1.0 else 0.0 })
    }

    @Test
    fun `Test Array gt Array`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 1.0 else 2.0 }, 0, F64Array.simdSize)
        val arr2 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 1.5 }, 0, F64Array.simdSize)
        val arr3 = arr1 gt arr2
        assertContentEquals(arr3.toDoubleArray(), DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 0.0 else 1.0 })
    }

    @Test
    fun `Test Array gt Scalar`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 1.0 else 2.0 }, 0, F64Array.simdSize)
        val scalar = 1.5
        val arr2 = arr1 gt scalar
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { if (it % 2 == 0) 0.0 else 1.0 })
    }

    @Test
    fun `Test Array isnan`() {
        val options = doubleArrayOf(0.0, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)
        val arr = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { options[it % options.size] }, 0, F64Array.simdSize)
        val arr2 = arr.isNan()
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { if (options[it % options.size].isNaN()) 1.0 else 0.0 })
    }

    @Test
    fun `Test Array isinf`() {
        val options = doubleArrayOf(0.0, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)
        val arr = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { options[it % options.size] }, 0, F64Array.simdSize)
        val arr2 = arr.isInf()
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { if (options[it % options.size].isInfinite()) 1.0 else 0.0 })
    }

    @Test
    fun `Test Array isfinite`() {
        val options = doubleArrayOf(0.0, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)
        val arr = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { options[it % options.size] }, 0, F64Array.simdSize)
        val arr2 = arr.isFinite()
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { if (options[it % options.size].isFinite()) 1.0 else 0.0 })
    }
}
