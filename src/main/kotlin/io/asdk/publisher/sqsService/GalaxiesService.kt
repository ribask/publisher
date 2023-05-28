package io.asdk.publisher.sqsService

import io.asdk.publisher.models.Galaxy
import io.asdk.publisher.models.SqsObject
import org.springframework.stereotype.Service

@Service
class GalaxiesService(
    private val sqsService: SqsService
) {

    fun process(galaxy: Galaxy) {
        galaxy.toSqsObject(galaxy.type.toString()).also {
            sqsService.publishIntoSqs(it)
        }
    }

}

private fun <T> T.toSqsObject(api: String): SqsObject<T> {
    return SqsObject(api, this)
}
