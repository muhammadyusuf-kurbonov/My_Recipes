package uz.muhammadyusuf.kurbonov.qm.books.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import kotlinx.coroutines.launch
import uz.muhammadyusuf.kurbonov.qm.books.R
import uz.muhammadyusuf.kurbonov.qm.books.databinding.FragmentMainViewBinding
import uz.muhammadyusuf.kurbonov.qm.books.ui.adapters.MainListAdapter
import uz.muhammadyusuf.kurbonov.qm.books.viewmodel.main.MainViewModel


/**
 * A simple [Fragment] subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
class MainViewFragment : Fragment(R.layout.fragment_main_view) {

    private val viewModel by activityViewModels<MainViewModel>()

    companion object {
        private const val TAG = "MainFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MainListAdapter()
        val binding = FragmentMainViewBinding.bind(view)

        val linearLayoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        binding.mainList.layoutManager = linearLayoutManager
        binding.mainList.adapter = adapter
        binding.mainList.requestLayout()


        lifecycleScope.launch {
            Log.d(TAG, "launched coroutine for paging data load")
            viewModel.repository.listenAllData().observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }


        binding.floatingActionButton.setOnClickListener {
            viewModel.navController.navigate(R.id.addRecipeFragment)
        }
    }
}