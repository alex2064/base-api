package kr.co.baseapi.redis.service

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisListServiceImpl(
    private val stringRedisTemplate: StringRedisTemplate
) : RedisListService {

    override fun addTaskToQueue(queueName: String, task: String): Boolean {
        stringRedisTemplate.opsForList().rightPush(queueName, task)

        return true
    }

    override fun getTaskFromQueue(queueName: String): String? {
        return stringRedisTemplate.opsForList().leftPop(queueName)
    }


    /**
     * Key 생성(object-type:id)
     */
    private fun makePageLoginIdKey(loginId: String): String = "recent:pages:$loginId"

    override fun addPageVisit(loginId: String, pageUrl: String): Boolean {
        val key: String = makePageLoginIdKey(loginId)
        stringRedisTemplate.opsForList().leftPush(key, pageUrl)
        // 최근 방문페이지 기록 최대 10개
        stringRedisTemplate.opsForList().trim(key, 0, 9)

        return true
    }

    override fun getRecentPages(loginId: String): List<String> {
        val key: String = makePageLoginIdKey(loginId)

        return stringRedisTemplate.opsForList().range(key, 0, -1) ?: emptyList()
    }


    /**
     * Key 생성(object-type:id)
     */
    private fun makeChatRoomIdKey(chatRoomId: String): String = "chat:room:$chatRoomId"

    override fun addMsgToChatRoom(chatRoomId: String, message: String): Boolean {
        val key: String = makeChatRoomIdKey(chatRoomId)
        stringRedisTemplate.opsForList().rightPush(key, message)

        return true
    }

    override fun getMsgFromChatRoom(chatRoomId: String, count: Long): List<String> {
        val key: String = makeChatRoomIdKey(chatRoomId)

        return stringRedisTemplate.opsForList().range(key, -count, -1) ?: emptyList()
    }


    /**
     * Key 생성(object-type:id)
     */
    private fun makeOrderLoginIdIdKey(loginId: String): String = "order:$loginId"

    override fun addOrder(loginId: String, orderId: Long): Boolean {
        val key: String = makeOrderLoginIdIdKey(loginId)
        stringRedisTemplate.opsForList().rightPush(key, orderId.toString())

        return true
    }

    override fun getOrders(loginId: String): List<Long> {
        val key: String = makeOrderLoginIdIdKey(loginId)
        val result: List<String> = stringRedisTemplate.opsForList().range(key, 0, -1) ?: emptyList()

        return result.map { it.toLong() }
    }
}