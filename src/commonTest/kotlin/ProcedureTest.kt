import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.impl.F64LargeDenseFlatArrayImpl
import com.martmists.ndarray.simd.impl.F64SmallDenseFlatArrayImpl
import com.martmists.ndarray.simd.pow
import kotlin.math.*
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ProcedureTest {
    @Test
    fun `Test Array sum`() {
        val arr = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { 1.0 }, 0, F64Array.simdSize)
        val res = arr.sum()
        assertEquals(F64Array.simdSize.toDouble(), res)
    }

    @Test
    fun `Test Array min`() {
        val arr = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it * 2.0 }, 0, F64Array.simdSize)
        val res = arr.min()
        assertEquals(0.0, res)
    }

    @Test
    fun `Test Array max`() {
        val arr = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it * 5.0 }, 0, F64Array.simdSize)
        val res = arr.max()
        assertEquals((F64Array.simdSize * 5 - 5).toDouble(), res)
    }

    @Test
    fun `Test Array prod`() {
        val arr = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it + 1.0 }, 0, F64Array.simdSize)
        val res = arr.product()

        fun factorial(n: Double): Double {
            if (n == 1.0) return n
            return n * factorial(n - 1)
        }

        assertEquals(factorial(F64Array.simdSize.toDouble()), res)
    }

    @Test
    fun `Test Array mean`() {
        val arr = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it.toDouble() }, 0, F64Array.simdSize)
        val res = arr.mean()
        assertEquals((F64Array.simdSize.toDouble() - 1) / 2, res)
    }

    @Test
    fun `Test Array coerce`() {
        val arr1 = F64SmallDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it * 5.0 }, 0, F64Array.simdSize)
        val arr2 = arr1.coerce(2.5, 17.0)
        assertEquals(2.5, arr2.min())
        assertEquals(17.0, arr2.max())
    }
}
