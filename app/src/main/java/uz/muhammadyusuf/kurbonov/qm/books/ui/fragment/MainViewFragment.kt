package uz.muhammadyusuf.kurbonov.qm.books.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    private val viewModel by viewModels<MainViewModel>()

    companion object {
        private const val TAG = "MainFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MainListAdapter()

        viewModel.initDatabase(requireContext())
        viewModel.generateFakeData()

        val binding = FragmentMainViewBinding.bind(view)

        binding.mainList.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        binding.mainList.adapter = adapter
        binding.mainList.requestLayout()


        lifecycleScope.launch {
            Log.d(TAG, "launched coroutine for paging data load")
            adapter.submitList(viewModel.repository.getAllData())
        }


        binding.btnManualTest.setOnClickListener {
            lifecycleScope.launch {
                val data = viewModel.repository.getAllData()
                Toast.makeText(requireContext(), "Size of data is ${data.size}", Toast.LENGTH_SHORT)
                    .show()

                adapter.notifyDataSetChanged()
            }
        }
    }
}