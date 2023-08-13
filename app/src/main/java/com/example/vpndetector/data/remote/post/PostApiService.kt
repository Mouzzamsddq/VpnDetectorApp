package com.example.vpndetector.data.remote.post

import com.example.vpndetector.data.remote.post.model.PostListItem
import retrofit2.Response
import retrofit2.http.GET

interface PostApiService {
    @GET("/posts")
    suspend fun getPost(): Response<List<PostListItem>>
}
