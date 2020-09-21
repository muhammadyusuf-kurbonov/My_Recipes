package uz.muhammadyusuf.kurbonov.qm.books.database.recipes

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert
    suspend fun insert(recipeModel: RecipeModel)

    @Update
    suspend fun update(recipeModel: RecipeModel)

    @Query("SELECT * FROM recipeModel LIMIT :size OFFSET :start")
    suspend fun getPage(start: Int, size: Int): List<RecipeModel>

    @Query("SELECT * FROM RECIPEMODEL")
    suspend fun getAllDirect(): List<RecipeModel>

    @Query("SELECT * FROM RECIPEMODEL")
    fun getAllData(): Flow<RecipeModel>


    @Query("SELECT * FROM RECIPEMODEL")
    fun listenAllData(): LiveData<List<RecipeModel>>

    @Query("SELECT * FROM recipeModel WHERE id= :id")
    suspend fun getRecipe(id: Int): RecipeModel

    @Delete
    suspend fun deleteRecipe(recipeModel: RecipeModel)
}