package com.example.posttemplate.utils

import arrow.core.Either
import arrow.core.left

suspend fun <T> check(block: suspend () -> T): Either<AppFailure, T> {
    return try {
        Either.Right(block())
    } catch (e: Throwable) {
        when (e) {
            is java.net.UnknownHostException -> AppFailure.NetworkFailure("Network not available", e).left()
            is java.sql.SQLException -> AppFailure.DatabaseFailure("Database operation failed", e).left()
            is IllegalArgumentException -> AppFailure.ValidationFailure(e.message ?: "Validation error").left()
            else -> AppFailure.UnknownFailure("An unknown error occurred", e).left()
        }
    }
}


fun String.fail(): Nothing {
    // customise your logic
    throw RuntimeException(this)
}
