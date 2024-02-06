package kr.co.baseapi.common.handler

import jakarta.servlet.http.HttpServletRequest
import kr.co.baseapi.dto.BaseResponse
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import java.time.LocalDateTime

@RestControllerAdvice(basePackages = ["kr.co.baseapi.controller"])
class SuccessHandler : ResponseBodyAdvice<Any> {

    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        val excludeMethods: List<String> = listOf("methodName")
        val methodName: String = returnType.method?.name ?: ""

        return !((methodName in excludeMethods) || (BaseResponse::class.java.isAssignableFrom(returnType.parameterType)))
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        val attributes: ServletRequestAttributes =
            RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes
        val httpRequest: HttpServletRequest = attributes.request
        val beginTime: LocalDateTime? = httpRequest.getAttribute("beginTime") as? LocalDateTime

        return BaseResponse.successOf(
            data = body,
            beginTime = beginTime,
            method = request.method.name(),
            path = request.uri.path
        )
    }
}