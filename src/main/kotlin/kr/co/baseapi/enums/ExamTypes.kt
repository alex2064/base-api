package kr.co.baseapi.enums

import com.fasterxml.jackson.annotation.JsonCreator
import kr.co.baseapi.common.converter.ConvertType
import kr.co.baseapi.common.util.EnumUtil


/**
 * enum 생성
 * 1. Enum class 이름은 테이블 컬럼명과 일치
 * 2. Database 와 연동되는 것은 ConvertType 구현(향후 Converter 에 사용)
 * 3. name : Kotlin code 에서 사용할 Enum Name(대문자)
 * 4. code : Database 에 저장할 String
 * 5. Body 를 DTO 로 받는 경우 @JsonCreator 로 유효한 것만 DTO 에 담기
 */
enum class GenderType(
    override val code: String,
    override val desc: String
) : ConvertType {

    MAN("man", "남자"),
    WOMAN("woman", "여자");

    companion object {
        @JvmStatic
        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        fun get(name: String?): GenderType? = EnumUtil.getEnumByNameOrCode(GenderType::class.java, name)
    }
}