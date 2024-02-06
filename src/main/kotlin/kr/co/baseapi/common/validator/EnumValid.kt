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

class EnumValidator : ConstraintValidator<EnumValid, String> {
    private lateinit var enums: Array<out Enum<*>>

    override fun initialize(annotation: EnumValid) {
        enums = annotation.enumClass.java.enumConstants
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            return true
        }
        return enums.any { it.name == value }
    }
}