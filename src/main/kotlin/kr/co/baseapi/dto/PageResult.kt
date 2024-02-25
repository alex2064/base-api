package kr.co.baseapi.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Page

class PageResult<T> private constructor(
    @field:Schema(description = "현재 페이지 번호")
    val pageNumber: Int,

    @field:Schema(description = "페이지당 아이템 수")
    val pageSize: Int,

    @field:Schema(description = "전체 페이지 수")
    val totalPages: Int,

    @field:Schema(description = "전체 아이템 수")
    val totalCount: Long,

    @field:Schema(description = "현재 페이지의 데이터 리스트")
    val list: List<T>
) {
    companion object {
        fun <T> of(page: Page<T>): PageResult<T> = PageResult(
            pageNumber = page.number,
            pageSize = page.size,
            totalPages = page.totalPages,
            totalCount = page.totalElements,
            list = page.content
        )
    }
}