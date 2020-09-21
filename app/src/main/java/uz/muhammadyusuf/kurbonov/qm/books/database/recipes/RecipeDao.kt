package uz.muhammadyusuf.kurbonov.qm.books.database

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface RecipeDao {
    @Insert
    suspend fun insert(recipeModel: RecipeModel)

    @Update
    suspend fun update(recipeModel: RecipeModel)

    @Query("SELECT * FROM recipeModel LIMIT :size OFFSET :start")
    suspend fun  getPage(start: Int, size: Int): List<RecipeModel>

    @Query("SELECT * FROM RECIPEMODEL")
    suspend fun getAllDirect(): List<RecipeModel>

    @Query("SELECT * FROM recipeModel WHERE id= :id")
    suspend fun getRecipe(id: Int): RecipeModel

    @Delete
    suspend fun deleteRecipe(recipeModel: RecipeModel)
}