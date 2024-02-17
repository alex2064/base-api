package kr.co.baseapi.entity.converter

import jakarta.persistence.Converter
import kr.co.baseapi.common.converter.AbstractEnumTypeConverter
import kr.co.baseapi.enums.GenderType

@Converter
class GenderTypeConverter : AbstractEnumTypeConverter<GenderType>(GenderType::class.java)
