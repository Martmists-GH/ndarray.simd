package com.martmists.ndarray.simd.compat

import com.martmists.ndarray.simd.F64Array
import java.io.File

/**
 * Reads a CSV file into an [F64Array].
 *
 * @param csv The CSV file to read.
 * @param separator The separator between columns.
 * @return The [F64Array] read from the CSV file.
 * @since 1.4.0
 */
fun F64Array.Companion.fromCSV(csv: File, separator: String = ","): F64Array {
    return fromCSV(sequence {
        csv.useLines { inLines ->
            yieldAll(inLines)
        }
    })
}

/**
 * Writes an [F64Array] to a CSV file.
 *
 * @param csv The CSV file to write to.
 * @param separator The separator between columns.
 * @since 1.4.0
 */
fun F64Array.toCSV(csv: File, separator: String = ",") {
    require(shape.size == 2) { "Only 2D arrays can be written to CSV" }

    csv.writer().use {
        for (line in toCSV(separator)) {
            it.append(line)
            it.append("\n")
        }
    }
}
