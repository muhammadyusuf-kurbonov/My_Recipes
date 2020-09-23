package uz.muhammadyusuf.kurbonov.qm.books.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import uz.muhammadyusuf.kurbonov.qm.books.R
import uz.muhammadyusuf.kurbonov.qm.books.databinding.FragmentDetailsBinding
import uz.muhammadyusuf.kurbonov.qm.books.viewmodel.main.MainViewModel

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    private val model by activityViewModels<MainViewModel>()
    private var supportActionBar: ActionBar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsBinding.bind(view)
        supportActionBar = (activity as AppCompatActivity?)?.supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        lifecycleScope.launch {
            val id = DetailsFragmentArgs.fromBundle(requireArguments()).mealId
            val recipe = model.repository.getRecipe(id)
            binding.toolbar.title = recipe.title
            binding.toolbar.setTitleTextColor(Color.WHITE)
            supportActionBar?.hide()

            Picasso.get()
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQzCogtFg0-5rC5Q8SFLkzuSgp5ULeX78c5sA&usqp=CAU")
                .placeholder(R.drawable.ic_baseline_sync_24)
                .error(ColorDrawable(Color.parseColor("#FFC107")))
                .into(binding.appBarImage)
        }
    }

    override fun onPause() {
        super.onPause()
        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar = null
    }
}