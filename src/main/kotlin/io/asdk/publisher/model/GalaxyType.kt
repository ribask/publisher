package io.asdk.publisher.model

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
@JsonDeserialize
enum class GalaxyType {
    @JsonAlias("elliptical") ELLIPTICAL,
    @JsonAlias("spiral") SPIRAL,
}
