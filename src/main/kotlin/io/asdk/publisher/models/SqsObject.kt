package io.asdk.publisher.models

data class SqsObject<T>(
    val api: String,
    val data: T
)
