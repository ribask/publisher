package io.asdk.publisher.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import software.amazon.awssdk.services.sqs.model.SendMessageResponse
import java.net.URI

@Configuration
class SqsConfig {

    @Value("\${sqs.queueUrl}")
    private var sqsQueue : String = "http://localhost:4566/000000000000/galaxies"

    @Value("\${sqs.uri}")
    private var uri : String = "http://localhost:4566"

    fun requestSender(message: String?): SendMessageResponse? {
        val request = SendMessageRequest.builder()
                .queueUrl(sqsQueue)
                .messageBody(message)
                .build()
        return this.sqsClient().sendMessage(request)
    }

    private fun sqsClient() =
        SqsClient.builder()
            .endpointOverride(URI(uri))
            .region(Region.US_EAST_1)
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build()
}