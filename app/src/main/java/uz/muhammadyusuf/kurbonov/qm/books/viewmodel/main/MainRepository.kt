package uz.muhammadyusuf.kurbonov.qm.books.viewmodel.main

import androidx.paging.PagingSource
import uz.muhammadyusuf.kurbonov.qm.books.database.recipes.RecipeModel
import uz.muhammadyusuf.kurbonov.qm.books.viewmodel.base.BaseRepository

interface MainRepository : BaseRepository {
    suspend fun getAllData(): List<RecipeModel>

    suspend fun getPagedList(): PagingSource<Int, RecipeModel>

    suspend fun insertNew(recipeModel: RecipeModel)

    suspend fun update(recipeModel: RecipeModel)

    suspend fun getRecipe(id: Int): RecipeModel
}