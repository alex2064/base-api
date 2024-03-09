package kr.co.baseapi.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.co.baseapi.common.swagger.ApiForExam
import kr.co.baseapi.dto.ExamPageParam
import kr.co.baseapi.dto.ExamVaildParam
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.*

/**
 * Controller 생성
 * 1. Get 요청은 @ParameterObject @Valid 필수
 * 2. Post 요청은 @RequestBody @Valid 필수
 * 3. URL은 '/'로 시작, 명사로 종료
 * 4. Request의 DTO는 {param}, Response의 DTO는 {result} 로 변수명 사용
 */
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