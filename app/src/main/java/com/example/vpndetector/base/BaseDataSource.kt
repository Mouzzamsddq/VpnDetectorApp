package com.example.vpndetector.base

import com.google.gson.Gson
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ApiData<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let { return ApiData.success(it) }
                if (body == null) {
                    return ApiData.success(null)
                }
            }
            var errorMessage = ""
            try {
                val gson = Gson()
                val jsonObject =
                    gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                errorMessage = jsonObject.errors.getOrNull(0)?.reason ?: ""
            } catch (e: java.lang.Exception) {
                errorMessage = response.errorBody()?.string() ?: response.message()
            } finally {
                if (errorMessage.isEmpty()) errorMessage = "Oops! Something Went Wrong"
            }
            return error(errorMessage)
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): ApiData<T> {
        return ApiData.error(message)
    }
}
