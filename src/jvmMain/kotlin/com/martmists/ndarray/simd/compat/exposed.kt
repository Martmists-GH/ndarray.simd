package com.martmists.ndarray.simd.compat

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64FlatArray
import com.martmists.ndarray.simd.toF64Array
import com.martmists.ndarray.simd.toFloatArray
import com.pgvector.PGvector
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.QueryBuilder
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.append
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection
import java.sql.ResultSet

/**
 * [ColumnType] for [F64FlatArray] using [PGvector].
 */
internal class PgVectorColumnType(private val size: Int) : ColumnType<F64FlatArray>() {
    override fun sqlType(): String = "vector($size)"

    override fun readObject(rs: ResultSet, index: Int) = rs.getObject(index) as PGvector?

    override fun validateValueBeforeUpdate(value: F64FlatArray?) {
        when (value) {
            is F64FlatArray -> require(value.shape.contentEquals(intArrayOf(size))) { "F64FlatArray dimension must be $size" }
            else -> error("Unexpected value: $value")
        }
    }

    override fun valueFromDB(value: Any) = when (value) {
        is PGvector -> value.toArray().toF64Array()
        else -> error("Unexpected value: $value")
    }

    override fun notNullValueToDB(value: F64FlatArray) = PGvector(value.flatten().toFloatArray())
}

/**
 *  [PGVectorManager] is an implementation of [TransactionManager] that adds the [PGvector] type to the current transaction. This is required for the extension to work.
 *
 *  Due to its simplicity, this class is licensed under [CC0](https://en.wikipedia.org/wiki/Creative_Commons_license#Zero,_public_domain).
 */
internal class PGVectorManager(private val impl: TransactionManager) : TransactionManager by impl {
    override fun newTransaction(isolation: Int, readOnly: Boolean, outerTransaction: Transaction?): Transaction {
        val transaction = impl.newTransaction(isolation, readOnly, outerTransaction)
        val conn = transaction.connection.connection as Connection
        PGvector.addVectorType(conn)
        return transaction
    }
}

/**
 * Represents a Vector comparison between `left` and `right`, where `left` is a [Column] and `right` is a [Column].
 */
internal class VectorColumnOp(
    private val left: Column<F64FlatArray>,
    private val right: Column<F64FlatArray>,
    private val op: String
) : Op<Float>() {
    override fun toQueryBuilder(queryBuilder: QueryBuilder) = queryBuilder {
        append(left, " $op ", right)
    }
}

/**
 * Represents a Vector comparison between `left` and `right`, where `left` is a [Column] and `right` is an [F64FlatArray].
 */
internal class VectorValueOp(
    private val left: Column<F64FlatArray>,
    private val right: F64FlatArray,
    private val op: String
) : Op<Float>() {
    override fun toQueryBuilder(queryBuilder: QueryBuilder) = queryBuilder {
        append(left, " $op ", "'${left.columnType.notNullValueToDB(right)}'")
    }
}

/**
 * A column type for storing vectors in PostgreSQL.
 *
 * @param name The name of the column.
 * @param size The size of the vector.
 * @since 1.4.0
 */
fun Table.vector(name: String, size: Int = 384): Column<F64FlatArray> = registerColumn(name, PgVectorColumnType(size))

/**
 * Calculates the cosine distance between this and some [other] column.
 * @since 1.4.0
 */
infix fun Column<F64FlatArray>.cosineDistance(other: Column<F64FlatArray>): Op<Float> {
    return VectorColumnOp(this, other, "<=>")
}

/**
 * Calculates the cosine distance between this and some [other] vector.
 * @since 1.4.0
 */
infix fun Column<F64FlatArray>.cosineDistance(other: F64FlatArray): Op<Float> {
    return VectorValueOp(this, other, "<=>")
}

/**
 * Calculates the L2 distance between this and some [other] column.
 * @since 1.4.0
 */
infix fun Column<F64FlatArray>.l2Distance(other: Column<F64FlatArray>): Op<Float> {
    return VectorColumnOp(this, other, "<->")
}

/**
 * Calculates the L2 distance between this and some [other] vector.
 * @since 1.4.0
 */
infix fun Column<F64FlatArray>.l2Distance(other: F64FlatArray): Op<Float> {
    return VectorValueOp(this, other, "<->")
}

/**
 * Calculates the inner product between this and some [other] column.
 * @since 1.4.0
 */
infix fun Column<F64FlatArray>.innerProduct(other: Column<F64FlatArray>): Op<Float> {
    return VectorColumnOp(this, other, "<#>")
}

/**
 * Calculates the inner product between this and some [other] vector.
 * @since 1.4.0
 */
infix fun Column<F64FlatArray>.innerProduct(other: F64FlatArray): Op<Float> {
    return VectorValueOp(this, other, "<#>")
}

/**
 * Calculates the L1 distance between this and some [other] column.
 * @since 1.4.0
 */
infix fun Column<F64FlatArray>.l1Distance(other: Column<F64FlatArray>): Op<Float> {
    return VectorColumnOp(this, other, "<+>")
}

/**
 * Calculates the L1 distance between this and some [other] vector.
 * @since 1.4.0
 */
infix fun Column<F64FlatArray>.l1Distance(other: F64FlatArray): Op<Float> {
    return VectorValueOp(this, other, "<+>")
}

/**
 * Initializes the [Database] to use the pgvector extension and registers managers for the correct type.
 * @since 1.4.0
 */
fun Database.usePGVector() {
    TransactionManager.registerManager(this, PGVectorManager(TransactionManager.manager))
    transaction(this) {
        exec("CREATE EXTENSION IF NOT EXISTS vector;")
    }
}
