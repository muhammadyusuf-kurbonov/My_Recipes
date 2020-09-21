package uz.muhammadyusuf.kurbonov.qm.books.viewmodel

import androidx.paging.DataSource
import androidx.paging.PagingSource
import uz.muhammadyusuf.kurbonov.qm.books.database.RecipeModel

interface Repository {
    suspend fun getAllData(): List<RecipeModel>

    suspend fun getPagedList(): PagingSource<Int, RecipeModel>

    suspend fun insertNew(recipeModel: RecipeModel)

    suspend fun update(recipeModel: RecipeModel)

    suspend fun getRecipe(id: Int): RecipeModel
}