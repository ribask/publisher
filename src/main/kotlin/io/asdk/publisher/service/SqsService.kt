package io.asdk.publisher.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.Gson
import io.asdk.publisher.config.SqsConfig
import io.asdk.publisher.model.SqsObject
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import software.amazon.awssdk.services.sqs.model.SendMessageResponse
import java.net.URI


@Service
class SqsService(
        private val sqs: SqsConfig,
        private val redisService: RedisService
  ) {

  var log = LoggerFactory.getLogger(SqsService::class.java)!!

  fun <T> publishIntoSqs(sqsObject: SqsObject<T>) {
    val sqsObj = sqsObject.toJson()
    try {
      sqs.requestSender(sqsObj)
      redisService.getObjectsToReprocess<SqsObject<T>>(sqsObject.api).forEach { obj ->
          sqs.requestSender(obj.toJson())
          redisService.removeReprocessedObject(obj)
      }
    } catch (e : Exception) {
      redisService.setObjectsToReprocess(sqsObject)
      log.error("An exception occurred when trying to send the message to SQS: $e!")
    }
  }
}

fun<T> T?.toObject(): SqsObject<T>? = jacksonObjectMapper().readValue(this as String)

private fun <T> SqsObject<T>.toJson() = Gson().toJson(this)
