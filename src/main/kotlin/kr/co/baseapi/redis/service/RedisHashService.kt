package kr.co.baseapi.redis.service

interface RedisHashService {

    /**
     * 사용자 프로필 저장
     * 사용자의 다양한 속성을 해시로 저장하여 관리
     */
    fun findUserProfile(userId: Long): Map<String, Any>

    fun saveUserProfile(userId: Long, profile: Map<String, Any>): Boolean

    fun saveUserProfileField(userId: Long, field: String, value: Any): Boolean

    fun deleteUserProfile(userId: Long): Boolean

    fun deleteUserProfileField(userId: Long, field: String): Boolean


    /**
     * 세션 데이터 저장
     * 사용자 세션 데이터를 해시로 저장하여 다양한 세션 속성을 관리
     */


    /**
     * 통계 데이터 저장
     * 애플리케이션에서 수집한 다양한 통계 데이터를 해시로 저장하여 관리
     */
}