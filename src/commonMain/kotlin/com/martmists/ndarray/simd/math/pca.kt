package com.martmists.ndarray.simd.math

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64FlatArray
import kotlin.math.sqrt

// TODO: Add support for points encoded as a single 2D F64Array

/**
 * Reduces dimensionality of `points` to `k` dimensions using PCA.
 *
 * @param k The number of dimensions to reduce to.
 * @param points The points to reduce.
 * @return The reduced points.
 * @since 1.4.1
 */
fun pca(k: Int, points: List<F64FlatArray>): List<F64FlatArray> {
    val mean = points.reduce { acc, array -> acc + array } / points.size.toDouble()
    val stdDev = (points.reduce { acc, array -> acc + (array - mean).let { it * it } } / (points.size - 1.0)).transform(::sqrt)
    val normalized = F64Array.ofRows(points.map { (it - mean) / stdDev } )

    val asMatrix = F64Array.ofRows(points)
    val covMatrix = asMatrix.transpose().matmul(asMatrix) / (points.size - 1.0)
    val (eigenValues, eigenVectors) = covMatrix.eigen()
    val values = eigenValues.toDoubleArray()
    val vectors = eigenVectors.along(0).toList()

    val sorted = values.mapIndexed { index, d -> index to d }.sortedByDescending(Pair<Int, Double>::second).map(Pair<Int, Double>::first)
    val sortedVectors = sorted.map(vectors::get)

    val topEigen = sortedVectors.take(k)
    val newMat = F64Array.ofRows(topEigen)

    return normalized.matmul(newMat.transpose()).along(0).map { it.flatten() }.toList()
}
