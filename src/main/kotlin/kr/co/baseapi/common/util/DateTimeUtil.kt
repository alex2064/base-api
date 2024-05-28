package kr.co.baseapi.common.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter.BASIC_ISO_DATE
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE

/**
 * String(yyyy-MM-dd or yyyyMMdd) -> LocalDate 변환
 */
fun String.toLocalDate(): LocalDate? =
    try {
        LocalDate.parse(this.replace("-", ""), BASIC_ISO_DATE)
    } catch (e: Exception) {
        null
    }

/**
 * LocalDate -> String(yyyyMMdd)
 */
fun LocalDate.toString8(): String = this.format(BASIC_ISO_DATE)

/**
 * LocalDate -> String(yyyy-MM-dd)
 */
fun LocalDate.toString10(): String = this.format(ISO_LOCAL_DATE)

/**
 * 현재일자 ~ toDate 까지 LocalDate List
 */
infix fun LocalDate.toDatesUntil(toDate: LocalDate): List<LocalDate> =
    if (toDate.isBefore(this)) {
        listOf()
    } else {
        datesUntilSequence(this, toDate).toList()
    }

private fun datesUntilSequence(frDate: LocalDate, toDate: LocalDate): Sequence<LocalDate> =
    generateSequence(frDate) { it.plusDays(1L) }
        .takeWhile { it.isBefore(toDate) || it == toDate }  // it <= toDate ??



