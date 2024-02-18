package kr.co.baseapi.common.p6spy

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.P6SpyOptions
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import jakarta.annotation.PostConstruct
import org.hibernate.engine.jdbc.internal.FormatStyle
import org.springframework.context.annotation.Configuration
import org.springframework.util.ClassUtils
import java.util.*

@Configuration
class SqlFormatConfig : MessageFormattingStrategy {

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

            else -> FormatStyle.BASIC.formatter.format(sql)
        }
    }

    private fun stackTrace(): String {
        return Throwable().stackTrace.filter {
            it.toString().startsWith("kr.co") && !it.toString().contains(ClassUtils.getUserClass(this).name)
        }.toString()
    }
}