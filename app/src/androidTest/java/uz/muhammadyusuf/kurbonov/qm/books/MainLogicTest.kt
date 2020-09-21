package uz.muhammadyusuf.kurbonov.qm.books

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import uz.muhammadyusuf.kurbonov.qm.books.database.LocalDatabase
import uz.muhammadyusuf.kurbonov.qm.books.database.RecipeModel
import java.util.*

@RunWith(AndroidJUnit4::class)
class RoomTest {

    lateinit var db: LocalDatabase

    @Before
    fun initialize(){
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            LocalDatabase::class.java
        ).build()
    }


    fun testInsert(){


        var recipe = RecipeModel(
            0,
            "First",
            "Lorem",
            "Androider",
            "http://google.com/img?example",
            listOf("Honey", "Tomato", "Potato")
        )
        runBlocking {

            if (db.recipeDao().getAllDirect().isNotEmpty()) return@runBlocking

            for (i in 0 until 100){

                db.recipeDao().insert(recipe)

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

    @Test
    fun testRead(){
        testInsert()
        runBlocking {
            val result = db.getAllData()
            assertEquals(100, result.size)
        }
    }

    @Test
    fun testPaging(){
        runBlocking {

            testInsert()

            val source = db.getPagedList()

            val pager = Pager(
                PagingConfig(10),
                pagingSourceFactory = {
                    source
                }
            )
            println("Starting...")

            pager.flow.collect {
                println(it)
            }

            println("Ending ...")
            assert(true)
        }
    }

    @After
    fun close(){
        db.close()
    }
}