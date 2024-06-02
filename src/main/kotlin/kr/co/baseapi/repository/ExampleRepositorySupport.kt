package kr.co.baseapi.repository

import kr.co.baseapi.dto.ExamDto
import kr.co.baseapi.dto.ExamPageParam
import kr.co.baseapi.entity.Example
import org.springframework.data.domain.PageImpl
import java.util.*


interface ExampleRepositorySupport {

    /**
     * QueryDsl 로 entity 조회
     */
    fun findById(id: Long): Optional<Example>

    /**
     * QueryDsl 로 List 조회
     */
    fun findByNameList(param: ExamPageParam): List<ExamDto>

    /**
     * QueryDsl 로 page 조회
     */
    fun findByNamePage(param: ExamPageParam): PageImpl<ExamDto>
}