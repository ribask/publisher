package io.asdk.publisher.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.io.Serializable


@JsonSerialize
@JsonDeserialize
data class SqsObject<T>(
    @JsonProperty val api: String? = "",
    @JsonProperty val data: T? = null
) : Serializable
