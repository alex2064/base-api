package kr.co.baseapi.common.redis.annotation

import kr.co.baseapi.common.redis.enums.LockType

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisLock(
    val type: LockType
)
