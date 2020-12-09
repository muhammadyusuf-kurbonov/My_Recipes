package uz.muhammadyusuf.kurbonov.qm.books.database.recipes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.muhammadyusuf.kurbonov.utils.formatAsDate

@Entity
data class RecipeModel(
    @PrimaryKey(autoGenerate = true)
    var pk: Int? = null,
    var cooking_instructions: String? = null,
    var date_added: String = System.currentTimeMillis().formatAsDate("MMMM dd YYYY"),
    var date_updated: String = System.currentTimeMillis().formatAsDate("MMMM dd YYYY"),
    var description: String? = null,
    var featured_image: String? = null,
    var ingredients: List<String> = emptyList(),
    var publisher: String? = null,
    var rating: Int? = null,
    var source_url: String? = null,
    var title: String? = null
)