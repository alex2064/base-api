package kr.co.baseapi.service

import kr.co.baseapi.dto.*

interface ExampleService {

    /**
     * Example 조회
     */
    fun findExample(id: Long): ExamResult

    /**
     * Example 추가
     */
    fun saveExample(param: ExamParam): Boolean

    /**
     * Example 수정
     */
    fun saveExampleInfo(id: Long, param: ExamParam): Boolean

    /**
     * Example 삭제
     */
    fun deleteExample(id: Long): Boolean

    /**
     * 여러 id 값을 한번에 업데이트
     */
    fun saveIsAuth(param: ExamIsAuthParam): Boolean

    /**
     * select 하면서 데이터 락 잡기(select for update)
     */
    fun findExampleForLock(id: Long): ExamResult

    /**
     * QueryDsl 로 entity 조회
     */
    fun findExampleDsl(id: Long): ExamResult

    /**
     * QueryDsl 로 List 조회
     */
    fun findExampleDslList(param: ExamPageParam): PageResult<ExamResult>

    /**
     * QueryDsl 로 page 조회
     */
    fun findExampleDslPage(param: ExamPageParam): PageResult<ExamResult>

    /**
     * yml 에 작성된 키 프로퍼티 사용
     */
    fun findKey(): String

    /**
     * 캐시 조회
     */
    fun findExampleCache(id: Long): ExamResult

    /**
     * 캐시 새로 reload
     */
    fun findExampleCachePut(id: Long): ExamResult

    /**
     * 캐시 삭제
     */
    fun deleteExampleCache(id: Long): Boolean
}