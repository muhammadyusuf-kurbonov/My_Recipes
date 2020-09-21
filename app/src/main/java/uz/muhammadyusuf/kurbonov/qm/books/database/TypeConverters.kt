package uz.muhammadyusuf.kurbonov.qm.books.database

import androidx.room.TypeConverter

class TypeConverters {

    @TypeConverter
    fun listToString(list: List<String>): String = list.joinToString(";")

    @TypeConverter
    fun stringToList(str: String): List<String> = str.split(";")
}