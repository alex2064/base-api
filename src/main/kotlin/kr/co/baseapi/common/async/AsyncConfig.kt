package kr.co.baseapi.common.async

import kr.co.baseapi.common.handler.AsyncExceptionHandler
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor


@Configuration
@EnableAsync
class AsyncConfig : AsyncConfigurer {

    override fun getAsyncExecutor(): Executor? {
        val executor: ThreadPoolTaskExecutor =
            ThreadPoolTaskExecutor().apply {
                corePoolSize = 2
                maxPoolSize = 10
                queueCapacity = 100
                setThreadNamePrefix("async-default-")
                setRejectedExecutionHandler(ThreadPoolExecutor.DiscardPolicy())
                initialize()
            }

        return executor
    }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler? {
        return AsyncExceptionHandler()
    }

    @Bean("customAsyncExecutor")
    fun customAsyncExecutor(): Executor? {
        val executor: ThreadPoolTaskExecutor =
            ThreadPoolTaskExecutor().apply {
                corePoolSize = 2
                maxPoolSize = 2
                queueCapacity = 10
                setThreadNamePrefix("async-custom-")
                setRejectedExecutionHandler(ThreadPoolExecutor.DiscardPolicy())
                initialize()
            }

        return executor
    }
}