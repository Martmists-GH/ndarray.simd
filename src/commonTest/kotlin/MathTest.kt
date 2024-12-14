import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.impl.F64LargeDenseFlatArrayImpl
import com.martmists.ndarray.simd.pow
import kotlin.math.*
import kotlin.test.Test
import kotlin.test.assertContentEquals

class MathTest {
    // FIXME: disabled until we can test within certain precision
//    @Test
//    fun `Test Array sqrt`() {
//        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it.toDouble() }, 0, F64Array.simdSize)
//        val arr2 = arr1.sqrt()
//        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { sqrt(it.toDouble()) })
//    }
//
//    @Test
//    fun `Test Array pow`() {
//        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it.toDouble() }, 0, F64Array.simdSize)
//        val arr2 = arr1.pow(2.0)
//        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { it.toDouble().pow(2.0) })
//    }
//
//    @Test
//    fun `Test Array ipow`() {
//        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it.toDouble() }, 0, F64Array.simdSize)
//        val arr2 = (2.0).pow(arr1)
//        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { (2.0).pow(it) })
//    }
//
//    @Test
//    fun `Test Array log`() {
//        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it.toDouble() }, 0, F64Array.simdSize)
//        val arr2 = arr1.ln()
//        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { ln(it.toDouble()) })
//    }
//
//    @Test
//    fun `Test Array logbase`() {
//        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it.toDouble() }, 0, F64Array.simdSize)
//        val arr2 = arr1.logBase(5.0)
//        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { log(it.toDouble(), 5.0) })
//    }
//
//    @Test
//    fun `Test Array exp`() {
//        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it.toDouble() }, 0, F64Array.simdSize)
//        val arr2 = arr1.exp()
//        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { exp(it.toDouble()) })
//    }
//
//    @Test
//    fun `Test Array expm1`() {
//        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it.toDouble() }, 0, F64Array.simdSize)
//        val arr2 = arr1.expm1()
//        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { expm1(it.toDouble()) })
//    }
//
//    @Test
//    fun `Test Array log1p`() {
//        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it.toDouble() }, 0, F64Array.simdSize)
//        val arr2 = arr1.log1p()
//        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { ln(1.0 + it) })
//    }
//
//    @Test
//    fun `Test Array log2`() {
//        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it.toDouble() }, 0, F64Array.simdSize)
//        val arr2 = arr1.log2()
//        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { log2(it.toDouble()) })
//    }
//
//    @Test
//    fun `Test Array log10`() {
//        val arr1 = F64LargeDenseFlatArrayImpl(DoubleArray(F64Array.simdSize) { it.toDouble() }, 0, F64Array.simdSize)
//        val arr2 = arr1.log10()
//        assertContentEquals(arr2.toDoubleArray(), DoubleArray(F64Array.simdSize) { log10(it.toDouble()) })
//    }
}
