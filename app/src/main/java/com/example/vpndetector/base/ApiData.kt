package com.example.vpndetector.base

data class ApiData<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): ApiData<T> {
            return ApiData(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): ApiData<T> {
            return ApiData(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): ApiData<T> {
            return ApiData(Status.LOADING, data, null)
        }
    }
}
