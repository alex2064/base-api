package kr.co.baseapi.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.co.baseapi.common.swagger.ApiForExam
import kr.co.baseapi.dto.*
import kr.co.baseapi.service.ExamService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
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
class ExamController(
    private val examService: ExamService
) {

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
    @Operation(summary = "Example 조회", description = "Example 조회")
    @GetMapping("/example")
    fun findExample(@ParameterObject @Valid param: ExamIdParam): ExamResult {
        return examService.findExample(param.id!!)
    }

    @ApiForExam
    @Operation(summary = "Example 추가", description = "Example 추가")
    @PostMapping("/example")
    fun saveExample(@RequestBody @Valid param: ExamParam): Boolean {
        return examService.saveExample(param)
    }

    @ApiForExam
    @Operation(summary = "Example 수정", description = "Example 수정")
    @PutMapping("/example/{id}")
    fun saveExampleInfo(@PathVariable("id") id: Long, @RequestBody @Valid param: ExamParam): Boolean {
        require(id > 0) { "id는 0보다 커야합니다." }
        return examService.saveExampleInfo(id, param)
    }

    @ApiForExam
    @Operation(summary = "Example 삭제", description = "Example 삭제")
    @DeleteMapping("/example/{id}")
    fun deleteExample(@PathVariable("id") id: Long): Boolean {
        require(id > 0) { "id는 0보다 커야합니다." }
        return examService.deleteExample(id)
    }

    @ApiForExam
    @Operation(summary = "isAuth update", description = "isAuth update")
    @PutMapping("/example/auth")
    fun saveIsAuth(@RequestBody @Valid param: ExamIsAuthParam): Boolean {
        return examService.saveIsAuth(param)
    }

    @ApiForExam
    @Operation(summary = "select Lock", description = "select Lock")
    @GetMapping("/example/lock")
    fun findExampleForLock(@ParameterObject @Valid param: ExamIdParam): ExamResult {
        return examService.findExampleForLock(param.id!!)
    }

    @ApiForExam
    @Operation(summary = "select querydsl", description = "select querydsl")
    @GetMapping("/example/querydsl")
    fun findExampleDsl(@ParameterObject @Valid param: ExamIdParam): ExamResult {
        return examService.findExampleDsl(param.id!!)
    }

    @ApiForExam
    @Operation(summary = "조회 페이징 처리", description = "조회 페이징 처리")
    @GetMapping("/example/page")
    fun findExampleDslPage(@ParameterObject @Valid param: ExamPageParam): PageResult<ExamResult> {
        return examService.findExampleDslPage(param)
    }
}