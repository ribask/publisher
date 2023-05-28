package io.asdk.publisher.sqsService

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.asdk.publisher.models.SqsObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import java.net.URI

@Service
class SqsService {

  @Value("\${sqs.queueUrl}")
  private lateinit var sqsQueue : String

  fun <T> publishIntoSqs(sqsObject: SqsObject<T>) {
    val queueUrl = sqsQueue
    val message = sqsObject.toJson()

    val sqsClient = SqsClient.builder()
      .endpointOverride(URI(sqsQueue))
      .region(Region.US_EAST_1)
      .credentialsProvider(DefaultCredentialsProvider.create())
      .build()

    val request = SendMessageRequest.builder()
      .queueUrl(queueUrl)
      .messageBody(message)
      .build()

    val response = sqsClient.sendMessage(request)


    println("Message sent. MessageId: ${response.messageId()}")
  }

}

private fun <T> SqsObject<T>.toJson() = Gson().toJson(this)
