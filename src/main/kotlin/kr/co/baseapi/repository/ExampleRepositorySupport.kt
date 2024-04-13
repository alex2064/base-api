package kr.co.baseapi.repository

import kr.co.baseapi.dto.ExamPageParam
import kr.co.baseapi.dto.ExamResult
import kr.co.baseapi.entity.Example
import org.springframework.data.domain.PageImpl
import java.util.*


interface ExampleRepositorySupport {

    fun findById(id: Long): Optional<Example>

    fun findByNameList(param: ExamPageParam): List<ExamResult>

    fun findByNamePage(param: ExamPageParam): PageImpl<ExamResult>
}