package kr.co.baseapi.redis.service

import java.math.BigDecimal

interface RedisZSetService {

    /**
     * 리더보드
     * 게임이나 애플리케이션에서 사용자들의 점수를 기준으로 순위를 매기는 리더보드
     */
    fun addScoreToLeaderboard(leaderboardId: String, userId: String, score: BigDecimal): Boolean

    fun getTopUsers(leaderboardId: String, topN: Long): Set<String>

    fun getUserRank(leaderboardId: String, userId: String): Long?

    fun getUserScore(leaderboardId: String, userId: String): BigDecimal?


    /**
     * 타임라인
     * 사용자들의 게시물을 시간 순서대로 저장하고 관리
     */

    /**
     * 우선순위 큐
     * 작업을 우선순위에 따라 처리할 수 있도록 저장하고 관리
     */

}