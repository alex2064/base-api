package kr.co.baseapi.common.auditor

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.optional.shouldBePresent
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

class AuditorAwareConfigTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    Given("AuditorAwareConfig가 주어지면") {
        val auditorAwareConfig: AuditorAwareConfig = AuditorAwareConfig()

        When("currentAuditor 메서드를 확인할 때") {
            val currentAuditor: Optional<Long> = auditorAwareConfig.currentAuditor

            Then("currentAuditor에 값이 존재해야 한다.") {
                currentAuditor.shouldBePresent()
            }
            Then("그 값은 100L 이여야 한다.") {
                currentAuditor.get() shouldBe 100L
            }
        }
    }
})
