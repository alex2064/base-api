package kr.co.baseapi.common.interceptor

import kr.co.baseapi.common.converter.StringToEnumTypeConverterFactory
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val requestInterceptor: RequestInterceptor,
//    private val duplicateRequestInterceptor: DuplicateRequestInterceptor,
    private val stringToEnumTypeConverterFactory: StringToEnumTypeConverterFactory
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(requestInterceptor)
//        registry.addInterceptor(duplicateRequestInterceptor)
    }

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverterFactory(stringToEnumTypeConverterFactory)
    }
}