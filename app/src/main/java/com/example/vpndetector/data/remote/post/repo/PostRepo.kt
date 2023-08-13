package com.example.vpndetector.data.remote.post.repo

import com.example.vpndetector.base.ApiData
import com.example.vpndetector.data.remote.post.model.PostListItem
import com.example.vpndetector.data.remote.post.source.RemotePostDataSource
import javax.inject.Inject

class PostRepo @Inject constructor(
    private val remotePostDataSource: RemotePostDataSource,
) {

    suspend fun getPosts(): ApiData<List<PostListItem>> =
        remotePostDataSource.getPosts()
}
