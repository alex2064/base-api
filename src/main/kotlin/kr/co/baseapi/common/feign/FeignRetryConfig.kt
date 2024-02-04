package kr.co.baseapi.common.feign

import feign.Request
import feign.RequestInterceptor
import feign.RequestTemplate
import feign.Retryer
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean

class FeignRetryConfig : FeignLoggerConfig() {

    @Bean
    fun retryer(): Retryer = Retryer.Default(2_000L, 3_000L, 3)

    @Bean
    fun errorDecoder(): ErrorDecoder = RetryErrorDecoder()

    @Bean
    fun requestInterceptor(): RequestInterceptor =
        RequestInterceptor { requestTemplate ->
            if (requestTemplate.body().isEmpty() && !requestTemplate.isGetOrDelete()) {
                requestTemplate.body("{}")
            }
        }

    private fun RequestTemplate.isGetOrDelete(): Boolean =
        when (this.method()) {
            in setOf(Request.HttpMethod.GET.name, Request.HttpMethod.DELETE.name) -> true
            else -> false
        }
}