package com.example.vpndetector.feature

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vpndetector.databinding.ActivityMainBinding
import com.example.vpndetector.feature.adapter.PostAdapter
import com.example.vpndetector.feature.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val postAdapter: PostAdapter by lazy {
        PostAdapter()
    }
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
            postRv.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = postAdapter
            }
            getPostButton.setOnClickListener {
                mainViewModel.getAllPosts()
            }
        }
    }

    private fun setObservers() {
        mainViewModel.postListLiveData.observe(this) { postListState ->
            when (postListState) {
                is MainViewModel.PostListState.Success -> {
                    changeVisibility(
                        loaderShow = false,
                        recyclerViewShow = true,
                    )
                    postListState.postList.let {
                        postAdapter.setPostItemList(it)
                    }
                }

                is MainViewModel.PostListState.Error -> {
                    changeVisibility(
                        loaderShow = false,
                        recyclerViewShow = false,
                    )
                    showAlertMessage(postListState.errorMessage)
                }

                is MainViewModel.PostListState.Loading -> {
                    changeVisibility(
                        loaderShow = true,
                        recyclerViewShow = true,
                    )
                }
            }
        }
    }

    private fun changeVisibility(loaderShow: Boolean, recyclerViewShow: Boolean) {
        binding?.apply {
            progressBar.isVisible = loaderShow
            postRv.isVisible = recyclerViewShow
        }
    }

    private fun showAlertMessage(errorMessage: String) {
        val alertDialog = AlertDialog.Builder(this@MainActivity)
        alertDialog.apply {
            setTitle("Error...!")
            setMessage(errorMessage)
            setNegativeButton(
                "Retry",
            ) { p0, p1 -> mainViewModel.getAllPosts() }
            setPositiveButton(
                "Ok",
            ) { p0, p1 -> }
            show()
        }
    }
}
