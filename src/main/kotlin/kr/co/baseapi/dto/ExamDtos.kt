package kr.co.baseapi.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import kr.co.baseapi.common.validator.EmailValid
import kr.co.baseapi.common.validator.EnumValid
import kr.co.baseapi.common.validator.PhoneValid
import kr.co.baseapi.entity.Example
import kr.co.baseapi.enums.GenderType
import java.math.BigDecimal
import java.time.LocalDate


/**
 * GUIDE
 * DTO 생성
 * 1. data class(주 생성자 + val + null 허용) 로 만들기
 * 2. Spring validation 으로 유효성 체크
 *      1) String 타입 : @NotBlank
 *      2) Int 타입 : @PositiveOrZero, @Positive, @NotNull
 *      3) Long 타입 : @PositiveOrZero, @Positive, @NotNull
 *      4) BigDecimal 타입 : @DecimalMin(value = "0.0"), @NotNull
 *      5) Enum 타입 : @EnumValid, enum class 에 EnumUtil.getEnumByNameOrCode 필수
 *      6) LocalDate 타입 : @NotNull, enum 처럼 건드릴 수 없어서 날짜 형식이 아니면 미리 error 발생
 *      7) Email 타입 : @EmailValid, @NotBlank
 *      8) Phone 타입 : @PhoneValid, @NotBlank
 * 3. @Schema 필수
 * 4. Paging 처리가 필요한 요청은 PageParam() 상속
 * 5. QueryDsl 로 조회한 결과 DTO 도 client 에 보낼때는 Response 용 DTO 호 변환해서 사용
 * 6. 주 생성자와 팩토리 메서드 둘 다 사용
 *      1) 주 생성자 : 값을 그대로 사용해서 인스턴스를 만드는 경우
 *      2) 팩토리 메서드 : 값을 그대로 사용 안하고 setter 로 뽑거나 가공 처리하고 인스턴스를 만드는 경우
 * 7. 클래스 명 사용
 *      1) {~Param} : Request DTO
 *      2) {~Result} : Response DTO
 *      3) {~FeignParam} : OpenFeign 에 사용되는 Request DTO
 *      4) {~FeignResult} : OpenFeign 에 사용되는 Response DTO
 *      5) {~Dto} : QueryDsl 등 어플리케이션 내부적으로 사용하는 DTO
 */
data class ExamVaildParam(

    // String 타입 : @NotBlank
    val string: String?,

    @field:NotBlank
    val stringNotBlank: String?,


    // Int 타입 : @PositiveOrZero, @Positive, @NotNull
    val int: Int?,

    @field:NotNull
    val intNotNull: Int?,

    @field:Positive
    val intPositive: Int?,

    @field:NotNull
    @field:Positive
    val intNotNullPositive: Int?,


    // Long 타입 : @PositiveOrZero, @Positive, @NotNull
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


    // Enum 타입 : @EnumValid, enum class 에 EnumUtil.getEnumByNameOrCode 필수
    val examType: GenderType?,

    @field:EnumValid(enumClass = GenderType::class)
    val examTypeEnumValid: GenderType?,


    // LocalDate 타입 : @NotNull, enum 처럼 건드릴 수 없어서 날짜 형식이 아니면 미리 error 발생
    val localdate: LocalDate?,

    @field:NotNull
    val localdateNotNull: LocalDate?,


    // Email 타입 : @EmailValid, @NotBlank
    @field:EmailValid
    val emailValid: String?,

    @field:NotBlank
    @field:EmailValid
    val emailNotBlankValid: String?,

    // Phone 타입 : @PhoneValid, @NotBlank
    @field:PhoneValid
    val phoneValid: String?,

    @field:NotBlank
    @field:PhoneValid
    val phoneNotBlankValid: String?,
)

data class ExamIdParam(
    @field:Schema(description = "ID")
    @field:NotNull
    @field:Positive
    val id: Long?
)

data class ExamParam(
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
    @field:NotEmpty
    val ids: List<Long>?,

    @field:Schema(description = "인증여부")
    @field:NotNull
    val isAuth: Boolean?,
)

data class ExamPageParam(
    @field:Schema(description = "이름")
    val name: String?
) : PageParam()