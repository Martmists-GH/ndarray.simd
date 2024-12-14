import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.impl.F64LargeDenseFlatArrayImpl
import com.martmists.ndarray.simd.pow
import kotlin.math.*
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class RoundingTest {
    @Test
    fun `Test Array floor`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 0.1 * it }, 0, F64Array.simdSize)
        val arr2 = arr1.floor()
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { floor(0.1 * it) })
    }

    @Test
    fun `Test Array ceil`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 0.1 * it }, 0, F64Array.simdSize)
        val arr2 = arr1.ceil()
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { ceil(0.1 * it) })
    }

    @Test
    fun `Test Array trunc`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 0.1 * it }, 0, F64Array.simdSize)
        val arr2 = arr1.trunc()
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { truncate(0.1 * it) })
    }

    @Test
    fun `Test Array round`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 0.2 * it }, 0, F64Array.simdSize)
        val arr2 = arr1.round()
        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { round(0.2 * it) })
    }
}
