package kr.co.baseapi.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.co.baseapi.common.swagger.ApiForExam
import kr.co.baseapi.dto.ExamPage
import kr.co.baseapi.dto.ExamParam
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.*

@Tag(name = "예시 controller")
@RequestMapping("/exam")
@RestController
class ExamController {

    @ApiForExam
    @Operation(summary = "예시 End Point(Get)", description = "예시 End Point(Get) description")
    @GetMapping("/validation")
    fun examGetValid(@ParameterObject @Valid param: ExamParam): ExamParam {
        return param
    }

    @ApiForExam
    @Operation(summary = "예시 End Point(Post)", description = "예시 End Point(Post) description")
    @PostMapping("/validation")
    fun examPostValid(@RequestBody @Valid param: ExamParam): ExamParam {
        return param
    }

    @ApiForExam
    @Operation(summary = "예시 페이징 호출", description = "예시 페이징 호출")
    @GetMapping("/page")
    fun examPage(@ParameterObject @Valid param: ExamPage): ExamPage {
        return param
    }

}