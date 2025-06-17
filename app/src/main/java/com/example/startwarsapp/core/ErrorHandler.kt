package com.example.startwarsapp.core

import javax.inject.Inject

class ErrorHandler @Inject constructor() {
    suspend fun <T> runCatching(block: suspend () -> T): Result<T> =
        try {
            Result.success(block())
        } catch (e: Exception) {
            Result.failure(e)
        }
}