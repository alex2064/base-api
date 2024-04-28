package kr.co.baseapi.redis.service

import kr.co.baseapi.dto.ExamParam
import kr.co.baseapi.redis.entity.RedisExample

interface RedisExampleService {

    /**
     * Example 조회(Redis)
     */
    fun findExample(id: Long): RedisExample

    /**
     * Example 추가(Redis)
     */
    fun saveExample(param: ExamParam): Boolean

    /**
     * Example 수정(Redis)
     */
    fun saveExampleInfo(id: Long, param: ExamParam): Boolean

    /**
     * Example 삭제(Redis)
     */
    fun deleteExample(id: Long): Boolean
}