package com.samsaz.githubsearch.util

import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

suspend fun <T: Any> safeCallDeferred(deferred: Deferred<Response<T>>): Result<T> =
    safeCall {
        val result = deferred.await()
        if (result.isSuccessful) {
            val body = result.body()
            if (body != null)
                return@safeCall Result.Success(body)
        }
        Result.Error(IOException("Could not get result"))
    }

suspend fun <T : Any> safeCall(call: suspend () -> Result<T>): Result<T> {
    return try {
        call()
    } catch (e: Exception) {
        Result.Error(e)
    }
}