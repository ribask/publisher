package io.asdk.publisher.service

import io.asdk.publisher.model.Galaxy
import io.asdk.publisher.model.SqsObject
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
