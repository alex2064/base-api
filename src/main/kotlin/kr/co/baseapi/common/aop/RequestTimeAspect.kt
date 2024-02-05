package kr.co.baseapi.common.aop

import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.Duration
import java.time.LocalDateTime

private val log = KotlinLogging.logger {}

@Aspect
@Component
class RequestTimeAspect {

    @Pointcut("execution(* kr.co.baseapi.controller.*Controller.*(..))")
    fun controllerPointcut() {
    }

    @Around("controllerPointcut()")
    @Throws(Throwable::class)
    fun logRequestTime(joinPoint: ProceedingJoinPoint): Any? {

        val attributes: ServletRequestAttributes =
            RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes
        val httpRequest: HttpServletRequest = attributes.request

        val beginTime: LocalDateTime = LocalDateTime.now()
        httpRequest.setAttribute("beginTime", beginTime)

        try {
            return joinPoint.proceed()
        } finally {
            val endTime: LocalDateTime = LocalDateTime.now()
            val duration: Duration = Duration.between(beginTime, endTime)
            log.info {
                """
                [Http Request] - ${duration.toMillis()} ms 
                Method: ${httpRequest.method}
                Path: ${httpRequest.requestURI}
                """.trimIndent()
            }
        }
    }
}