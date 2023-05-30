package io.asdk.publisher.service

import io.asdk.publisher.model.SqsObject
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<Any, Any>
) {

    @Suppress("UNCHECKED_CAST")
    fun <T> getObjectsToReprocess(api: String?): MutableSet<SqsObject<T>> {
        return redisTemplate.opsForSet().members(galaxyKey(api)) as MutableSet<SqsObject<T>>
    }

    fun<T> setObjectsToReprocess(sqsObject: SqsObject<T>) {
        redisTemplate.opsForSet().add(galaxyKey(sqsObject.api), sqsObject)
    }

    fun<T> removeReprocessedObject(sqsObject: SqsObject<T>) {
        redisTemplate.opsForSet().remove(galaxyKey(sqsObject.api), sqsObject)
    }

    private fun galaxyKey(api: String?) = "galaxy:${api}"

}