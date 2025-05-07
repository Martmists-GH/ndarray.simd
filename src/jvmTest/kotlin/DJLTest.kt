import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.compat.fromImage
import com.martmists.ndarray.simd.compat.image
import com.martmists.ndarray.simd.compat.toDJL
import com.martmists.ndarray.simd.compat.toF64Array
import com.martmists.ndarray.simd.compat.toImage
import com.martmists.ndarray.simd.compat.toOpenCVMat
import org.opencv.core.CvType
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class DJLTest {
    private val image = F64Array.fromImage(File(this::class.java.getResource("sample.png")!!.toURI()))

    @Test
    fun `Test DJL conversion`() {
        val nd = image.toDJL()
        val img = nd.toF64Array()
        img.neqInPlace(image)
        assertEquals(0.0, img.sum())
    }
}
