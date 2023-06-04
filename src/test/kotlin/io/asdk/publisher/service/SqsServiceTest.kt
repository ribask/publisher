package io.asdk.publisher.service

import io.asdk.publisher.config.SqsConfig
import io.asdk.publisher.model.Galaxy
import io.asdk.publisher.model.SqsObject
import io.asdk.publisher.utils.CreationUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import software.amazon.awssdk.services.sqs.model.SendMessageResponse

@SpringBootTest
class SqsServiceTest {


    @Test
    fun publishIntoSqsTest() {
        val utils = CreationUtils()
        val redisService = mockk<RedisService>()
        val sqsConfig = mockk<SqsConfig>()
        val sqsService = SqsService(sqsConfig, redisService)

        val request = slot<String>()
        val response = slot<SendMessageResponse>()
        val input = slot<SqsObject<Galaxy>>()

        every { sqsConfig.requestSender(capture(request)) } returns response.captured
        every { redisService.setObjectsToReprocess(capture(input)) } returns Unit
        every { redisService.removeReprocessedObject(capture(input)) } returns Unit

        sqsService.publishIntoSqs(utils.getSqsObject())

        assert(true)

    }

}