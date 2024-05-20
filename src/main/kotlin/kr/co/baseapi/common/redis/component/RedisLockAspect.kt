package kr.co.baseapi.common.redis.component

import kr.co.baseapi.common.redis.annotation.RedisLock
import kr.co.baseapi.common.redis.enums.LockType
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Aspect
@Component
class RedisLockAspect(
    private val redisLockManager: RedisLockManager
) {

    @Around("@annotation(redisLock)")
    fun lockMethod(pjp: ProceedingJoinPoint, redisLock: RedisLock): Any? {
        val signature: MethodSignature = pjp.signature as MethodSignature
        val parameterNames: Array<String> = signature.parameterNames
        val values: Array<Any> = pjp.args

        val type: LockType = redisLock.type
        val index: Int = parameterNames.indexOfFirst { it == type.keyName }
        val id: Long = (values.getOrNull(index) as? Long) ?: 0

        if (!redisLockManager.lock(type, id)) {
            throw IllegalStateException("이미 작업 중입니다. 조회 후 다시 시도해주세요.")
        }

        return try {
            pjp.proceed()

        } finally {
            redisLockManager.unlock(type, id)
        }
    }
}