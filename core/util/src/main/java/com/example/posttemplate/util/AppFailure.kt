package com.example.posttemplate.util

sealed class AppFailure(message: String? = null, cause: Throwable? = null) : Throwable(message, cause) {
    class NetworkFailure(message: String? = null, cause: Throwable? = null) : AppFailure(message, cause)
    class DatabaseFailure(message: String? = null, cause: Throwable? = null) : AppFailure(message, cause)
    class ValidationFailure(message: String? = null) : AppFailure(message)
    class UnknownFailure(message: String? = null, cause: Throwable? = null) : AppFailure(message, cause)
}
