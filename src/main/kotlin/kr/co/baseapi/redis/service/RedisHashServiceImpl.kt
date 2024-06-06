package kr.co.baseapi.redis.service

import kr.co.baseapi.common.util.toLocalDate
import kr.co.baseapi.common.util.toString10
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class RedisHashServiceImpl(
    private val stringRedisTemplate: StringRedisTemplate
) : RedisHashService {


    /**
     * Key 생성(object-type:id)
     */
    private fun makeUserProfileKey(userId: Long): String = "user:profile:$userId"

    override fun findUserProfile(userId: Long): Map<String, Any> {
        val key: String = makeUserProfileKey(userId)
        val result: MutableMap<String, String> = stringRedisTemplate.opsForHash<String, String>().entries(key)

        // Redis Hash 데이터 타입은 기본적으로 문자열(String)만 저장할 수 있어서 읽어온 데이터를 원하는 타입으로 변환해서 사용
        return result.mapValues { (k, v) ->
            when (k) {
                "age" -> v.toLong()
                "birthday" -> v.toLocalDate()
                else -> v
            } ?: mapOf<String, Any>()
        }
    }

    override fun saveUserProfile(userId: Long, profile: Map<String, Any>): Boolean {
        val key: String = makeUserProfileKey(userId)

        // Redis Hash 데이터 타입은 기본적으로 문자열(String)만 저장할 수 있어서 저장할때 문자열로 변환해서 저장
        val hash: Map<String, String> = profile.mapValues { (k, v) ->
            when (v) {
                is LocalDate -> v.toString10()
                else -> v.toString()
            }
        }

        stringRedisTemplate.opsForHash<String, String>().putAll(key, hash)

        return true
    }

    override fun saveUserProfileField(userId: Long, field: String, value: Any): Boolean {
        val key: String = makeUserProfileKey(userId)

        // Redis Hash 데이터 타입은 기본적으로 문자열(String)만 저장할 수 있어서 저장할때 문자열로 변환해서 저장
        val value2: String = when (value) {
            is LocalDate -> value.toString10()
            else -> value.toString()
        }

        stringRedisTemplate.opsForHash<String, String>().put(key, field, value2)

        return true
    }

    override fun deleteUserProfile(userId: Long): Boolean {
        val key: String = makeUserProfileKey(userId)
        stringRedisTemplate.delete(key)

        return true
    }

    override fun deleteUserProfileField(userId: Long, field: String): Boolean {
        val key: String = makeUserProfileKey(userId)
        stringRedisTemplate.opsForHash<String, String>().delete(key, field)

        return true
    }
}