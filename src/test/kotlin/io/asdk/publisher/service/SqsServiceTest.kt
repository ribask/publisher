package io.asdk.publisher.service

import io.asdk.publisher.config.SqsConfig
import io.asdk.publisher.controller.GalaxiesController
import io.asdk.publisher.model.Galaxy
import io.asdk.publisher.model.SqsObject
import io.asdk.publisher.utils.CreationUtils
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension
import software.amazon.awssdk.services.sqs.model.SendMessageResponse

@ExtendWith(SpringExtension::class)
@WebMvcTest(GalaxiesController::class)
class SqsServiceTest {

    @MockBean
    private lateinit var galaxiesController: GalaxiesController

    @MockBean
    private lateinit var objectService: ObjectService

    @Test
    fun publishIntoSqsTest() {
        val utils = CreationUtils()

        val redisTemplate = mockk<RedisTemplate<Any, Any>>()
        val sqsConfig = mockk<SqsConfig>()

        val redisService = RedisService(redisTemplate)
        val sqsService = SqsService(sqsConfig, redisService)

        val request = slot<String>()
        val response = slot<SendMessageResponse>()


        val key = slot<Any>()
        val membersResponse = slot<MutableSet<Any>>()

        val galaxyKey = slot<String>()
        val input = slot<SqsObject<Any>>()

        every { sqsConfig.requestSender(capture(request)) } answers { response.captured }
        every { redisTemplate.opsForSet().members(capture(key)) } answers { membersResponse.captured }
        every { redisTemplate.opsForSet().add(capture(galaxyKey), capture(input)) } returns 1L
        every { redisTemplate.opsForSet().remove(capture(galaxyKey), capture(input)) } returns 1L

        sqsService.publishIntoSqs(utils.getSqsObject())

        assert(true)
    }

}