package uz.muhammadyusuf.kurbonov.qm.books.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import uz.muhammadyusuf.kurbonov.qm.books.R
import uz.muhammadyusuf.kurbonov.qm.books.database.TypeConverters
import uz.muhammadyusuf.kurbonov.qm.books.database.recipes.RecipeModel
import uz.muhammadyusuf.kurbonov.qm.books.databinding.FragmentAddRecipeBinding
import uz.muhammadyusuf.kurbonov.qm.books.viewmodel.main.MainViewModel
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
class AddRecipeFragment : DialogFragment() {

    companion object {
        const val PICK_IMAGE = 150
    }

    private val model: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentAddRecipeBinding
    private var imageUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    imageUri = data?.data
                    binding.btnSelectImage.setText(R.string.change_image_caption)
                    Picasso.get().load(data?.data)
                        .into(binding.img)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Something is wrong", Toast.LENGTH_SHORT)
                        .show()
                    binding.btnSelectImage.setText(R.string.select_image_caption)
                }
            } else {
                Toast.makeText(requireContext(), "You haven't selected image", Toast.LENGTH_SHORT)
                    .show()
                binding.btnSelectImage.setText(R.string.select_image_caption)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddRecipeBinding.bind(view)

        setupImageSelectTextView()

        setupAutoCompleteTextView()

        setupButtonsOnClickListeners()
    }

    private fun setupImageSelectTextView() {
        binding.btnSelectImage.setOnClickListener {

            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "image/*"

            val pickIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "image/*"

            val chooserIntent = Intent.createChooser(getIntent, "Select Image")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            startActivityForResult(chooserIntent, PICK_IMAGE)

        }
    }

    private fun setupAutoCompleteTextView() {
        lifecycleScope.launch {
            val ingredientsList = mutableListOf(
                "Potato",
                "Tomato",
                "Onion",
                "Cucumber",
                "Apple",
                "Banana",
                "Nok",
                "Shaftoli",
                "Carrot",
                "Milk"
            )
            model.repository.getAllDataDirect().forEach {
                ingredientsList.addAll(it.ingredients.map { ingredient ->
                    ingredient.trim().capitalize(Locale.ROOT)
                })
            }
            val newIngredientsList = ingredientsList.distinct()
            ingredientsList.clear()
            ingredientsList.addAll(newIngredientsList)
            val adapter =
                IngredientsAdapter(requireContext(), ingredientsList)
            binding.evIngredients.setAdapter(adapter)
        }
        binding.evIngredients.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
    }

    private fun setupButtonsOnClickListeners() {
        lateinit var submitFunction: (v: View) -> Unit

        val mealId = AddRecipeFragmentArgs.fromBundle(requireArguments()).mealId
        if (mealId < 0) {
            submitFunction = {
                model.viewModelScope.launch {
                    model.repository.insertNew(
                        RecipeModel(
                            0,
                            binding.evTitle.text.toString(),
                            binding.evDescription.text.toString(),
                            "Androider",
                            imageUri?.toString() ?: "",
                            TypeConverters().stringToList(
                                binding.evIngredients.text.toString().trimEnd(',', ' ', ';', '.')
                            )
                        )
                    )
                }
                model.goHomFragment()
            }
        } else {
            lifecycleScope.launch {
                val recipe = model.repository.getRecipe(mealId)
                with(binding) {
                    tvTitle.text = getString(
                        R.string.update_title,
                        recipe.title
                    )
                    btnSave.text = getString(R.string.update_caption)

                    evTitle.setText(recipe.title)
                    evDescription.setText(recipe.processDescription)
                    evIngredients.setText(TypeConverters().listToString(recipe.ingredients))
                    Picasso.get()
                        .load(recipe.imageLink)
                        .into(img)
                    btnSelectImage.setText(R.string.change_image_caption)
                }
            }
            submitFunction = {
                model.viewModelScope.launch {
                    model.repository.update(
                        RecipeModel(
                            mealId,
                            binding.evTitle.text.toString(),
                            binding.evDescription.text.toString(),
                            "Androider",
                            imageUri?.toString() ?: "",
                            TypeConverters().stringToList(
                                binding.evIngredients.text.toString().trimEnd(',', ' ', ';', '.')
                            )
                        )
                    )
                }
                model.goHomFragment()
            }
        }
        binding.btnSave.setOnClickListener(submitFunction)
        binding.btnCancel.setOnClickListener {
            model.goHomFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_recipe, container, false)
    }

    private class IngredientsAdapter(context: Context, val list: List<String>) :
        ArrayAdapter<String>(context, android.R.layout.simple_list_item_1) {
        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(constraint: CharSequence?): FilterResults {

                    return constraint?.run {
                        val suggestions = mutableListOf<String>()
                        list.forEach {
                            if (it.toLowerCase(Locale.ROOT)
                                    .contains(constraint.toString().toLowerCase(Locale.ROOT))
                            ) {
                                suggestions.add(it)
                            }
                        }
                        val result = FilterResults()
                        result.apply {
                            count = suggestions.size
                            values = suggestions
                        }
                    } ?: FilterResults()
                }

                override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                    results?.let {
                        clear()
                        if (results.count > 0) {
                            (results.values as ArrayList<*>).forEach {
                                add(it.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}