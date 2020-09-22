package uz.muhammadyusuf.kurbonov.qm.books.database

import androidx.room.TypeConverter
import java.util.*

class TypeConverters {

    @TypeConverter
    fun listToString(list: List<String>): String = list.joinToString(",")

    @TypeConverter
    fun stringToList(str: String): List<String> = str.split(",").map { it.capitalize(Locale.ROOT) }
}