import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.impl.F64LargeDenseFlatArrayImpl
import com.martmists.ndarray.simd.impl.F64SmallDenseFlatArrayImpl
import com.martmists.ndarray.simd.pow
import kotlin.math.*
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class VectorTest {
    @Test
    fun `Test Array dot Array`() {
        val arr1 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it.toDouble() }, 0, F64Array.simdSize)
        val arr2 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it - 1.0 }, 0, F64Array.simdSize)
        val res = arr1 dot arr2
        assertEquals(res, (0 until F64Array.simdSize).sumOf { it * (it - 1.0) })
    }

    @Test
    fun `Test Array matmul Array`() {
        val arr1 = F64Array.random(F64Array.simdSize, F64Array.simdSize)
        val inv = arr1.inverse()
        val res = arr1 matmul inv
        // assertContentEquals(res.diagonal().toDoubleArray(), DoubleArray(arr1.shape.min()) { 1.0 })
    }
}
