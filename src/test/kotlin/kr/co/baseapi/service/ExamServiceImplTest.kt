package kr.co.baseapi.service

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import kr.co.baseapi.dto.*
import kr.co.baseapi.entity.Example
import kr.co.baseapi.enums.GenderType
import kr.co.baseapi.repository.ExampleRepository
import kr.co.baseapi.repository.ExampleRepositorySupport
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal
import java.time.LocalDate

@Import(ExamServiceImpl::class)
class ExamServiceImplTest(
    private val examService: ExamService,
    @MockkBean private val exampleRepository: ExampleRepository,
    @MockkBean private val exampleRepositorySupport: ExampleRepositorySupport
) : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    Given("[findExample] 조회할 Example의 id가 주어지면") {
        val id: Long = 1L
        val example: Example = Example.of("kim", 10, 100L, BigDecimal.ZERO, GenderType.MAN, false, LocalDate.now())
        every { exampleRepository.findById(id).orElseThrow() } returns example

        When("id로 example을 찾을 때") {
            val result: ExamResult = examService.findExample(id)

            Then("Result DTO와 엔티티에 들어있는 값이 일치해야 한다.") {
                result.name shouldBe example.name
                result.age shouldBe example.age
                result.amount shouldBe example.amount
                result.height shouldBe example.height
                result.gender shouldBe example.gender
                result.isAuth shouldBe example.isAuth
                result.baseDate shouldBe example.baseDate
            }
        }
    }

    Given("[saveExample] 저장할 DTO가 주어지면") {
        val param: ExamParam = ExamParam("kim", 10, 100L, BigDecimal.ZERO, GenderType.MAN, false, LocalDate.now())
        val example: Example = Example.of("kim", 10, 100L, BigDecimal.ZERO, GenderType.MAN, false, LocalDate.now())
        every { exampleRepository.save(any()) } returns example

        When("DTO로 Example에 저장할 때") {
            val result: Boolean = examService.saveExample(param)

            Then("result로 true가 나와야 한다.") {
                result shouldBe true
            }
        }
    }

    Given("[saveExampleInfo] 수정할 DTO가 주어지면") {
        val id: Long = 1L
        val param: ExamParam = ExamParam("kim", 10, 100L, BigDecimal.ZERO, GenderType.MAN, false, LocalDate.now())
        val example: Example = Example.of("kim", 10, 100L, BigDecimal.ZERO, GenderType.MAN, false, LocalDate.now())
        every { exampleRepository.findById(id).orElseThrow() } returns example

        When("DTO로 Example에 저장할 때") {
            val result: Boolean = examService.saveExampleInfo(id, param)

            Then("result로 true가 나와야 한다.") {
                result shouldBe true
            }
        }
    }

    Given("[deleteExample] 삭제할 id가 주어지면") {
        val id: Long = 1L
        val example: Example = Example.of("kim", 10, 100L, BigDecimal.ZERO, GenderType.MAN, false, LocalDate.now())
        every { exampleRepository.findById(id).orElseThrow() } returns example
        every { exampleRepository.delete(example) } returns Unit

        When("id로 Example을 삭제할 때") {
            val result: Boolean = examService.deleteExample(id)

            Then("result로 true가 나와야 한다.") {
                result shouldBe true
            }
        }
    }

    Given("[saveIsAuth] 인증여부를 변경할 id리스트와 인증여부가 주어지면") {
        val param: ExamIsAuthParam = ExamIsAuthParam(mutableListOf(1L, 2L), true)
        every { exampleRepository.saveIsAuth(param.isAuth!!, param.ids!!) } returns Unit

        When("DTO로 인증여부를 수정할 때") {
            val result: Boolean = examService.saveIsAuth(param)

            Then("result로 true가 나와야 한다.") {
                result shouldBe true
            }
        }
    }

    Given("[findExampleForLock] 조회할 Example의 id가 주어지면") {
        val id: Long = 1L
        val example: Example = Example.of("kim", 10, 100L, BigDecimal.ZERO, GenderType.MAN, false, LocalDate.now())
        every { exampleRepository.findByIdForLock(id).orElseThrow() } returns example

        When("id로 example을 찾을 때") {
            val result: ExamResult = examService.findExampleForLock(id)

            Then("Result DTO와 엔티티에 들어있는 값이 일치해야 한다.") {
                result.name shouldBe example.name
                result.age shouldBe example.age
                result.amount shouldBe example.amount
                result.height shouldBe example.height
                result.gender shouldBe example.gender
                result.isAuth shouldBe example.isAuth
                result.baseDate shouldBe example.baseDate
            }
        }
    }

    Given("[findExampleDsl] 조회할 Example의 id가 주어지면") {
        val id: Long = 1L
        val example: Example = Example.of("kim", 10, 100L, BigDecimal.ZERO, GenderType.MAN, false, LocalDate.now())
        every { exampleRepositorySupport.findById(id).orElseThrow() } returns example

        When("id로 example을 찾을 때") {
            val result: ExamResult = examService.findExampleDsl(id)

            Then("Result DTO와 엔티티에 들어있는 값이 일치해야 한다.") {
                result.name shouldBe example.name
                result.age shouldBe example.age
                result.amount shouldBe example.amount
                result.height shouldBe example.height
                result.gender shouldBe example.gender
                result.isAuth shouldBe example.isAuth
                result.baseDate shouldBe example.baseDate

            }
        }
    }

    Given("[findExampleDslPage] 페이징 처리해서 조회할 Example의 name이 주어지면") {
        val param: ExamPageParam = ExamPageParam("kim")
        every { exampleRepositorySupport.findByNamePage(param) } returns
                PageImpl<ExamResult>(mutableListOf(), PageRequest.of(0, 20), 0L)

        When("DTO로 example을 찾을 때") {
            val result: PageResult<ExamResult> = examService.findExampleDslPage(param)

            Then("페이징 처리된 빈 result DTO가 나와야 한다.") {
                result.pageNumber shouldBe 0
                result.pageSize shouldBe 20
                result.totalPages shouldBe 0
                result.totalCount shouldBe 0L
                result.list shouldBe emptyList()
            }
        }
    }
})