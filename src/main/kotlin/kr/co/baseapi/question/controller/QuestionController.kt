package kr.co.baseapi.question.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.baseapi.common.swagger.ApiForQuest
import kr.co.baseapi.question.service.QuestionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "질문 controller")
@RequestMapping("/question")
@RestController
class QuestionController(
    private val questionService: QuestionService
) {

    @ApiForQuest
    @Operation(summary = "1번 질문", description = "트랜잭션과 영속성 컨텍스트")
    @GetMapping("/q1")
    fun question1(): Boolean = questionService.question1()

    @ApiForQuest
    @Operation(summary = "2번 질문", description = "트랜잭션과 영속성 컨텍스트")
    @GetMapping("/q2")
    fun question2(): Boolean = questionService.question2()

    @ApiForQuest
    @Operation(summary = "3번 질문", description = "트랜잭션과 영속성 컨텍스트")
    @GetMapping("/q3")
    fun question3(): Boolean = questionService.question3()

    @ApiForQuest
    @Operation(summary = "4번 질문", description = "트랜잭션과 영속성 컨텍스트")
    @GetMapping("/q4")
    fun question4(): Boolean = questionService.question4()

    @ApiForQuest
    @Operation(summary = "5번 질문", description = "트랜잭션과 영속성 컨텍스트")
    @GetMapping("/q5")
    fun question5(): Boolean = questionService.question5()

    @ApiForQuest
    @Operation(summary = "6번 질문", description = "트랜잭션과 영속성 컨텍스트")
    @GetMapping("/q6")
    fun question6(): Boolean = questionService.question6()

    @ApiForQuest
    @Operation(summary = "7번 질문", description = "트랜잭션과 영속성 컨텍스트")
    @GetMapping("/q7")
    fun question7(): Boolean = questionService.question7()

    @ApiForQuest
    @Operation(summary = "8번 질문", description = "트랜잭션과 영속성 컨텍스트")
    @GetMapping("/q8")
    fun question8(): Boolean = questionService.question8()

    @ApiForQuest
    @Operation(summary = "9번 질문", description = "트랜잭션과 영속성 컨텍스트")
    @GetMapping("/q9")
    fun question9(): Boolean = questionService.question9()

    @ApiForQuest
    @Operation(summary = "10번 질문", description = "트랜잭션과 영속성 컨텍스트")
    @GetMapping("/q10")
    fun question10(): Boolean = questionService.question10()

    @ApiForQuest
    @Operation(summary = "11번 질문", description = "트랜잭션과 영속성 컨텍스트")
    @GetMapping("/q11")
    fun question11(): Boolean = questionService.question11()

    @ApiForQuest
    @Operation(summary = "12번 질문", description = "트랜잭션과 영속성 컨텍스트")
    @GetMapping("/q12")
    fun question12(): Boolean = questionService.question12()
}