package kr.co.baseapi.redis.service

interface RedisStringService {

    /**
     * 캐싱
     * 데이터베이스에서 자주 조회되는 데이터를 Redis에 캐시하여 성능을 향상
     */
    fun findUserInfo(userId: Long): String


    /**
     * 세션 관리
     * 애플리케이션의 세션 데이터를 Redis에 저장하여 확장성을 높임. 여러 서버에서 세션을 공유
     */
    fun getSession(sessionId: String): String?

    fun saveSession(sessionId: String, sessionData: String): Boolean

    fun deleteSession(sessionId: String): Boolean


    /**
     * config 데이터 저장
     * 애플리케이션의 구성 데이터를 Redis에 저장하고 필요할 때마다 조회
     */
    fun getConfig(configKey: String): String?

    fun saveConfig(configKey: String, configValue: String): Boolean


    /**
     * 카운터
     * Redis 문자열을 이용하여 간단한 카운터를 구현
     */
    fun getSequence(keyword: String): Long


    /**
     * 상태 플래그 저장
     * 특정 이벤트의 상태를 저장
     */
    fun onApply(applyKey: String): Boolean

    fun offApply(applyKey: String): Boolean

    fun isApply(applyKey: String): Boolean
}