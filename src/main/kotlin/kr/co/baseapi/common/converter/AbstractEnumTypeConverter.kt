package kr.co.baseapi.common.converter

import jakarta.persistence.AttributeConverter
import kr.co.baseapi.common.util.EnumUtil

abstract class AbstractEnumTypeConverter<E>(private val eClass: Class<E>) :
    AttributeConverter<E, String> where E : Enum<E>, E : ConvertType {

    override fun convertToDatabaseColumn(enum: E?): String? = enum?.code

    override fun convertToEntityAttribute(code: String?): E? = EnumUtil.getEnumByCode(eClass, code)
}
