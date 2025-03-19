import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.compat.fromImage
import com.martmists.ndarray.simd.compat.toF64Array
import com.martmists.ndarray.simd.compat.toImage
import com.martmists.ndarray.simd.compat.toOpenCVMat
import org.opencv.core.CvType
import java.io.File
import kotlin.test.Test

class OpenCVTest {
    // FIXME: Find some pictures to test on
    // Validated locally for now

//    private val image = F64Array.fromImage(File(this::class.java.getResource("test.png")!!.toURI()))
//
//    @Test
//    fun `Test OpenCV conversion`() {
//        val mat = image.toOpenCVMat(CvType.CV_8SC1)  // reduce depth to s8, remove colors (since 1 channel is monochrome)
//        val img = mat.toF64Array()
//        img.toImage(File("build/test.jpg"))
//    }
}
