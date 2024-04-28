package kr.co.baseapi.common.handler.dto

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.ProblemDetail
import java.time.LocalDateTime

data class BaseResponse<T>(
    val isSuccess: Boolean = false,
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val beginTime: LocalDateTime,
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val endTime: LocalDateTime = LocalDateTime.now(),
    val method: String,
    val path: String,
    val data: T
) {
    companion object {
        fun <T> successOf(
            data: T,
            beginTime: LocalDateTime,
            method: String,
            path: String
        ): BaseResponse<T> = BaseResponse(
            isSuccess = true,
            beginTime = beginTime,
            method = method,
            path = path,
            data = data
        )

        fun failureOf(
            problemDetail: ProblemDetail,
            beginTime: LocalDateTime,
            method: String,
            path: String
        ): BaseResponse<ProblemDetail> = BaseResponse(
            isSuccess = false,
            beginTime = beginTime,
            method = method,
            path = path,
            data = problemDetail
        )
    }
}