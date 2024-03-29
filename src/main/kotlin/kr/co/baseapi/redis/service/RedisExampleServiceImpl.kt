package kr.co.baseapi.redis.service

import kr.co.baseapi.dto.ExamParam
import kr.co.baseapi.redis.entity.RedisExample
import kr.co.baseapi.redis.repository.RedisExampleRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RedisExampleServiceImpl(
    private val redisExampleRepository: RedisExampleRepository
) : RedisExampleService {

    override fun findExample(id: Long): RedisExample {
        val redisExample: RedisExample = redisExampleRepository.findById(id).orElseThrow()
        return redisExample
    }

    override fun saveExample(param: ExamParam): Boolean {
        val id: Long = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE
        val redisExample: RedisExample = RedisExample(
            id = id,
            name = param.name,
            age = param.age,
            amount = param.amount,
            height = param.height,
            gender = param.gender,
            isAuth = param.isAuth,
            baseDate = param.baseDate
        )

        redisExampleRepository.save(redisExample)
        return true
    }

    override fun saveExampleInfo(id: Long, param: ExamParam): Boolean {
        val redisExample: RedisExample = RedisExample(
            id = id,
            name = param.name,
            age = param.age,
            amount = param.amount,
            height = param.height,
            gender = param.gender,
            isAuth = param.isAuth,
            baseDate = param.baseDate
        )

        redisExampleRepository.save(redisExample)
        return true
    }

    override fun deleteExample(id: Long): Boolean {
        val redisExample: RedisExample = redisExampleRepository.findById(id).orElseThrow()
        redisExampleRepository.delete(redisExample)

        return true
    }
}