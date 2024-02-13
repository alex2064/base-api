package kr.co.baseapi.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.co.baseapi.common.swagger.ApiForExam
import kr.co.baseapi.dto.ExamParam
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "예시 controller")
@RequestMapping("/exam")
@RestController
class ExamController {

    @ApiForExam
    @Operation(summary = "예시 End Point", description = "예시 End Point description")
    @PostMapping("/validation")
    fun examValidation(@RequestBody @Valid param: ExamParam): ExamParam {
        return param
    }

}