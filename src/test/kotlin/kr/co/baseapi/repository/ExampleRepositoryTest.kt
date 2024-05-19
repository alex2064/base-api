package kr.co.baseapi.repository

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import kr.co.baseapi.common.auditor.AuditorAwareConfig
import kr.co.baseapi.common.p6spy.SqlFormatConfig
import kr.co.baseapi.entity.Example
import kr.co.baseapi.enums.GenderType
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import java.math.BigDecimal
import java.time.LocalDate

@Import(AuditorAwareConfig::class, SqlFormatConfig::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExampleRepositoryTest(
    private val exampleRepository: ExampleRepository
) : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    extension(SpringTestExtension(mode = SpringTestLifecycleMode.Root))

    Given("[saveIsAuth] Example 엔티티가 여러개 주어지면") {
        val example1: Example = Example.of("kim", 10, 100L, BigDecimal.ZERO, GenderType.MAN, false, LocalDate.now())
        val example2: Example = Example.of("park", 10, 100L, BigDecimal.ZERO, GenderType.MAN, false, LocalDate.now())
        exampleRepository.saveAll(listOf(example1, example2))

        When("saveIsAuth를 사용해서 JPQL로 DB의 값을 변경할 때") {
            exampleRepository.saveIsAuth(true, listOf(example1.id!!, example2.id!!))
            val newExample1: Example = exampleRepository.findById(example1.id!!).orElseThrow()
            val newExample2: Example = exampleRepository.findById(example2.id!!).orElseThrow()

            Then("여러 행의 값이 같이 변경된다.") {
                newExample1.isAuth shouldBe true
                newExample2.isAuth shouldBe true
            }
        }
    }

    xGiven("[findByIdForLock] Example의 ID값이 주어지면") {
        val id: Long = 1L

        When("findByIdForLock를 사용해서 비관적 락(DB Lock)을 잡을 때") {
            val example: Example = exampleRepository.findByIdForLock(id).orElseThrow()

            Then("sleep(20초)을 걸면 해당 데이터의 값을 변경할 수 없어야한다.") {
                // 병렬로 작성하면 테스트코드가 많아져서 그냥 sleep 주고 DB에 붙어서 확인하자.
                Thread.sleep(20_000L)
            }
        }
    }

    Given("[findByName] Example 엔티티 조회 후 주어지면") {
        val example: Example = exampleRepository.findById(1L).orElseThrow()

        When("엔티티의 값을 변경 후 JPQL을 호출할 때") {
            example.modifyInfo("newname", 10, 100L, BigDecimal.ZERO, GenderType.MAN, false, LocalDate.now())
            val examples: List<Example> = exampleRepository.findByName("newname")

            Then("(flush)update, select 순서대로 SQL이 나가고 JPQL로 조회된 List에 처음 주어진 example이 포함되어야 한다.") {
                examples shouldContain example
            }
        }
    }
})