package kr.co.baseapi.common.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [EnumValidator::class])
annotation class EnumValid(
    val message: String = "Invalid enum value",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val enumClass: KClass<out Enum<*>>
)

class EnumValidator : ConstraintValidator<EnumValid, Any> {
    private lateinit var enums: Array<out Enum<*>>

    override fun initialize(annotation: EnumValid) {
        enums = annotation.enumClass.java.enumConstants
    }

    override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {
        val isValid: Boolean = when (value) {
            null -> false
            is Enum<*> -> enums.any { it.name == value.name }
            is String -> enums.any { it.name == value }
            else -> false
        }

        if (!isValid && context != null) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate("유효하지 않은 값입니다. 가능한 값은 [${enums.joinToString { it.name }}] 중 하나여야 합니다.")
                .addConstraintViolation()
        }

        return isValid
    }
}