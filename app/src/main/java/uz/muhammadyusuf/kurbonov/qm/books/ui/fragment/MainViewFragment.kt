package uz.muhammadyusuf.kurbonov.qm.books.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import uz.muhammadyusuf.kurbonov.qm.books.ui.adapters.MainRecyclerAdapter
import uz.muhammadyusuf.kurbonov.qm.books.R
import uz.muhammadyusuf.kurbonov.qm.books.databinding.FragmentMainViewBinding
import uz.muhammadyusuf.kurbonov.qm.books.viewmodel.MainViewModel


/**
 * A simple [Fragment] subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
class MainViewFragment : Fragment(R.layout.fragment_main_view) {

    private val viewModel by viewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MainRecyclerAdapter()

        viewModel.initDatabase(requireContext())
        viewModel.generateFakeData()

        viewModel.allPagedData.observe(viewLifecycleOwner){
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }

        val binding = FragmentMainViewBinding.bind(view)
        binding.mainList.adapter = adapter
        binding.mainList.layoutManager = LinearLayoutManager(requireContext())
    }
}