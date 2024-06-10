package kr.co.baseapi.common.redis.component

import mu.KotlinLogging
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class RedisKeyExpirationListener(
    private val stringRedisTemplate: StringRedisTemplate
) {

    fun removeRedisHashKey(expiredKey: String) {
        log.debug { "[Redis] | expiredKey: $expiredKey" }

        val keyName: String = expiredKey.substringBeforeLast(":")
        val member: String = expiredKey.substringAfterLast(":")

        stringRedisTemplate.opsForSet().remove(keyName, member)
    }
}