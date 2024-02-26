package kr.co.baseapi.common.handler

import jakarta.servlet.http.HttpServletRequest
import kr.co.baseapi.common.handler.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class FailureHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): BaseResponse<ProblemDetail> {

        val errors: MutableMap<String, String> = mutableMapOf()
        for (error in e.bindingResult.allErrors) {
            val fieldName = (error as FieldError).field
            errors[fieldName] = error.getDefaultMessage() ?: "Not Exception Message"
        }

        val problemDetail: ProblemDetail = e.body
        problemDetail.setProperty("fieldErrors", errors)

        val beginTime: LocalDateTime = request.getAttribute("beginTime") as LocalDateTime

        return BaseResponse.failureOf(
            problemDetail = problemDetail,
            beginTime = beginTime,
            method = request.method,
            path = request.requestURI
        )
    }


    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleDefaultException(
        e: Exception,
        request: HttpServletRequest
    ): BaseResponse<ProblemDetail> {
        val problemDetail: ProblemDetail =
            ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, (e.message ?: "Not Exception Message"))

        val beginTime: LocalDateTime = request.getAttribute("beginTime") as LocalDateTime

        return BaseResponse.failureOf(
            problemDetail = problemDetail,
            beginTime = beginTime,
            method = request.method,
            path = request.requestURI
        )
    }
}