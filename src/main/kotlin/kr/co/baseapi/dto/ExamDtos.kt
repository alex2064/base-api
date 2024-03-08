package kr.co.baseapi.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import kr.co.baseapi.common.validator.EmailValid
import kr.co.baseapi.common.validator.EnumValid
import kr.co.baseapi.entity.Example
import kr.co.baseapi.enums.GenderType
import java.math.BigDecimal
import java.time.LocalDate


// Spring validation 사용(기본 생성자 + val + null 허용으로 만들기)
data class ExamVaildParam(

    // String 타입 : @NotBlank
    val string: String?,

    @field:NotBlank
    val stringNotBlank: String?,


    // Int 타입 : @Positive, @NotNull
    val int: Int?,

    @field:NotNull
    val intNotNull: Int?,

    @field:Positive
    val intPositive: Int?,

    @field:NotNull
    @field:Positive
    val intNotNullPositive: Int?,


    // Long 타입 : @Positive, @NotNull
    val long: Long?,

    @field:NotNull
    val longNotNull: Long?,

    @field:Positive
    val longPositive: Long?,

    @field:NotNull
    @field:Positive
    val longNotNullPositive: Long?,


    // BigDecimal 타입 : @DecimalMin(value = "0.0"), @NotNull
    val bigDecimal: BigDecimal?,

    @field:NotNull
    val bigDecimalNotNull: BigDecimal?,

    @field:DecimalMin(value = "0.0")
    val bigDecimalDecimalMin: BigDecimal?,

    @field:NotNull
    @field:DecimalMin(value = "0.0")
    val bigDecimalNotNullDecimalMin: BigDecimal?,


    // Enum 타입 : @EnumValid, enum class에 EnumUtil.getEnumByNameOrCode 필수
    val examType: GenderType?,

    @field:EnumValid(enumClass = GenderType::class)
    val examTypeEnumValid: GenderType?,


    // LocalDate 타입 : @NotNull, enum처럼 건드릴 수 없어서 날짜 형식이 아니면 미리 error 발생
    val localdate: LocalDate?,

    @field:NotNull
    val localdateNotNull: LocalDate?,


    // Email 타입 : @EmailValid, @NotBlank
    @field:EmailValid
    val emailEmailValid: String?,

    @field:NotBlank
    @field:EmailValid
    val emailNotBlankEmailValid: String?,


    @field:Schema(description = "ID")
    val id: Long?
)


data class ExamPageParam(
    @field:Schema(description = "이름")
    val name: String?
) : PageParam()


data class ExamParam(
    @field:Schema(description = "ID")
    @field:NotNull
    @field:Positive
    val id: Long?,

    @field:Schema(description = "이름")
    @field:NotBlank
    val name: String?,

    @field:Schema(description = "나이")
    @field:NotNull
    @field:Positive
    val age: Int?,

    @field:Schema(description = "금액")
    @field:NotNull
    @field:Positive
    val amount: Long?,

    @field:Schema(description = "키")
    @field:NotNull
    @field:DecimalMin(value = "0.0")
    val height: BigDecimal?,

    @field:Schema(description = "성별")
    @field:EnumValid(enumClass = GenderType::class)
    val gender: GenderType?,

    @field:Schema(description = "인증여부")
    @field:NotNull
    val isAuth: Boolean?,

    @field:Schema(description = "기준일")
    @field:NotNull
    val baseDate: LocalDate?
)


data class ExamResult(
    @field:Schema(description = "ID")
    @field:NotNull
    @field:Positive
    val id: Long?,

    @field:Schema(description = "이름")
    @field:NotBlank
    val name: String?,

    @field:Schema(description = "나이")
    @field:NotNull
    @field:Positive
    val age: Int?,

    @field:Schema(description = "금액")
    @field:NotNull
    @field:Positive
    val amount: Long?,

    @field:Schema(description = "키")
    @field:NotNull
    @field:DecimalMin(value = "0.0")
    val height: BigDecimal?,

    @field:Schema(description = "성별")
    @field:EnumValid(enumClass = GenderType::class)
    val gender: GenderType?,

    @field:Schema(description = "인증여부")
    @field:NotNull
    val isAuth: Boolean?,

    @field:Schema(description = "기준일")
    @field:NotNull
    val baseDate: LocalDate?
) {
    companion object {
        fun exampleOf(example: Example): ExamResult =
            ExamResult(
                id = example.id,
                name = example.name,
                age = example.age,
                amount = example.amount,
                height = example.height,
                gender = example.gender,
                isAuth = example.isAuth,
                baseDate = example.baseDate
            )
    }
}


data class ExamIsAuthParam(
    @field:Schema(description = "ID리스트")
    @field:NotNull
    val ids: MutableList<Long>?,

    @field:Schema(description = "인증여부")
    @field:NotNull
    val isAuth: Boolean?,
)
