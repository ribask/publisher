package io.asdk.publisher.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.lettuce.core.ReadFrom
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
class RedisConfig {

    @Value("\${redis.url}")
    private lateinit var redisUrl: String

    @Value("\${redis.port}")
    private lateinit var port: String

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        val clientConfig =
            LettuceClientConfiguration
                .builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .build()
        val serverConfig = RedisStandaloneConfiguration(
            redisUrl,
            port.toInt()
        )
        return LettuceConnectionFactory(serverConfig, clientConfig)
    }

    @Bean
    fun <K, V>redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<K, V>? {
        return RedisTemplate<K, V>().apply {
            this.connectionFactory = redisConnectionFactory
            this.valueSerializer = GenericJackson2JsonRedisSerializer()
            this.keySerializer = StringRedisSerializer()
            this.afterPropertiesSet()
            this.setDefaultSerializer(GenericJackson2JsonRedisSerializer())
        }
    }
}
