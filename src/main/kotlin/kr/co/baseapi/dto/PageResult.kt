package kr.co.baseapi.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Page

class PageResult<T, S> private constructor(
    @field:Schema(description = "현재 페이지 번호")
    val pageNumber: Int,

    @field:Schema(description = "페이지당 아이템 수")
    val pageSize: Int,

    @field:Schema(description = "전체 페이지 수")
    val totalPages: Int,

    @field:Schema(description = "전체 아이템 수")
    val totalCount: Long,

    @field:Schema(description = "현재 페이지의 데이터 리스트")
    val list: List<T>,

    @field:Schema(description = "요약")
    val summary: S

) {
    companion object {
        fun <T> pageOf(page: Page<T>): PageResult<T, Nothing?> = PageResult(
            pageNumber = page.number,
            pageSize = page.size,
            totalPages = page.totalPages,
            totalCount = page.totalElements,
            list = page.content,
            summary = null
        )

        fun <T, U> pageOf(page: Page<U>, converter: (U) -> T): PageResult<T, Nothing?> = PageResult(
            pageNumber = page.number,
            pageSize = page.size,
            totalPages = page.totalPages,
            totalCount = page.totalElements,
            list = page.content.map(converter),
            summary = null
        )

        fun <T, S> pageWithSummaryOf(page: Page<T>, summary: S): PageResult<T, S> = PageResult(
            pageNumber = page.number,
            pageSize = page.size,
            totalPages = page.totalPages,
            totalCount = page.totalElements,
            list = page.content,
            summary = summary
        )

        fun <T, S, U> pageWithSummaryOf(page: Page<U>, summary: S, converter: (U) -> T): PageResult<T, S> = PageResult(
            pageNumber = page.number,
            pageSize = page.size,
            totalPages = page.totalPages,
            totalCount = page.totalElements,
            list = page.content.map(converter),
            summary = summary
        )

        fun <T> listOf(list: List<T>): PageResult<T, Nothing?> = PageResult(
            pageNumber = 0,
            pageSize = 0,
            totalPages = 0,
            totalCount = list.size.toLong(),
            list = list,
            summary = null
        )

        fun <T, U> listOf(list: List<U>, converter: (U) -> T): PageResult<T, Nothing?> = PageResult(
            pageNumber = 0,
            pageSize = 0,
            totalPages = 0,
            totalCount = list.size.toLong(),
            list = list.map(converter),
            summary = null
        )

        fun <T, S> listWithSummaryOf(list: List<T>, summary: S): PageResult<T, S> = PageResult(
            pageNumber = 0,
            pageSize = 0,
            totalPages = 0,
            totalCount = list.size.toLong(),
            list = list,
            summary = summary
        )

        fun <T, S, U> listWithSummaryOf(list: List<U>, summary: S, converter: (U) -> T): PageResult<T, S> = PageResult(
            pageNumber = 0,
            pageSize = 0,
            totalPages = 0,
            totalCount = list.size.toLong(),
            list = list.map(converter),
            summary = summary
        )
    }
}