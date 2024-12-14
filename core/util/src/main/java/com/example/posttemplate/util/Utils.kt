package com.example.posttemplate.util

fun String.fail(): Nothing {
    // customise your logic
    throw RuntimeException(this)
}
