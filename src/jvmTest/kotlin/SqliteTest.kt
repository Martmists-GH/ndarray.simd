import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.compat.ndarray
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class SqliteTest {
    object MyTable : Table() {
        val arr = ndarray("test")
    }

    @Test
    fun testSerialization() {
        val arr1 = F64Array.random(10, 10)

        val db = Database.connect("jdbc:sqlite:./data.sqlite3", "org.sqlite.JDBC")
        File("./data.sqlite3").deleteOnExit()

        transaction(db) {
            SchemaUtils.createMissingTablesAndColumns(MyTable)

            MyTable.insert {
                it[MyTable.arr] = arr1
            }
        }

        val arr2 = transaction(db) {
            val row = MyTable.selectAll().first()
            row[MyTable.arr]
        }

        assertEquals(arr1, arr2)

    }
}
