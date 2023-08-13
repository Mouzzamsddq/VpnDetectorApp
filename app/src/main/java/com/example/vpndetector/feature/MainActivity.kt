package com.example.vpndetector.feature

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import assertk.assertions.support.show
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
                    showLogMessage("Success")
                }

                is MainViewModel.PostListState.Error -> {
                    showLogMessage("Error : ${postListState.errorMessage}")
                }

                is MainViewModel.PostListState.Loading -> {
                    showLogMessage("Loading")
                }
            }
        }
    }

    private fun showLogMessage(message: String) {
        Log.d("kkk","response status : $message")
    }
}
