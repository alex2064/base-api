package kr.co.baseapi.common.redis.component

import kr.co.baseapi.common.redis.enums.LockType
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisLockManager(
    private val stringRedisTemplate: StringRedisTemplate
) {

    /**
     * 잠금(다른곳에서 선점시 false)
     * 성공적으로 잠금을 설정하면 true, 이미 잠금이 있으면 false 반환
     */
    fun lock(lockType: LockType, id: Long): Boolean {
        val key: String = makeKey(lockType, id)
        return stringRedisTemplate
            .opsForValue()
            .setIfAbsent(key, "ING", Duration.ofMinutes(1L))
            ?: false
    }

    /**
     * 지정된 lockType 과 id 를 가진 락 해제
     */
    fun unlock(lockType: LockType, id: Long) {
        val key: String = makeKey(lockType, id)
        stringRedisTemplate.delete(key)
    }

    /**
     * Lock Key 생성(object-type:id)
     */
    private fun makeKey(lockType: LockType, id: Long): String = "${lockType.objectType}:$id"
}