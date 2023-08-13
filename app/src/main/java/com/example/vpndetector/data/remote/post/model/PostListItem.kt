package com.example.vpndetector.data.remote.post.model


import com.google.gson.annotations.SerializedName

data class PostListItem(
    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
)