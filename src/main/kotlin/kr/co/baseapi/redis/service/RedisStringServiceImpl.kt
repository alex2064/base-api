package kr.co.baseapi.redis.service

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisStringServiceImpl(
    private val stringRedisTemplate: StringRedisTemplate
) : RedisStringService {


    /**
     * Key 생성(object-type:id)
     */
    private fun makeUserInfoKey(userId: Long): String = "user:info:$userId"

    override fun findUserInfo(userId: Long): String {
        val key: String = makeUserInfoKey(userId)
        val value: String? = stringRedisTemplate.opsForValue().get(key)

        return if (value != null) {
            value
        } else {
            // DB에서 조회한 값으로 가정
            val dbValue: String = ""

            // ttl 1분으로 데이터 저장
            stringRedisTemplate.opsForValue().set(key, dbValue, java.time.Duration.ofMinutes(1L))
            dbValue
        }
    }


    /**
     * Key 생성(object-type:id)
     */
    private fun makeSessionKey(sessionId: String): String = "session:$sessionId"

    override fun getSession(sessionId: String): String? {
        val key: String = makeSessionKey(sessionId)
        return stringRedisTemplate.opsForValue().get(key)
    }

    override fun saveSession(sessionId: String, sessionData: String): Boolean {
        val key: String = makeSessionKey(sessionId)
        stringRedisTemplate.opsForValue().set(key, sessionData, Duration.ofMinutes(10L))

        return true
    }

    override fun deleteSession(sessionId: String): Boolean {
        val key: String = makeSessionKey(sessionId)
        stringRedisTemplate.delete(key)

        return true
    }


    /**
     * Key 생성(object-type:id)
     */
    private fun makeConfigKey(configKey: String): String = "config:$configKey"

    override fun getConfig(configKey: String): String? {
        val key: String = makeConfigKey(configKey)
        return stringRedisTemplate.opsForValue().get(key)
    }

    override fun saveConfig(configKey: String, configValue: String): Boolean {
        val key: String = makeConfigKey(configKey)
        stringRedisTemplate.opsForValue().set(key, configValue)

        return true
    }


    /**
     * Key 생성(object-type:id)
     */
    private fun makeSequenceKey(keyword: String): String = "sequence:$keyword"

    override fun getSequence(keyword: String): Long {
        val key: String = makeSequenceKey(keyword)
        return stringRedisTemplate.opsForValue().increment(key, 1L) ?: 0L
    }


    /**
     * Key 생성(object-type:id)
     */
    private fun makeApplyKey(applyKey: String): String = "apply:status:$applyKey"

    override fun onApply(applyKey: String): Boolean {
        val key: String = makeApplyKey(applyKey)
        stringRedisTemplate.opsForValue().set(key, "ON")

        return true
    }

    override fun offApply(applyKey: String): Boolean {
        val key: String = makeApplyKey(applyKey)
        stringRedisTemplate.opsForValue().set(key, "OFF")

        return true
    }

    override fun isApply(applyKey: String): Boolean {
        val key: String = makeApplyKey(applyKey)

        return stringRedisTemplate.opsForValue().get(key) == "ON"
    }
}