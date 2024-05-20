package kr.co.baseapi.common.util

import kotlin.math.min

object StringUtil {

    /**
     * 문자열과 바이트수를 받아서 해당 바이트수만큼 자르기(euc-kr 기준)
     */
    fun convertToEucKr(str: String, byteLength: Int): String =
        try {
            val strBytes: ByteArray = str.toByteArray(charset("EUC-KR"))
            val len: Int = min(byteLength, strBytes.size)
            val strEucKr: String = String(strBytes, 0, len, charset("EUC-KR"))

            extractCommonString(strEucKr, str)
        } catch (e: Exception) {
            ""
        }

    /**
     * 두 문자열에서 시작부터 공통되는 문자까지 추출
     */
    private fun extractCommonString(str1: String, str2: String): String {
        val minLength: Int = min(str1.length, str2.length)
        val sb: StringBuilder = StringBuilder()

        for (i in 0..<minLength) {
            if (str1[i] == str2[i]) {
                sb.append(str1[i])
            } else {
                break
            }
        }

        return sb.toString()
    }
}