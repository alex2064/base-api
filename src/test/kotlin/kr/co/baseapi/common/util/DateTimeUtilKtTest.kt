package kr.co.baseapi.common.util

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class DateTimeUtilKtTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    Given("날짜 형식의 LocalDate와 String 이 주어지면") {
        val localDate: LocalDate = LocalDate.of(2024, 1, 1)
        val str8: String = "20240101"
        val str10: String = "2024-01-01"
        val strWorng: String = "2024"

        When("확장함수 toLocalDate 를 사용하면") {
            Then("LocalDate 로 변환되어야 한다.") {
                str8.toLocalDate() shouldBe localDate
                str10.toLocalDate() shouldBe localDate
            }
        }

        When("확장함수 toString8 을 사용하면") {
            Then("yyyyMMdd String로 변환되어야 한다.") {
                localDate.toString8() shouldBe str8
            }
        }

        When("확장함수 toString10 을 사용하면") {
            Then("yyyy-MM-dd String로 변환되어야 한다.") {
                localDate.toString10() shouldBe str10
            }
        }

        When("잘못된 문자열에 확장함수 toLocalDate 를 사용하면") {
            Then("null 로 변환되어야 한다.") {
                strWorng.toLocalDate() shouldBe null
            }
        }
    }
})
