package kr.co.baseapi.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

abstract class PageParam {
    @field:Schema(description = "현재 페이지 번호", defaultValue = "0")
    @field:NotNull
    @field:PositiveOrZero
    var pageNumber: Int? = null
        private set(value) {
            field = value
            initPageable(value, pageSize)
        }

    @field:Schema(description = "페이지당 아이템 갯수", defaultValue = "20")
    @field:NotNull
    @field:Positive
    var pageSize: Int? = null
        private set(value) {
            field = value
            initPageable(pageNumber, value)
        }

    @field:Schema(description = "페이지 처리 객체", hidden = true)
    var pageable: Pageable? = null
        private set

    private fun initPageable(pageNumber: Int?, pageSize: Int?) {
        if (pageNumber != null && pageSize != null && pageSize > 0) {
            pageable = PageRequest.of(pageNumber, pageSize)
        }
    }
}

