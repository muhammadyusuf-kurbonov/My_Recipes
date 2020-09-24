package uz.muhammadyusuf.kurbonov.qm.books.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.google.android.material.chip.Chip
import uz.muhammadyusuf.kurbonov.qm.books.database.recipes.RecipeModel
import uz.muhammadyusuf.kurbonov.qm.books.databinding.RecipeItemBinding

class MainListAdapter :
    ListAdapter<RecipeModel, MainPagingAdapter.RecipeViewHolder>(MainPagingAdapter.DiffResolver()) {
    override fun onBindViewHolder(holder: MainPagingAdapter.RecipeViewHolder, position: Int) {
        with(holder.binding) {
            val recipe = getItem(position)
            title.text = recipe?.title
            tags.removeAllViews()
            recipe.ingredients.forEach {
                val chip = Chip(root.context)
                chip.text = it
                tags.addView(chip)
            }
            if (onClickListener != null) {
                card.setOnClickListener {
                    onClickListener?.invoke(recipe.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainPagingAdapter.RecipeViewHolder =
        MainPagingAdapter.RecipeViewHolder(
            RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    var onClickListener: ((id: Int) -> Unit)? = null
}