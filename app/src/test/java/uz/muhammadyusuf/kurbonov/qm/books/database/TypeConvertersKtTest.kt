package uz.muhammadyusuf.kurbonov.qm.books.database

import org.junit.Assert.assertEquals
import org.junit.Test

class TypeConvertersKtTest {
    val typeConverters = TypeConverters()
    @Test
    fun listToString() {
        val l = listOf("Tomato", "Potato", "Onion", "Milk 250")
        val s = typeConverters.listToString(l)

        assertEquals(
            "Tomato;Potato;Onion;Milk 250",
            s
        )
    }

    @Test
    fun stringToList() {
        val s = "Tomato;Potato;Onion;Milk 250"
        val l = typeConverters.stringToList(s)

        assertEquals(
            listOf("Tomato", "Potato", "Onion", "Milk 250"),
            l
        )
    }
}