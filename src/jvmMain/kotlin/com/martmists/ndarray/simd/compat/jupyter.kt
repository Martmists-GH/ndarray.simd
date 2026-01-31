package com.martmists.ndarray.simd.compat

import com.martmists.ndarray.simd.F64Array
import org.jetbrains.kotlinx.jupyter.api.HTML
import org.jetbrains.kotlinx.jupyter.api.libraries.JupyterIntegration
import kotlin.math.min

class F64ArrayJupyterIntegration : JupyterIntegration() {
    override fun Builder.onLoaded() {
        import("com.martmists.ndarray.simd.*")
        import("com.martmists.ndarray.simd.compat.*")
        import("com.martmists.ndarray.simd.math.*")

        import("org.jetbrains.letsPlot.*")
        import("org.jetbrains.letsPlot.geom.geomLine")

        render<F64Array> {
            HTML(it.asHTML())
        }
    }

    private fun F64Array.asHTML(): String {
        val shapeStr = shape.joinToString(", ", "[", "]")
        val header = "<strong>F64Array</strong> shape: $shapeStr<br>"

        val table = when (nDim) {
            1 -> render1D()
            2 -> render2D()
            else -> "ND-Array (Rendering first 2D slice):<br>${view(0, 0).render2D()}"
        }

        return """
            <div style="font-family: monospace; border: 1px solid #ccc; padding: 8px; border-radius: 4px;">
                $header
                <div style="overflow-x: auto; margin-top: 8px;">
                    $table
                </div>
            </div>
        """.trimIndent()
    }

    private fun F64Array.render1D(): String {
        val sb = StringBuilder("<table style='border-collapse: collapse;'><tr>")
        val limit = 10
        val len = shape[0]

        for (i in 0 until min(len, limit)) {
            sb.append("<td style='border: 1px solid #ddd; padding: 4px;'>${this[i]}</td>")
        }
        if (len > limit) sb.append("<td style='padding: 4px;'>...</td>")

        sb.append("</tr></table>")
        return sb.toString()
    }

    private fun F64Array.render2D(): String {
        val sb = StringBuilder("<table style='border-collapse: collapse;'>")
        val rows = shape[0]
        val cols = shape[1]
        val rowLimit = 10
        val colLimit = 10

        for (r in 0 until min(rows, rowLimit)) {
            sb.append("<tr>")
            for (c in 0 until min(cols, colLimit)) {
                sb.append("<td style='border: 1px solid #ddd; padding: 4px; text-align: right;'>${this[r, c]}</td>")
            }
            if (cols > colLimit) sb.append("<td style='padding: 4px;'>...</td>")
            sb.append("</tr>")
        }
        if (rows > rowLimit) sb.append("<tr><td colspan='$colLimit' style='text-align: center;'>...</td></tr>")

        sb.append("</table>")
        return sb.toString()
    }
}
