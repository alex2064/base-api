package kr.co.baseapi.redis.service

interface RedisSetService {

    /**
     * 태그 관리
     * 문서나 게시물에 여러 태그를 추가하고 관리
     */
    fun addTagToDocument(documentId: String, tag: String): Boolean

    fun removeTagFromDocument(documentId: String, tag: String): Boolean

    fun getTagsForDocument(documentId: String): Set<String>

    fun isTagInDocument(documentId: String, tag: String): Boolean


    /**
     * 친구목록 관리
     * 사용자의 친구 목록을 관리
     */


    /**
     * 중복 제거
     */
    fun addUniqueValue(key: String, value: String): Boolean

    fun getUniqueValues(key: String): Set<String>

    fun removeValue(key: String, value: String): Boolean

    fun containsValue(key: String, value: String): Boolean
}