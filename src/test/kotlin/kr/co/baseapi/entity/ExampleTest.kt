package kr.co.baseapi.entity

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kr.co.baseapi.enums.GenderType
import java.math.BigDecimal
import java.time.LocalDate

class ExampleTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    Given("[of] Example 엔티티 생성할 값이 주어지면") {
        val name: String = "kim"
        val age: Int = 35
        val amount: Long = 10_000L
        val height: BigDecimal = BigDecimal(20_000L)
        val gender: GenderType = GenderType.MAN
        val isAuth: Boolean = true
        val baseDate: LocalDate = LocalDate.now()

        When("팩토리메서드로 Example 엔티티를 생성할 때") {
            val example: Example = Example.of(name, age, amount, height, gender, isAuth, baseDate)

            Then("값이 올바르게 할당되어야 한다.") {
                example.name shouldBe name
                example.age shouldBe age
                example.amount shouldBe amount
                example.height shouldBe height
                example.gender shouldBe gender
                example.isAuth shouldBe isAuth
                example.baseDate shouldBe baseDate
            }
        }
    }

    Given("[updateInfo] Example 엔티티와 수정할 값이 주어지면") {
        val example: Example = Example.of(null, null, null, null, null, null, null)
        val name: String = "kim"
        val age: Int = 35
        val amount: Long = 10_000L
        val height: BigDecimal = BigDecimal(20_000L)
        val gender: GenderType = GenderType.MAN
        val isAuth: Boolean = true
        val baseDate: LocalDate = LocalDate.now()

        When("updateInfo로 Example 엔티티를 수정할 때") {
            example.updateInfo(name, age, amount, height, gender, isAuth, baseDate)

            Then("값이 올바르게 변경되어야 한다.") {
                example.name shouldBe name
                example.age shouldBe age
                example.amount shouldBe amount
                example.height shouldBe height
                example.gender shouldBe gender
                example.isAuth shouldBe isAuth
                example.baseDate shouldBe baseDate
            }
        }
    }
})
