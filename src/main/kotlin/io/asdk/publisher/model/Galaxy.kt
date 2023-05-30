package io.asdk.publisher.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.io.Serializable

@JsonSerialize
@JsonDeserialize
data class Galaxy(
    @JsonProperty val id : Long? = null,
    @JsonProperty val name : String? = null,
    @JsonProperty val type : GalaxyType? = null,
) : Serializable
