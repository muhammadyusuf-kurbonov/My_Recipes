package uz.muhammadyusuf.kurbonov.qm.books.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recipe_item.view.*
import uz.muhammadyusuf.kurbonov.qm.books.database.recipes.RecipeModel
import uz.muhammadyusuf.kurbonov.qm.books.databinding.RecipeItemBinding

class MainPagingAdapter :
    PagingDataAdapter<RecipeModel, MainPagingAdapter.RecipeViewHolder>(DiffResolver()) {


    class RecipeViewHolder(itemView: RecipeItemBinding) : RecyclerView.ViewHolder(itemView.root)

    class DiffResolver : DiffUtil.ItemCallback<RecipeModel>() {
        override fun areItemsTheSame(oldItem: RecipeModel, newItem: RecipeModel): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: RecipeModel, newItem: RecipeModel): Boolean =
            oldItem == newItem


    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        with(holder.itemView){
            val recipe = getItem(position)
            title.text = recipe?.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(
            RecipeItemBinding.inflate(LayoutInflater.from(parent.context))
        )
}