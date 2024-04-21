package kr.co.baseapi.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.co.baseapi.common.swagger.ApiForExam
import kr.co.baseapi.dto.*
import kr.co.baseapi.service.ExampleService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.*

/**
 * Controller 생성
 * 1. 요청 분류
 *      1) Get : 단순 조회(@ParameterObject @Valid 필수)
 *      2) Post : 개념적으로 해당하는 도메인에 데이터를 새로 생성하는 경우(@RequestBody @Valid 필수)
 *      3) Put : 개념적으로 해당하는 도메인에 ID가 있는 상태에서 수정하는 경우(@RequestBody @Valid 필수, @PathVariable 는 고민해봐야 할 듯)
 *      4) Delete : 개념적으로 해당하는 도메인에 ID가 있는 상태에서 삭제하는 경우(@RequestBody @Valid 필수, @PathVariable 는 고민해봐야 할 듯)
 * 2. URL 은 '/'로 시작, 명사(소문자)로 종료
 * 3. 파라미터 명 사용
 *      1) {param} : Request 의 DTO
 *      2) {result} : Response 의 DTO
 */
@Tag(name = "예시 controller")
@RequestMapping("/exam")
@RestController
class ExampleController(
    private val exampleService: ExampleService
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
    @Operation(summary = "Example 조회", description = "Example 조회 설명")
    @GetMapping("/example")
    fun findExample(@ParameterObject @Valid param: ExamIdParam): ExamResult {
        return exampleService.findExample(param.id!!)
    }

    @ApiForExam
    @Operation(summary = "Example 추가", description = "Example 추가 설명")
    @PostMapping("/example")
    fun saveExample(@RequestBody @Valid param: ExamParam): Boolean {
        return exampleService.saveExample(param)
    }

    @ApiForExam
    @Operation(summary = "Example 수정", description = "Example 수정 설명")
    @PutMapping("/example/{id}")
    fun saveExampleInfo(@PathVariable("id") id: Long, @RequestBody @Valid param: ExamParam): Boolean {
        require(id > 0) { "id는 0보다 커야합니다." }
        return exampleService.saveExampleInfo(id, param)
    }

    @ApiForExam
    @Operation(summary = "Example 삭제", description = "Example 삭제 설명")
    @DeleteMapping("/example/{id}")
    fun deleteExample(@PathVariable("id") id: Long): Boolean {
        require(id > 0) { "id는 0보다 커야합니다." }
        return exampleService.deleteExample(id)
    }

    @ApiForExam
    @Operation(summary = "isAuth update", description = "isAuth update 설명")
    @PutMapping("/example/auth")
    fun saveIsAuth(@RequestBody @Valid param: ExamIsAuthParam): Boolean {
        return exampleService.saveIsAuth(param)
    }

    @ApiForExam
    @Operation(summary = "select Lock", description = "select Lock 설명")
    @GetMapping("/example/lock")
    fun findExampleForLock(@ParameterObject @Valid param: ExamIdParam): ExamResult {
        return exampleService.findExampleForLock(param.id!!)
    }

    @ApiForExam
    @Operation(summary = "select querydsl", description = "select querydsl 설명")
    @GetMapping("/example/querydsl")
    fun findExampleDsl(@ParameterObject @Valid param: ExamIdParam): ExamResult {
        return exampleService.findExampleDsl(param.id!!)
    }

    @ApiForExam
    @Operation(summary = "조회 리스트 처리", description = "조회 리스트 처리 설명")
    @GetMapping("/example/list")
    fun findExampleDslList(@ParameterObject @Valid param: ExamPageParam): PageResult<ExamResult> {
        return exampleService.findExampleDslList(param)
    }

    @ApiForExam
    @Operation(summary = "조회 페이징 처리", description = "조회 페이징 처리 설명")
    @GetMapping("/example/page")
    fun findExampleDslPage(@ParameterObject @Valid param: ExamPageParam): PageResult<ExamResult> {
        return exampleService.findExampleDslPage(param)
    }
}