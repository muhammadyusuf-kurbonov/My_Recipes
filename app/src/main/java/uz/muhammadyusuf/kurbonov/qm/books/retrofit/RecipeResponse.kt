package uz.muhammadyusuf.kurbonov.qm.books.retrofit

import uz.muhammadyusuf.kurbonov.qm.books.database.recipes.RecipeModel

data class RecipeResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<RecipeModel>
)