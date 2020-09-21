package uz.muhammadyusuf.kurbonov.qm.books.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeModel(
    @PrimaryKey
    val id: Int,
    val title: String,
    val processDescription: String,
    val author: String,
    @ColumnInfo(defaultValue = "")
    val imageLink: String,
    val ingredients: List<String>
)