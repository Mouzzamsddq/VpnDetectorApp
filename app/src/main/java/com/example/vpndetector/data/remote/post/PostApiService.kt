package com.example.vpndetector.data.remote.post

import com.example.vpndetector.data.remote.post.model.PostList
import retrofit2.Response
import retrofit2.http.GET

interface PostApiService {
    @GET("/posts")
    fun getPost(): Response<PostList>
}
