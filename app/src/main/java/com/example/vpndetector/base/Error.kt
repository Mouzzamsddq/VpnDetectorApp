package com.example.vpndetector.base

import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("code")
    val code: String,
    @SerializedName("datetime")
    val datetime: String,
    @SerializedName("reason")
    val reason: String,
)
