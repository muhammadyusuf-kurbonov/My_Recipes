package uz.muhammadyusuf.kurbonov.qm.books.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
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

    private var mealId: Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsBinding.bind(view)
        supportActionBar = (activity as AppCompatActivity?)?.supportActionBar
        mealId = DetailsFragmentArgs.fromBundle(requireArguments()).mealId

        lifecycleScope.launch {
            val recipe = model.repository.getRecipe(mealId)
            binding.toolbar.title = recipe.title
            binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            binding.toolbar.setNavigationOnClickListener {
                model.navController.popBackStack()
            }

            Picasso.get()
                .load(recipe.imageLink)
                .placeholder(R.drawable.ic_baseline_sync_24)
                .error(ColorDrawable(Color.parseColor("#FFC107")))
                .into(binding.appBarImage)
        }

        binding.btnEdit.setOnClickListener {
            model.navController.navigate(
                DetailsFragmentDirections.actionDetailsFragmentToAddRecipeFragment().also {
                    it.mealId = mealId
                })
        }
        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.confirm_message))
                .setTitle(getString(R.string.confirm_title))
                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                    model.viewModelScope.launch {
                        model.repository.delete(mealId)
                    }
                    dialog.dismiss()
                    model.goHomFragment()
                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onPause() {
        super.onPause()
        supportActionBar?.show()

        supportActionBar = null
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.hide()
    }
}