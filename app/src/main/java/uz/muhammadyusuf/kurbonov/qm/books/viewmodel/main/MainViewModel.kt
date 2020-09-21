package uz.muhammadyusuf.kurbonov.qm.books.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.muhammadyusuf.kurbonov.qm.books.database.LocalDatabase
import uz.muhammadyusuf.kurbonov.qm.books.database.RecipeModel
import java.util.*

class MainViewModel: ViewModel() {

    lateinit var repository: Repository

    fun initDatabase(context: Context){
        repository = Room.databaseBuilder(context, LocalDatabase::class.java, "main.db")
            .build()
    }

    val allPagedData = liveData {

        val source = repository.getPagedList()

        val pager = Pager(
            PagingConfig(10),
            pagingSourceFactory = {
                source
            }
        )

        pager.flow.collect {
            emit(it)
        }
    }

    fun generateFakeData(){
        var recipe = RecipeModel(
            0,
            "First",
            "Lorem",
            "Androider",
            "http://google.com/img?example",
            listOf("Honey", "Tomato", "Potato")
        )
        viewModelScope.launch {

            if (repository.getAllData().isNotEmpty()) return@launch

            for (i in 0 until 25){

                repository.insertNew(recipe)

                recipe = RecipeModel(
                    i+1,
                    UUID.randomUUID().toString(),
                    "",
                    "Androider",
                    "http://google.com/img?example",
                    listOf("Honey", "Tomato", "Potato")
                )
            }
        }
    }


}