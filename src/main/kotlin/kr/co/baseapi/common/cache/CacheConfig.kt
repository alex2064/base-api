package kr.co.baseapi.common.cache

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration


@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheManager(redisConnectionFactory: RedisConnectionFactory): RedisCacheManager {
        val objectMapper: ObjectMapper = ObjectMapper().apply {
            findAndRegisterModules()        // Java8 날짜와 시간 API 등의 모듈 처리를 자동화
            enable(SerializationFeature.INDENT_OUTPUT)      // 출력되는 Json을 보기 좋게 들여쓰기
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)     // Java 날짜와 시간을 타임스탬프가 아닌 ISO 형식의 문자열로 직렬화
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // 알 수 없는 속성은 무시
            activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder().allowIfBaseType(Any::class.java).build(),
                ObjectMapper.DefaultTyping.EVERYTHING,
                JsonTypeInfo.As.PROPERTY
            )
        }

        val serializer: Jackson2JsonRedisSerializer<Any> = Jackson2JsonRedisSerializer(objectMapper, Any::class.java)

        val config: RedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(SerializationPair.fromSerializer(serializer))
            .entryTtl(Duration.ofMinutes(10L))

        return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(config)
            .build()
    }
}
