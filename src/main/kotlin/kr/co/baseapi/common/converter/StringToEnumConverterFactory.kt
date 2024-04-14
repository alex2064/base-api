package kr.co.baseapi.common.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.converter.ConverterFactory
import org.springframework.stereotype.Component


@Component
class StringToEnumTypeConverterFactory : ConverterFactory<String, Enum<*>> {
    override fun <E : Enum<*>> getConverter(eClass: Class<E>): Converter<String, E> {
        return StringToEnumTypeConverter(eClass)
    }
}

class StringToEnumTypeConverter<E : Enum<*>>(private val eClass: Class<E>) : Converter<String, E> {
    override fun convert(name: String): E? =
        eClass.enumConstants.firstOrNull {
            if (it is ConvertType) {
                it.name == name || it.code == name
            } else {
                it.name == name
            }
        }
}