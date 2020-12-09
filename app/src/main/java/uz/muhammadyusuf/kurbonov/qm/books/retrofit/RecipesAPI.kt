package uz.muhammadyusuf.kurbonov.qm.books.retrofit

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import uz.muhammadyusuf.kurbonov.qm.books.database.recipes.RecipeModel

interface RecipesAPI {
    @Headers(
        "Authorization: Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    )
    @GET("api/recipe/search/")
    suspend fun getPage(@Query("page") page: Int, @Query("query") query: String = ""): RecipeResponse

    @Headers(
        "Authorization: Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    )
    @GET("api/recipe/search/")
    suspend fun getRecipe(@Query("id") id: Int): RecipeModel
}