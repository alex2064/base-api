package kr.co.baseapi.common.handler

import jakarta.servlet.http.HttpServletRequest
import kr.co.baseapi.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
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
    protected fun methodArgumentNotValidException(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): BaseResponse<ProblemDetail> {
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage ?: "Not Exception Message"
        }
        val problemDetail: ProblemDetail = ex.body
        problemDetail.setProperty("fieldErrors", errors)

        val beginTime: LocalDateTime? = request.getAttribute("beginTime") as? LocalDateTime

        return BaseResponse.failureOf(
            problemDetail = problemDetail,
            beginTime = beginTime,
            method = request.method,
            path = request.requestURI
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun defaultException(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): BaseResponse<ProblemDetail> {
        val errors = mapOf("미처리 에러" to (ex.message ?: "Not Exception Message"))
        val problemDetail: ProblemDetail = ex.body
        problemDetail.setProperty("fieldErrors", errors)

        val beginTime: LocalDateTime? = request.getAttribute("beginTime") as? LocalDateTime

        return BaseResponse.failureOf(
            problemDetail = problemDetail,
            beginTime = beginTime,
            method = request.method,
            path = request.requestURI
        )
    }
}