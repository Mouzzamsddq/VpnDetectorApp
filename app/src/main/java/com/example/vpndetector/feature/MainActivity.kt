package com.example.vpndetector.feature

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.vpndetector.databinding.ActivityMainBinding
import com.example.vpndetector.feature.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setObservers()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding?.apply {
            getPostButton.setOnClickListener {
                mainViewModel.getAllPosts()
            }
        }
    }

    private fun setObservers() {
        mainViewModel.postListLiveData.observe(this) { postListState ->
            when (postListState) {
                is MainViewModel.PostListState.Success -> {
                    showToast("Success")
                }

                is MainViewModel.PostListState.Error -> {
                    showToast("Error")
                }

                is MainViewModel.PostListState.Loading -> {
                    showToast("Loading")
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }
}
