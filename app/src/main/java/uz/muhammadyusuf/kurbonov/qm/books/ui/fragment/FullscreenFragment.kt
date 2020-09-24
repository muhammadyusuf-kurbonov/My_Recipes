@file:Suppress("DEPRECATION")

package uz.muhammadyusuf.kurbonov.qm.books.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.muhammadyusuf.kurbonov.qm.books.R
import uz.muhammadyusuf.kurbonov.qm.books.viewmodel.main.MainViewModel

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or WindowManager.LayoutParams.FLAG_FULLSCREEN)

        (activity as AppCompatActivity?)?.supportActionBar?.hide()
        activity?.actionBar?.hide()


        lifecycleScope.launch {
            delay(1500)
            val model by activityViewModels<MainViewModel>()
            model.navController.navigate(FullscreenFragmentDirections.actionFullscreenFragmentToMainViewFragment())
        }

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
    }

    override fun onPause() {
        super.onPause()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or WindowManager.LayoutParams.FLAG_FULLSCREEN)
        (activity as AppCompatActivity?)?.supportActionBar?.show()
        activity?.actionBar?.show()
        // Clear the systemUiVisibility flag
        view?.systemUiVisibility = 0
    }

}