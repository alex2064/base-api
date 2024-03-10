package kr.co.baseapi.service

import kr.co.baseapi.dto.*
import kr.co.baseapi.entity.Example

interface ExamService {

    fun findExample(id: Long): ExamResult

    fun saveExample(param: ExamParam): Boolean

    fun saveExampleInfo(param: ExamParam): Boolean

    fun deleteExample(id: Long): Boolean

    fun saveIsAuth(param: ExamIsAuthParam): Boolean

    fun findExampleForLock(id: Long): ExamResult

    fun findExampleDsl(id: Long): ExamResult

    fun findExampleDslPage(param: ExamPageParam): PageResult<ExamResult>
}