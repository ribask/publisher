package io.asdk.publisher.utils

import io.asdk.publisher.model.Galaxy
import io.asdk.publisher.model.GalaxyType
import io.asdk.publisher.model.SqsObject

class CreationUtils {
    fun getGalaxy() = Galaxy("api", 1, "milky way", GalaxyType.ELLIPTICAL)

    fun getSqsObject() = SqsObject<Galaxy>(api = GalaxyType.ELLIPTICAL.toString(), getGalaxy())
}