package com.martmists.ndarray.simd.compat

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.F64FlatArray
import com.martmists.ndarray.simd.impl.create
import com.martmists.ndarray.simd.impl.product
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.SQLServerDialect
import org.jetbrains.exposed.sql.vendors.currentDialect
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.sql.Blob
import java.sql.ResultSet

/**
 * [ColumnType] for [F64FlatArray] using simple serialization.
 */
internal class F64ArrayColumnType : ColumnType<F64Array>() {
    override fun sqlType(): String = currentDialect.dataTypeProvider.blobType()

    override fun valueFromDB(value: Any): F64Array = when (value) {
        is F64Array -> value
        is InputStream -> parse(value)
        is ByteArray -> parse(value)
        is Blob -> parse(value.binaryStream)
        else -> error("Unexpected value of type F64Array: $value of ${value::class.qualifiedName}")
    }

    override fun nonNullValueToString(value: F64Array) = currentDialect.dataTypeProvider.hexToDb(store(value).toHexString())
    private fun ByteArray.toHexString(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

    override fun readObject(rs: ResultSet, index: Int) = when {
        currentDialect is SQLServerDialect -> rs.getBytes(index)?.let(F64ArrayColumnType::parse)
        else -> rs.getBinaryStream(index)?.let(F64ArrayColumnType::parse)
    }

    companion object {
        @JvmStatic
        fun store(data: F64Array): ByteArray {
            val byteSize = 4 + 4 * data.shape.size + 8 * data.shape.product()
            val arr = ByteArray(byteSize)
            val buf = ByteBuffer.wrap(arr).order(ByteOrder.LITTLE_ENDIAN)

            buf.putInt(data.shape.size)
            for (dim in data.shape) {
                buf.putInt(dim)
            }

            val linear = data.reshape(data.shape.product())
            for (i in 0 until linear.length) {
                buf.putDouble(linear[i])
            }

            return arr
        }

        @JvmStatic
        fun parse(data: ByteArray): F64Array {
            val buf = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN)
            val shape = IntArray(buf.getInt()) {
                buf.getInt()
            }
            return F64FlatArray.create(DoubleArray(shape.product()) { buf.getDouble() }).reshape(*shape)
        }

        @JvmStatic
        fun parse(data: InputStream) = parse(data.readAllBytes())
    }
}

/**
 * A column type for storing vectors in Exposed.
 *
 * @param name The name of the column.
 * @param size The size of the vector.
 * @since 1.4.3
 */
@Deprecated("Currently not working as expected!")
fun Table.ndarray(name: String): Column<F64Array> = registerColumn(name, F64ArrayColumnType())
