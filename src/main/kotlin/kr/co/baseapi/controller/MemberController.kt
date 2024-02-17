package kr.co.baseapi.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.baseapi.common.swagger.ApiForExam
import kr.co.baseapi.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Member controller")
@RequestMapping("/member")
@RestController
class MemberController(
    private val memberService: MemberService
) {

    @ApiForExam
    @Operation(summary = "예시 End Point(Get)", description = "예시 End Point(Get) description")
    @GetMapping("/sign")
    fun saveMember(): Boolean {
        return memberService.saveMember()
    }
}