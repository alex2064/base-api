package kr.co.baseapi.redis.entity

import kr.co.baseapi.enums.GenderType
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.math.BigDecimal
import java.time.LocalDate

@RedisHash("RedisExample")
data class RedisExample(
    @Id
    val id: Long,
    val name: String?,
    val age: Int?,
    val amount: Long?,
    val height: BigDecimal?,
    val gender: GenderType?,
    val isAuth: Boolean?,
    val baseDate: LocalDate?
)
