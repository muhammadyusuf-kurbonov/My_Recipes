package uz.muhammadyusuf.kurbonov.qm.books.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import uz.muhammadyusuf.kurbonov.qm.books.R
import uz.muhammadyusuf.kurbonov.qm.books.databinding.ActivityMainBinding
import uz.muhammadyusuf.kurbonov.qm.books.viewmodel.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private val model by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)


        model.initDatabase(this)

        model.navController =
            (supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment).navController

        model.activityBroadcast.observe(this) {
            when (it) {
                MainViewModel.HIDE_KEYBOARD -> {
                    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                        binding.root.windowToken,
                        0
                    )
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        model.navController.popBackStack(R.id.fullscreenFragment, false)
    }
}