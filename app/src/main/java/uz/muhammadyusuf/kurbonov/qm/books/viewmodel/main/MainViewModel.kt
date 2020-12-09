package uz.muhammadyusuf.kurbonov.qm.books.viewmodel.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import uz.muhammadyusuf.kurbonov.qm.books.R
import uz.muhammadyusuf.kurbonov.qm.books.database.LocalDatabase
import uz.muhammadyusuf.kurbonov.qm.books.database.recipes.RecipeModel
import java.util.*

class MainViewModel: ViewModel() {

    companion object {
        const val HIDE_KEYBOARD = 1
    }

    lateinit var repository: MainRepository
    lateinit var navController: NavController

    fun initDatabase(context: Context, debug: Boolean = false) {
        repository = Room.databaseBuilder(context, LocalDatabase::class.java, "main.db")
            .build()

        if (debug) {
            repository = Room.inMemoryDatabaseBuilder(context, LocalDatabase::class.java)
                .build()
        }
    }

    val activityBroadcast: MutableLiveData<Int> = MutableLiveData()


    fun goHomFragment() {
        navController.popBackStack(R.id.mainViewFragment, false)
        activityBroadcast.postValue(HIDE_KEYBOARD)
    }


}