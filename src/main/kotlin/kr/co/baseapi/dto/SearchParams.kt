package kr.co.baseapi.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class SearchBasicParam(
    @field:Schema(description = "검색타입")
    @field:NotBlank
    val searchType: String,

    @field:Schema(description = "검색어")
    @field:NotBlank
    val searchText: String
)

data class SearchPeriodPageParam(
    @field:Schema(description = "조회 시작일자")
    @field:NotNull
    val frDate: LocalDate,

    @field:Schema(description = "조회 종료일자")
    @field:NotNull
    val toDate: LocalDate
) : PageParam()