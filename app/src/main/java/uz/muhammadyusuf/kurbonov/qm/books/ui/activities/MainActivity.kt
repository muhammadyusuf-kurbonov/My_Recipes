package uz.muhammadyusuf.kurbonov.qm.books.ui.activities

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
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

        requestPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 241) {
            if (grantResults[0] != PERMISSION_GRANTED) {
                AlertDialog.Builder(this)
                    .setNeutralButton(android.R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setMessage("Without permission app can't show images")
                    .show()
            }
        }
    }

    private fun requestPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 241)
        }
    }

    override fun onRestart() {
        super.onRestart()
        model.navController.popBackStack(R.id.fullscreenFragment, false)
    }
}