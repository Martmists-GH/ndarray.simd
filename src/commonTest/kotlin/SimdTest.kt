import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.impl.F64LargeDenseFlatArrayImpl
import kotlin.test.Test

class SimdTest {
    @Test
    fun `Test Array + Array`() {
        println(F64Array.simdSize)
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 0.0 }, 0, F64Array.simdSize)
        val arr2 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 1.0 }, 0, F64Array.simdSize)
        val arr3 = arr1 + arr2
        println(arr3)
    }
    @Test
    fun `Test Array + Scalar`() {
        println(F64Array.simdSize)
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 0.0 }, 0, F64Array.simdSize)
        val scalar = 3.0
        val arr2 = arr1 + scalar
        println(arr2)
    }
}
