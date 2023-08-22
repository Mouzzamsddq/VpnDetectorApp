package com.example.vpndetector.feature

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vpndetector.databinding.ActivityMainBinding
import com.example.vpndetector.feature.adapter.PostAdapter
import com.example.vpndetector.feature.viewmodel.MainViewModel
import com.example.vpndetector.utils.VpnUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

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

                is MainViewModel.PostListState.ValidationError -> {
                    changeVisibility(
                        loaderShow = false,
                        recyclerViewShow = false,
                    )
                    showAlertMessage(postListState.errorMessage)
                }
            }
        }
        lifecycleScope.launch {
            VpnUtils.isVpnActive(this@MainActivity).collectLatest { isActive ->
                if (isActive) {
                    showAlertMessage("vpn is active", true)
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

    private fun showAlertMessage(errorMessage: String, fromVpn: Boolean = false) {
        val alertDialog = AlertDialog.Builder(this@MainActivity)
        alertDialog.apply {
            setTitle("Error...!")
            setMessage(errorMessage)
            setNegativeButton(
                "Retry",
            ) { p0, p1 -> mainViewModel.getAllPosts() }
            setPositiveButton(
                "Ok",
            ) { p0, p1 -> if (fromVpn) finishAffinity() }
            show()
        }
    }
}
