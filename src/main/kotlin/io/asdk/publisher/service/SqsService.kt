package io.asdk.publisher.service

import com.google.gson.Gson
import io.asdk.publisher.config.SqsConfig
import io.asdk.publisher.model.SqsObject
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SqsService(
    private val sqs: SqsConfig,
    private val redisService: RedisService
  ) {

  var log = LoggerFactory.getLogger(SqsService::class.java)!!

  fun <T> publishIntoSqs(sqsObject: SqsObject<T>): Boolean {
    val sqsObj = sqsObject.toJson()
    try {
      sqs.requestSender(sqsObj)
      redisService.getObjectsToReprocess<SqsObject<T>>(sqsObject.api).forEach { obj ->
          sqs.requestSender(obj.toJson())
          redisService.removeReprocessedObject(obj)
      }
        return true
    } catch (e : Exception) {
      redisService.setObjectsToReprocess(sqsObject)
      log.error("An exception occurred when trying to send the message to SQS: $e!")
    }
      return false
  }
}

private fun <T> SqsObject<T>.toJson() = Gson().toJson(this)
