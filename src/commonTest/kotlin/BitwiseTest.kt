import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.impl.F64LargeDenseFlatArrayImpl
import com.martmists.ndarray.simd.impl.F64SmallDenseFlatArrayImpl
import kotlin.test.Test
import kotlin.test.assertContentEquals

class BitwiseTest {
    @Test
    fun `Test Array and Array`() {
        val arr1 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 3.0 }, 0, F64Array.simdSize)
        val arr2 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 5.0 }, 0, F64Array.simdSize)
        val arr3 = arr1 and arr2
        assertContentEquals(arr3.toDoubleArray(), DoubleArray(F64Array.simdSize) { 1.0 })
    }

    @Test
    fun `Test Array and Scalar`() {
        val arr1 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 3.0 }, 0, F64Array.simdSize)
        val scalar = 5
        val arr2 = arr1 and scalar
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { 1.0 })
    }

    @Test
    fun `Test Array or Array`() {
        val arr1 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 3.0 }, 0, F64Array.simdSize)
        val arr2 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 5.0 }, 0, F64Array.simdSize)
        val arr3 = arr1 or arr2
        assertContentEquals(arr3.toDoubleArray(), DoubleArray(F64Array.simdSize) { 7.0 })
    }

    @Test
    fun `Test Array or Scalar`() {
        val arr1 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 3.0 }, 0, F64Array.simdSize)
        val scalar = 5
        val arr2 = arr1 or scalar
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { 7.0 })
    }

    @Test
    fun `Test Array xor Array`() {
        val arr1 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 3.0 }, 0, F64Array.simdSize)
        val arr2 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 5.0 }, 0, F64Array.simdSize)
        val arr3 = arr1 xor arr2
        assertContentEquals(arr3.toDoubleArray(), DoubleArray(F64Array.simdSize) { 6.0 })
    }

    @Test
    fun `Test Array xor Scalar`() {
        val arr1 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 3.0 }, 0, F64Array.simdSize)
        val scalar = 5
        val arr2 = arr1 xor scalar
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { 6.0 })
    }

    @Test
    fun `Test Array not`() {
        val arr1 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 8.0 }, 0, F64Array.simdSize)
        val arr2 = arr1.not()
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { -9.0 })
    }

    @Test
    fun `Test Array shl Array`() {
        val arr1 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 5.0 }, 0, F64Array.simdSize)
        val arr2 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 3.0 }, 0, F64Array.simdSize)
        val arr3 = arr1 shl arr2
        assertContentEquals(arr3.toDoubleArray(), DoubleArray(F64Array.simdSize) { 40.0 })
    }

    @Test
    fun `Test Array shl Scalar`() {
        val arr1 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 5.0 }, 0, F64Array.simdSize)
        val arr2 = arr1 shl 3
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { 40.0 })
    }

    @Test
    fun `Test Array shr Array`() {
        val arr1 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 40.0 }, 0, F64Array.simdSize)
        val arr2 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 3.0 }, 0, F64Array.simdSize)
        val arr3 = arr1 shr arr2
        assertContentEquals(arr3.toDoubleArray(), DoubleArray(F64Array.simdSize) { 5.0 })
    }

    @Test
    fun `Test Array shr Scalar`() {
        val arr1 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 40.0 }, 0, F64Array.simdSize)
        val arr2 = arr1 shr 3
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { 5.0 })
    }
}
