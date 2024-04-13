package kr.co.baseapi.service

import kr.co.baseapi.dto.*

interface ExampleService {

    fun findExample(id: Long): ExamResult

    fun saveExample(param: ExamParam): Boolean

    fun saveExampleInfo(id: Long, param: ExamParam): Boolean

    fun deleteExample(id: Long): Boolean

    fun saveIsAuth(param: ExamIsAuthParam): Boolean

    fun findExampleForLock(id: Long): ExamResult

    fun findExampleDsl(id: Long): ExamResult

    fun findExampleDslList(param: ExamPageParam): List<ExamResult>

    fun findExampleDslPage(param: ExamPageParam): PageResult<ExamResult>

    fun findKey(): String

    fun findExampleCache(id: Long): ExamResult

    fun findExampleCachePut(id: Long): ExamResult

    fun deleteExampleCache(id: Long): Boolean
}