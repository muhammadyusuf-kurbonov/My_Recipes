package uz.muhammadyusuf.kurbonov.qm.books.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow
import uz.muhammadyusuf.kurbonov.qm.books.database.recipes.RecipeDao
import uz.muhammadyusuf.kurbonov.qm.books.database.recipes.RecipeModel
import uz.muhammadyusuf.kurbonov.qm.books.viewmodel.main.MainRepository
import uz.muhammadyusuf.kurbonov.qm.books.database.TypeConverters as TypeConvertersImpl

@Database(entities = [RecipeModel::class], version = 1)
@TypeConverters(TypeConvertersImpl::class)
abstract class LocalDatabase : MainRepository, RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    override fun getAllData(): Flow<RecipeModel> =
        recipeDao().getAllData()

    override suspend fun getAllDataDirect(): List<RecipeModel> =
        recipeDao().getAllDirect()

    override suspend fun getPagedList(): PagingSource<Int, RecipeModel> =
        RecipePagingSource(recipeDao())

    override suspend fun insertNew(recipeModel: RecipeModel) =
        recipeDao().insert(recipeModel)

    override suspend fun update(recipeModel: RecipeModel) =
        recipeDao().update(recipeModel)

    override suspend fun getRecipe(id: Int): RecipeModel =
        recipeDao().getRecipe(id)

    override suspend fun delete(id: Int) =
        recipeDao().deleteById(getRecipe(id))

    override fun listenAllData(): LiveData<List<RecipeModel>> =
        recipeDao().listenAllData()

    override fun closeConnection() {
        close()
    }

    //=======================PRIVATE CLASSES=================================================


    private class RecipePagingSource(val dao: RecipeDao) : PagingSource<Int, RecipeModel>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecipeModel> {

            val position = params.key ?: 1
            val data = dao.getPage(position * params.loadSize, params.loadSize)
            val page = LoadResult.Page(
                data,
                if (position == 1) null else position - 1,
                if (data.isEmpty()) null else position + 1
            )
            return page
        }

    }
}