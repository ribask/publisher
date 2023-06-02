package io.asdk.publisher.service

import io.asdk.publisher.`interface`.Wrapper
import io.asdk.publisher.model.SqsObject
import org.springframework.stereotype.Service


@Service
class ObjectService(
    private val sqsService: SqsService
) {
    fun <T : Wrapper> process(obj: T) {
        obj.toSqsObject(getType(obj)).also {
            sqsService.publishIntoSqs(it)
        }
    }
}
fun <T : Wrapper> getType(obj: T) = obj.reference
private fun <T> T.toSqsObject(api: String): SqsObject<T> = SqsObject(api, this)