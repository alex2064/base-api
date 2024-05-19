package kr.co.baseapi.common.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.lang.Exception
import java.time.Duration

private val log = KotlinLogging.logger {}

@Component
class DuplicateRequestInterceptor(
    private val stringRedisTemplate: StringRedisTemplate
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestKey: String = makeRequestKey(request)
        val isSet: Boolean = stringRedisTemplate
            .opsForValue()
            .setIfAbsent(requestKey, "ING", Duration.ofMinutes(1L))
            ?: false

        // 중복요청
        if (!isSet) {
            response.status = HttpStatus.TOO_MANY_REQUESTS.value()
            log.error {
                "[Duplicate Request] | Method: ${request.method} | Path: ${request.requestURI}"
            }
            return false
        }

        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        val requestKey: String = makeRequestKey(request)
        stringRedisTemplate.delete(requestKey)
    }

    private fun makeRequestKey(request: HttpServletRequest): String {
        val remoteAddr: String = request.remoteAddr
        val method: String = request.method
        val requestURI: String = request.requestURI

        return "request:$remoteAddr-$method-$requestURI"
    }
}