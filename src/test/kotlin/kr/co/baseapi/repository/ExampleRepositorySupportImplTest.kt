package kr.co.baseapi.repository

import feign.Request
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kr.co.baseapi.common.auditor.AuditorAwareConfig
import kr.co.baseapi.common.p6spy.SqlFormatConfig
import kr.co.baseapi.common.querydsl.QuerydslConfig
import kr.co.baseapi.dto.ExamPageParam
import kr.co.baseapi.dto.ExamResult
import kr.co.baseapi.entity.Example
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

@Import(AuditorAwareConfig::class, SqlFormatConfig::class, QuerydslConfig::class, ExampleRepositorySupportImpl::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExampleRepositorySupportImplTest(
    private val exampleRepositorySupport: ExampleRepositorySupport
) : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    extension(SpringTestExtension(mode = SpringTestLifecycleMode.Root))

    Given("Example의 ID값이 주어지면") {
        val id: Long = 1L

        When("querydsl로 조회할 때") {
            val example: Example? = exampleRepositorySupport.findById(id).orElse(null)

            Then("id값이 서로 같아야 한다.") {
                example?.id shouldBe id
            }
        }
    }

    Given("페이징 처리하는 ExamPageParam이 주어지면") {
        val param: ExamPageParam = mockk<ExamPageParam>()
        every { param.pageable } returns PageRequest.of(0, 20)
        every { param.name } returns "kim"

        When("findByNamePage로 조회할 때") {
            val result: Page<ExamResult> = exampleRepositorySupport.findByNamePage(param)

            Then("페이징 처리가 되어 content, total SQL 날아가고 페이징 정보는 일치해야 한다.") {
                result.pageable shouldBe PageRequest.of(0, 20)
                result.number shouldBe 0
            }
        }
    }
})
