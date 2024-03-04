package kr.co.baseapi

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import kr.co.baseapi.entity.Member
import kr.co.baseapi.repository.MemberRepository
import kr.co.baseapi.repository.MemberRepositorySupport
import kr.co.baseapi.service.MemberService
import kr.co.baseapi.service.MemberServiceImpl
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

// classes에 실제 테스트할 대상만 추가
@SpringBootTest(classes = [MemberServiceImpl::class])
class ExamTest(
    private val memberService: MemberService,   // 테스트할 대상은 주입받고
    @MockkBean private val memberRepository: MemberRepository,              // 아닌 대상은 Mockk로
    @MockkBean private val memberRepositorySupport: MemberRepositorySupport // 아닌 대상은 Mockk로
) : BehaviorSpec({  // BehaviorSpec : BDD스타일로 테스트 코드 작성
    isolationMode = IsolationMode.InstancePerLeaf   // 테스트 별로 별도로

    Given("기본 셋팅 대상") {
        val memberId: Long = 1L
        every { memberRepository.findById(memberId).orElse(null) } returns null

        val pageable: Pageable = PageRequest.of(0, 10)
        every { memberRepositorySupport.findDslPage(pageable) } returns PageImpl<Member>(listOf(), pageable, 0)

        When("테스트할 상황 제공") {
            val bool: Boolean = memberService.saveMember()

            Then("결과 확인") {
                bool shouldBe true
            }
        }
    }
})