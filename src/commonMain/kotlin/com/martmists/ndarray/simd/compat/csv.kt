@file:JvmName("CsvCommonKt")

package com.martmists.ndarray.simd.compat

import com.martmists.ndarray.simd.F64Array
import kotlin.jvm.JvmName

/**
 * Reads a CSV file into an [F64Array].
 *
 * @param csv A sequence of lines
 * @param separator The separator between columns.
 * @return The [F64Array] read from the CSV file.
 * @since 1.4.0
 */
fun F64Array.Companion.fromCSV(csv: Sequence<String>, separator: String = ","): F64Array {
    var length = 0
    val rows = mutableListOf<DoubleArray>()
    for ((i, line) in csv.withIndex()) {
        val parts = line.split(separator)
        if (parts.size != length) {
            if (length != 0) {
                throw IllegalArgumentException("Inconsistent number of columns in CSV file at line $i")
            }
            length = parts.size
        }

        rows.add(parts.map { it.toDouble() }.toDoubleArray())
    }

    return F64Array.ofRows(rows)
}

/**
 * Writes an [F64Array] to a CSV string.
 *
 * @param separator The separator between columns.
 * @return a sequence containing all lines in the CSV file. Newlines are omitted.
 * @since 1.4.0
 */
fun F64Array.toCSV(separator: String = ",") : Sequence<String> {
    require(shape.size == 2) { "Only 2D arrays can be written to CSV" }

    return sequence {
        for (i in 0 until shape[0]) {
            val line = StringBuilder()
            for (j in 0 until shape[1]) {
                line.append(get(i, j).toString())
                if (j < shape[1] - 1) {
                    line.append(separator)
                }
            }
            yield(line.toString())
        }
    }
}
