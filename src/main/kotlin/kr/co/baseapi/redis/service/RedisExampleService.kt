package kr.co.baseapi.redis.service

import kr.co.baseapi.dto.ExamParam
import kr.co.baseapi.redis.entity.RedisExample

interface RedisExampleService {

    fun findExample(id: Long): RedisExample

    fun saveExample(param: ExamParam): Boolean

    fun saveExampleInfo(id: Long, param: ExamParam): Boolean

    fun deleteExample(id: Long): Boolean
}