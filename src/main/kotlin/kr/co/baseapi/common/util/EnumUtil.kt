package kr.co.baseapi.common.util

import kr.co.baseapi.common.converter.ConvertType

object EnumUtil {
    fun <E> getEnumByCode(eClass: Class<E>, code: String?): E? where E : Enum<E>, E : ConvertType =
        eClass.enumConstants.firstOrNull { it.code == code }

    fun <E> getEnumByNameOrCode(eClass: Class<E>, name: String?): E? where E : Enum<E>, E : ConvertType =
        eClass.enumConstants.firstOrNull { it.name == name || it.code == name }
}