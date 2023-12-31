package com.example.vpndetector.feature.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vpndetector.base.ApiData
import com.example.vpndetector.data.remote.post.model.PostListItem
import com.example.vpndetector.data.remote.post.repo.PostRepo
import com.example.vpndetector.utils.RootUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val postRepo: PostRepo,
) : ViewModel() {

    private val _postListLiveData = MutableLiveData<PostListState>()
    val postListLiveData: LiveData<PostListState> = _postListLiveData

    fun getAllPosts() = viewModelScope.launch {
        _postListLiveData.postValue(PostListState.Loading)
        if (RootUtils.isDeviceRooted) {
            _postListLiveData.postValue(PostListState.ValidationError("Device is rooted"))
            return@launch
        }
        val response = postRepo.getPosts()
        when (response.status) {
            ApiData.Status.SUCCESS -> {
                response.data?.let {
                    _postListLiveData.postValue(PostListState.Success(it))
                }
            }

            ApiData.Status.ERROR -> {
                response.message?.let {
                    _postListLiveData.postValue(PostListState.Error(it))
                } ?: _postListLiveData.postValue(PostListState.Error("Something went wrong!!"))
            }

            ApiData.Status.LOADING -> {
                _postListLiveData.postValue(PostListState.Loading)
            }
        }
    }

    sealed class PostListState {
        data class Success(val postList: List<PostListItem>) : PostListState()

        data class Error(val errorMessage: String) : PostListState()

        data class ValidationError(val errorMessage: String) : PostListState()

        object Loading : PostListState()
    }
}
