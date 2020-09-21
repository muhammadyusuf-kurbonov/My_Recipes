package uz.muhammadyusuf.kurbonov.qm.books.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import uz.muhammadyusuf.kurbonov.qm.books.database.recipes.RecipeModel
import uz.muhammadyusuf.kurbonov.qm.books.viewmodel.base.BaseRepository

interface MainRepository : BaseRepository {
    fun getAllData(): Flow<RecipeModel>

    suspend fun getAllDataDirect(): List<RecipeModel>

    suspend fun getPagedList(): PagingSource<Int, RecipeModel>

    suspend fun insertNew(recipeModel: RecipeModel)

    suspend fun update(recipeModel: RecipeModel)

    suspend fun getRecipe(id: Int): RecipeModel

    fun listenAllData(): LiveData<List<RecipeModel>>
}