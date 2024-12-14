import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.impl.F64LargeDenseFlatArrayImpl
import com.martmists.ndarray.simd.pow
import kotlin.math.*
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class VectorTest {
    @Test
    fun `Test Array dot Array`() {
        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it.toDouble() }, 0, F64Array.simdSize)
        val arr2 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it - 1.0 }, 0, F64Array.simdSize)
        val res = arr1 dot arr2
        assertEquals(res, (0 until F64Array.simdSize).sumOf { it * (it - 1.0) })
    }
}
