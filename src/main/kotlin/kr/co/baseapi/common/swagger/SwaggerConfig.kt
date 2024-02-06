package kr.co.baseapi.common.swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import kr.co.baseapi.common.swagger.annotation.ApiForExam
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(title = "BASE API", description = "Base Api Description", version = "0.0.1")
)
class SwaggerConfig {

    @Bean
    fun examGroup(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("예시 API")
            .addOpenApiMethodFilter { it.isAnnotationPresent(ApiForExam::class.java) }
            .build()

}