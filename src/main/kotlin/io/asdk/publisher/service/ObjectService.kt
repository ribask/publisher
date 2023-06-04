package io.asdk.publisher.service

import io.asdk.publisher.wrapper.ObjectWrapper
import io.asdk.publisher.model.SqsObject
import org.springframework.stereotype.Service


@Service
class ObjectService(
    private val sqsService: SqsService
) {
    fun <T : ObjectWrapper> process(obj: T): Boolean {
        obj.toSqsObject(getType(obj)).also {
            sqsService.publishIntoSqs(it)
            return true
        }
    }
}

private fun <T : ObjectWrapper> getType(obj: T) = obj.reference
private fun <T> T.toSqsObject(api: String): SqsObject<T> = SqsObject(api, this)