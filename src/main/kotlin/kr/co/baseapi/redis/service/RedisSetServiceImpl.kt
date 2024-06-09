package kr.co.baseapi.redis.service

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisSetServiceImpl(
    private val stringRedisTemplate: StringRedisTemplate
) : RedisSetService {

    /**
     * Key 생성(object-type:id)
     */
    private fun makeDocumentIdKey(documentId: String): String = "document:tags:$documentId"

    override fun addTagToDocument(documentId: String, tag: String): Boolean {
        val key: String = makeDocumentIdKey(documentId)
        stringRedisTemplate.opsForSet().add(key, tag)

        return true
    }

    override fun removeTagFromDocument(documentId: String, tag: String): Boolean {
        val key: String = makeDocumentIdKey(documentId)
        stringRedisTemplate.opsForSet().remove(key, tag)

        return true
    }

    override fun getTagsForDocument(documentId: String): Set<String> {
        val key: String = makeDocumentIdKey(documentId)
        return stringRedisTemplate.opsForSet().members(key) ?: emptySet()
    }

    override fun isTagInDocument(documentId: String, tag: String): Boolean {
        val key: String = makeDocumentIdKey(documentId)
        return stringRedisTemplate.opsForSet().isMember(key, tag) ?: false
    }


    override fun addUniqueValue(key: String, value: String): Boolean {
        return stringRedisTemplate.opsForSet().add(key, value) == 1L
    }

    override fun getUniqueValues(key: String): Set<String> {
        return stringRedisTemplate.opsForSet().members(key) ?: emptySet()
    }

    override fun removeValue(key: String, value: String): Boolean {
        stringRedisTemplate.opsForSet().remove(key, value)

        return true
    }

    override fun containsValue(key: String, value: String): Boolean {
        return stringRedisTemplate.opsForSet().isMember(key, value) ?: false
    }
}