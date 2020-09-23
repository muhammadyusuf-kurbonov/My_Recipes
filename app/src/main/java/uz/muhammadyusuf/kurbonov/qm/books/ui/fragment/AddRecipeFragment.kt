package uz.muhammadyusuf.kurbonov.qm.books.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.MultiAutoCompleteTextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
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

    private val model: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAddRecipeBinding.bind(view)

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

        binding.btnCancel.setOnClickListener {
            model.goHomFragment()
        }
        binding.btnSave.setOnClickListener {
            model.viewModelScope.launch {
                model.repository.insertNew(
                    RecipeModel(
                        0,
                        binding.evTitle.text.toString(),
                        binding.evDescription.text.toString(),
                        "Androider",
                        "",
                        TypeConverters().stringToList(
                            binding.evIngredients.text.toString().trimEnd(',', ' ', ';', '.')
                        )
                    )
                )
            }
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