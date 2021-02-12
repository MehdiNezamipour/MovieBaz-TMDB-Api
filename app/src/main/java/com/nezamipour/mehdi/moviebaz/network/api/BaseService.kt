package com.nezamipour.mehdi.moviebaz.network.api

import retrofit2.Response

abstract class BaseService {

    protected suspend fun <T : Any> createCall(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Result.Success(body)
                }
            }
            return Result.Error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return Result.Error(e.message ?: e.toString())
        }
    }


}