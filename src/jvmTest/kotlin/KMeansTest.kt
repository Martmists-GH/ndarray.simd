import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64FlatArray
import com.martmists.ndarray.simd.compat.fromImage
import com.martmists.ndarray.simd.compat.image
import com.martmists.ndarray.simd.math.clusterKMeans
import java.io.File
import kotlin.math.roundToInt
import kotlin.test.Test
import org.jetbrains.kotlinx.kandy.dsl.categorical
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.layers.bars
import org.jetbrains.kotlinx.kandy.letsplot.layers.points
import org.jetbrains.kotlinx.kandy.util.color.Color

fun F64FlatArray.rgbToHsv(): F64FlatArray {
    // H in [0 .. 360]
    // S in [0 .. 1]
    // V in [0 .. 1]
    var (r, g, b) = this.toDoubleArray()

    r *= 255
    g *= 255
    b *= 255

    val max = maxOf(r, g, b)
    val min = minOf(r, g, b)
    val delta = max - min

    var h = when {
        delta == 0.0 -> 0.0
        max == r -> 60.0 * ((g - b) / delta % 6)
        max == g -> 60.0 * ((b - r) / delta + 2)
        else -> 60.0 * ((r - g) / delta + 4)
    }

    if (h < 0.0) h += 360.0

    val s = if (max == 0.0) 0.0 else delta / max
    val v = max / 255.0

    return F64Array.of(h, s, v).flatten()
}

fun F64FlatArray.hsvToRgb(): F64FlatArray {
    val (h, s, v) = this.toDoubleArray()

    fun component(n: Int): Double {
        val k = (n + h / 60) % 6
        return v - (v * s * maxOf(0.0, minOf(k, 4 - k, 1.0)))
    }

    val r = component(5)
    val g = component(3)
    val b = component(1)

    return F64Array.of(r, g, b).flatten()
}

fun F64Array.pixels(): List<F64FlatArray> {
    require(shape.size == 3) { "Cannot convert F64Array of shape ${this.shape} to pixels" }
    val xRange = 0 until shape[0]
    val yRange = 0 until shape[1]
    return (xRange.flatMap { x -> yRange.map { y -> this.V[x, y] } }).map { it.flatten() }
}

class KMeansTest {
    private val image = F64Array.fromImage(File(this::class.java.getResource("sample3.png")!!.toURI())).pixels().map { it.rgbToHsv() }

    private fun plot(colors: Map<F64FlatArray, Int>) = plot {
        bars {
            x((0 until colors.size))
            y(colors.values.toList())
            borderLine.color = Color.BLACK
            fillColor((0 until colors.size)) {
                scale = categorical(colors.keys.map {
                    val r = (it[0] * 255).roundToInt()
                    val g = (it[1] * 255).roundToInt()
                    val b = (it[2] * 255).roundToInt()
                    Color.rgb(r, g, b)
                })
            }
        }
    }

    @Test
    fun `K = 2, Image Data`() {
        val clusters = clusterKMeans(2, image)
        val meanColors = clusters.entries.associate { (hsv, cluster) -> hsv.hsvToRgb() to cluster.size }
        plot(meanColors).save("kmeans-2-image.png")
    }

    @Test
    fun `K = 5, Image Data`() {
        val clusters = clusterKMeans(5, image)
        val meanColors = clusters.entries.associate { (hsv, cluster) -> hsv.hsvToRgb() to cluster.size }
        plot(meanColors).save("kmeans-5-image.png")
    }

    @Test
    fun `K = 10, Image Data`() {
        val clusters = clusterKMeans(10, image)
        val meanColors = clusters.entries.associate { (hsv, cluster) -> hsv.hsvToRgb() to cluster.size }
        plot(meanColors).save("kmeans-10-image.png")
    }
}
