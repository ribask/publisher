package io.asdk.publisher.service

import io.asdk.publisher.config.SqsConfig
import io.asdk.publisher.model.SqsObject
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import software.amazon.awssdk.services.sqs.model.SendMessageResponse

@SpringBootTest()
class SqsServiceTest {

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<*, *>

    @Autowired
    private lateinit var redisService: RedisService

    @MockBean
    private lateinit var sqs: SqsConfig
    @Test
    fun publishIntoSqsTest() {
        assertTrue(true)
    }

}