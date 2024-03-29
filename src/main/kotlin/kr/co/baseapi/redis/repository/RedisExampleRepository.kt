package kr.co.baseapi.redis.repository

import kr.co.baseapi.redis.entity.RedisExample
import org.springframework.data.repository.CrudRepository

interface RedisExampleRepository : CrudRepository<RedisExample, Long>