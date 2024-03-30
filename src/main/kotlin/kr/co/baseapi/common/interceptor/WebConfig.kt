package kr.co.baseapi.common.interceptor

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val requestInterceptor: RequestInterceptor,
    private val duplicateRequestInterceptor: DuplicateRequestInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(requestInterceptor)
        registry.addInterceptor(duplicateRequestInterceptor)
    }
}