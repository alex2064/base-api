package kr.co.baseapi.enums

import com.fasterxml.jackson.annotation.JsonCreator
import kr.co.baseapi.common.converter.ConvertType
import kr.co.baseapi.common.util.EnumUtil


enum class ExamType(
    override val code: String,
    override val desc: String
) : ConvertType {
    MAN("man", "남자"),
    WOMAN("woman", "여자");

    companion object {
        @JvmStatic
        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        fun get(name: String?): ExamType? = EnumUtil.getEnumByNameOrCode(ExamType::class.java, name)
    }
}

enum class GenderType(
    override val code: String,
    override val desc: String
) : ConvertType {
    MAN("man", "남자"),
    WOMAN("woman", "여자");
}