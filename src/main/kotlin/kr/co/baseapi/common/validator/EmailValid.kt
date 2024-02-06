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
@Constraint(validatedBy = [EmailValidator::class])
annotation class EmailValid(
    val message: String = "{jakarta.validation.constraints.Email.message}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val regexp: String = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*[.][a-zA-Z]{2,3}$",
    val flags: Array<Pattern.Flag> = []
)

class EmailValidator : ConstraintValidator<EmailValid, String> {
    private lateinit var pattern: java.util.regex.Pattern

    override fun initialize(annotation: EmailValid) {
        pattern = java.util.regex.Pattern.compile(annotation.regexp)
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean =
        when {
            value == null -> true
            value.isBlank() -> false
            else -> pattern.matcher(value.trim()).matches()
        }
}