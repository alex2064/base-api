package kr.co.baseapi.entity.converter

import jakarta.persistence.Converter
import kr.co.baseapi.common.converter.AbstractEnumTypeConverter
import kr.co.baseapi.enums.GenderType

/**
 * GUIDE
 * Converter 생성
 * 1. Entity 에서 사용할 Enum 을 @Converter 로 생성
 * 2. AbstractEnumTypeConverter 상속받아서 사용
 * 3. converter 명은 "Enum Class" + "Converter" 로 사용
 * 4. 하나의 kt 파일 안에 여러 Converter 를 작성
 */
@Converter
class GenderTypeConverter : AbstractEnumTypeConverter<GenderType>(GenderType::class.java)
