package kr.co.baseapi.redis.service

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class RedisZSetServiceImpl(
    private val stringRedisTemplate: StringRedisTemplate
) : RedisZSetService {

    /**
     * Key 생성(object-type:id)
     */
    private fun makeLeaderboardIdKey(leaderboardId: String): String = "leaderboard:$leaderboardId"

    override fun addScoreToLeaderboard(leaderboardId: String, userId: String, score: BigDecimal): Boolean {
        val key: String = makeLeaderboardIdKey(leaderboardId)
        stringRedisTemplate.opsForZSet().add(key, userId, score.toDouble())

        return true
    }

    override fun getTopUsers(leaderboardId: String, topN: Long): Set<String> {
        val key: String = makeLeaderboardIdKey(leaderboardId)

        // default. Score 기준 오름차순
        return stringRedisTemplate.opsForZSet().reverseRange(key, 0, topN - 1) ?: emptySet()
    }

    override fun getUserRank(leaderboardId: String, userId: String): Long? {
        val key: String = makeLeaderboardIdKey(leaderboardId)
        return stringRedisTemplate.opsForZSet().reverseRank(key, userId)
    }

    override fun getUserScore(leaderboardId: String, userId: String): BigDecimal? {
        val key: String = makeLeaderboardIdKey(leaderboardId)
        return stringRedisTemplate.opsForZSet().score(key, userId)?.toBigDecimal()
    }
}