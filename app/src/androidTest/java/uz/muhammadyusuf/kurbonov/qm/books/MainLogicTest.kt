package uz.muhammadyusuf.kurbonov.qm.books

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import uz.muhammadyusuf.kurbonov.qm.books.database.LocalDatabase
import uz.muhammadyusuf.kurbonov.qm.books.database.recipes.RecipeModel
import uz.muhammadyusuf.kurbonov.qm.books.viewmodel.main.MainViewModel

@RunWith(AndroidJUnit4::class)
class MainLogicTest {

    lateinit var mainViewModel: MainViewModel

    @Before
    fun initialize() {
        mainViewModel = MainViewModel()
        mainViewModel.initDatabase(InstrumentationRegistry.getInstrumentation().context, true)
    }

    @Test
    fun testInsert() {
        runBlocking {
            mainViewModel.generateFakeData(150)

            assertEquals(150, mainViewModel.repository.getAllData().size)
        }
    }

    @Test
    fun testReading() {
        runBlocking {
            (mainViewModel.repository as LocalDatabase).clearAllTables()
            mainViewModel.generateFakeData(150)
            assertEquals(150, mainViewModel.repository.getAllData().size)
        }
    }

    @Test
    fun testUpdate() {
        runBlocking {
            val newRecipe = RecipeModel(
                1,
                "Тортики",
                "Lorem ipsum",
                "Androider",
                "http://link.google.com",
                listOf("Potato", "Good luck")
            )
            mainViewModel.repository.update(newRecipe)

            assertEquals(
                newRecipe,
                mainViewModel.repository.getRecipe(1)
            )
        }
    }

    @Test
    fun testPaging(){
        runBlocking {
            mainViewModel.allPagedData.collect {
                it.filterSync {
                    println()
                    true
                }
            }
        }
    }


    @After
    fun close(){
        mainViewModel
    }
}