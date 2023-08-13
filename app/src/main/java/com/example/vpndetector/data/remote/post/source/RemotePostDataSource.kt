package com.example.vpndetector.data.remote.post.source

import com.example.vpndetector.base.BaseDataSource
import com.example.vpndetector.data.remote.post.PostApiService
import javax.inject.Inject

class RemotePostDataSource @Inject constructor(
    private val postApiService: PostApiService,
) : BaseDataSource() {

    suspend fun getPosts() = getResult {
        postApiService.getPost()
    }
}
