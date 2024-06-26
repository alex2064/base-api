package kr.co.baseapi.common.converter

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class AbstractEnumTypeConverterTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    Given("AbstractEnumTypeConverter가 주어지면") {
        val converter: AbstractEnumTypeConverter<SampleEnum> =
            object : AbstractEnumTypeConverter<SampleEnum>(SampleEnum::class.java) {}

        When("Enum을 Database에 넣는 값으로 변환할 때") {
            val firstStr: String? = converter.convertToDatabaseColumn(SampleEnum.FIRST)
            val secondStr: String? = converter.convertToDatabaseColumn(SampleEnum.SECOND)

            Then("first와 second가 나와야 한다.") {
                firstStr shouldBe "first"
                secondStr shouldBe "second"
            }
        }

        When("Database의 값을 Enum으로 변환할 때") {
            val firstEnum: SampleEnum? = converter.convertToEntityAttribute("first")
            val secondEnum: SampleEnum? = converter.convertToEntityAttribute("second")

            Then("SampleEnum.FIRST와 SampleEnum.SECOND가 나와야 한다.") {
                firstEnum shouldBe SampleEnum.FIRST
                secondEnum shouldBe SampleEnum.SECOND
            }
        }
    }
})

enum class SampleEnum(
    override val code: String,
    override val desc: String
) : ConvertType {
    FIRST("first", "1"),
    SECOND("second", "2")
}
