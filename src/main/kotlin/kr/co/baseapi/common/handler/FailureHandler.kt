package kr.co.baseapi.common.handler

import jakarta.servlet.http.HttpServletRequest
import kr.co.baseapi.dto.BaseResponse
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
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): BaseResponse<ProblemDetail> {

        val errors: MutableMap<String, String> = mutableMapOf()
        for (error in ex.bindingResult.allErrors) {
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
    fun handleDefaultException(
        ex: Exception,
        request: HttpServletRequest
    ): BaseResponse<ProblemDetail> {
        val problemDetail: ProblemDetail =
            ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, (ex.message ?: "Not Exception Message"))

        val beginTime: LocalDateTime? = request.getAttribute("beginTime") as? LocalDateTime

        return BaseResponse.failureOf(
            problemDetail = problemDetail,
            beginTime = beginTime,
            method = request.method,
            path = request.requestURI
        )
    }
}