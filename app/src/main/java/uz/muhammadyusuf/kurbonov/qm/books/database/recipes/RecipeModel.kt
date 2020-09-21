package uz.muhammadyusuf.kurbonov.qm.books.database.recipes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val processDescription: String,
    val author: String,
    @ColumnInfo(defaultValue = "")
    val imageLink: String,
    val ingredients: List<String>
)