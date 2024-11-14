package com.example.posttemplate.utils

import arrow.core.Either
import arrow.core.left

suspend fun <T> check(block: suspend () -> T): Either<Throwable, T> {
    return try {
        Either.Right(block())
    } catch (e: Throwable) {
        e.left()
    }
}

fun fail(message: String): Nothing {
    // customise your logic
    throw RuntimeException(message)
}
