package kr.co.baseapi.common.handler

import mu.KotlinLogging
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import java.lang.reflect.Method

private val log = KotlinLogging.logger {}

class AsyncExceptionHandler : AsyncUncaughtExceptionHandler {

    override fun handleUncaughtException(e: Throwable, method: Method, vararg params: Any?) {
        val paramsStr: String = params.joinToString(separator = "/") { it.toString() }
        log.error {
            """
            
            Async error caught: ${e.message} 
            Method: ${method.declaringClass.name}.${method.name}
            params: $paramsStr
            """.trimIndent()
        }
    }
}