package kr.co.baseapi.common.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.lang.Exception
import java.time.Duration
import java.time.LocalDateTime

private val log = KotlinLogging.logger {}

@Component
class RequestInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val beginTime: LocalDateTime = LocalDateTime.now()
        request.setAttribute("beginTime", beginTime)

        log.info {
            "[Http Request] | Method: ${request.method} | Path: ${request.requestURI}"
        }
        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        val beginTime: LocalDateTime = request.getAttribute("beginTime") as LocalDateTime
        val endTime: LocalDateTime = LocalDateTime.now()
        val duration: Duration = Duration.between(beginTime, endTime)
        log.info {
            "[Http Response] | Method: ${request.method} | Path: ${request.requestURI} | duration: ${duration.toMillis()} ms"
        }
    }
}