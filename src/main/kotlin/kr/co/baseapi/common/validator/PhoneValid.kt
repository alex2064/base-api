package kr.co.baseapi.common.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import jakarta.validation.constraints.Pattern
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [PhoneValidator::class])
annotation class PhoneValid(
    val message: String = "올바른 형식의 휴대전화 번호여야 합니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val regexp: String = "^01(?:0|1|[6-9])(\\\\d{3}|\\\\d{4})\\\\d{4}\$",
    val flags: Array<Pattern.Flag> = []
)

class PhoneValidator : ConstraintValidator<PhoneValid, String> {
    private lateinit var pattern: java.util.regex.Pattern

    override fun initialize(annotation: PhoneValid) {
        pattern = java.util.regex.Pattern.compile(annotation.regexp)
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        val phone: String = value?.replace("-", "")?.trim() ?: ""
        return when {
            value == null -> true
            phone.isBlank() -> false
            else -> pattern.matcher(phone).matches()
        }
    }

}