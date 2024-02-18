package kr.co.baseapi.common.p6spy

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.P6SpyOptions
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import jakarta.annotation.PostConstruct
import org.hibernate.engine.jdbc.internal.FormatStyle
import org.springframework.context.annotation.Configuration
import org.springframework.util.ClassUtils
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

@Configuration
class SqlFormatConfig : MessageFormattingStrategy {

    private val iso8601Pattern: Pattern = Pattern.compile(
        "\\b(\\d{4}-\\d{2}-\\d{2})T(\\d{2}:\\d{2}:\\d{2})\\.\\d{3}\\+\\d{4}\\b"
    )

    @PostConstruct
    fun setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().logMessageFormat = this.javaClass.name
    }

    override fun formatMessage(
        connectionId: Int,
        now: String?,
        elapsed: Long,
        category: String?,
        prepared: String?,
        sql: String?,
        url: String?
    ): String {
        return when (category) {
            Category.STATEMENT.name -> "[$category] | $elapsed ms | ${formatSql(sql)}"
            else -> "[$category] | $elapsed ms"
        }
    }

    private fun formatSql(sql: String?): String? {
        val temp: String = sql?.trim()?.lowercase(Locale.ROOT) ?: ""
        return when {
            temp == "" -> sql

            (temp.startsWith("create") ||
                    temp.startsWith("alter") ||
                    temp.startsWith("comment")) -> FormatStyle.DDL.formatter.format(sql)

            else -> {
                val sb: StringBuilder = StringBuilder()
                val matcher: Matcher = iso8601Pattern.matcher(sql.toString())
                while (matcher.find()) {
                    val isoDate: String = matcher.group(0)
                    val convertDate: String = convertIsoDateString(isoDate)
                    matcher.appendReplacement(sb, convertDate)
                }
                matcher.appendTail(sb)

                FormatStyle.BASIC.formatter.format(sb.toString())
            }
        }
    }

    private fun convertIsoDateString(isoDate: String): String {
        val zonedDateTime: ZonedDateTime =
            ZonedDateTime.parse(isoDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))

        return if ((zonedDateTime.hour + zonedDateTime.minute + zonedDateTime.second) == 0) {
            zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        } else {
            zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        }
    }

    private fun stackTrace(): String {
        return Throwable().stackTrace.filter {
            it.toString().startsWith("kr.co") && !it.toString().contains(ClassUtils.getUserClass(this).name)
        }.toString()
    }
}