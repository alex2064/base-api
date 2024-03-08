package kr.co.baseapi.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.co.baseapi.common.swagger.ApiForExam
import kr.co.baseapi.dto.ExamPageParam
import kr.co.baseapi.dto.ExamParam
import kr.co.baseapi.dto.ExamVaildParam
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.*

@Tag(name = "예시 controller")
@RequestMapping("/exam")
@RestController
class ExamController {

    @ApiForExam
    @Operation(summary = "예시 validation(Get)", description = "예시 validation(Get) description")
    @GetMapping("/valid")
    fun examGetValid(@ParameterObject @Valid param: ExamVaildParam): ExamVaildParam {
        return param
    }

    @ApiForExam
    @Operation(summary = "예시 validation(Post)", description = "예시 validation(Post) description")
    @PostMapping("/valid")
    fun examPostValid(@RequestBody @Valid param: ExamVaildParam): ExamVaildParam {
        return param
    }

    @ApiForExam
    @Operation(summary = "예시 페이징 호출", description = "예시 페이징 호출 description")
    @GetMapping("/page")
    fun examPage(@ParameterObject @Valid param: ExamPageParam): ExamPageParam {
        return param
    }

}