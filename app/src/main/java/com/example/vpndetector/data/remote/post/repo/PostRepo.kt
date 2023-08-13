package com.example.vpndetector.data.remote.post.repo

import com.example.vpndetector.base.ApiData
import com.example.vpndetector.data.remote.post.model.PostList
import com.example.vpndetector.data.remote.post.source.RemotePostDataSource
import javax.inject.Inject

class PostRepo @Inject constructor(
    private val remotePostDataSource: RemotePostDataSource,
) {

    suspend fun signUp(): ApiData<PostList> =
        remotePostDataSource.getPosts()
}
