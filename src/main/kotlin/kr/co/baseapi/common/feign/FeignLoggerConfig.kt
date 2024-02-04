package kr.co.baseapi.common.feign

import feign.Logger
import feign.Request
import feign.Response
import feign.Util
import mu.KotlinLogging
import java.nio.charset.StandardCharsets

private val log = KotlinLogging.logger {}

open class FeignLoggerConfig : Logger() {

    override fun logRequest(configKey: String?, logLevel: Level?, request: Request?) {
        super.logRequest(configKey, logLevel, request)

        if ((logLevel?.ordinal ?: Level.NONE.ordinal) >= Level.HEADERS.ordinal) {
            val body: String = request?.body()?.let { String(request.body(), StandardCharsets.UTF_8) } ?: ""
            log.info {
                """
                [Feign Request] 
                URI: ${request?.url()}
                Method: ${request?.httpMethod()}
                Headers: ${request?.headers()}
                Body: $body
                """.trimIndent()
            }
        }
    }

    override fun logAndRebufferResponse(
        configKey: String?,
        logLevel: Level?,
        response: Response?,
        elapsedTime: Long
    ): Response {
        val res: Response? =
            if ((logLevel?.ordinal ?: Level.NONE.ordinal) >= Level.HEADERS.ordinal) {
                if (response != null) {
                    val bytes: ByteArray =
                        if (response.body() == null) byteArrayOf()
                        else Util.toByteArray(response.body().asInputStream())

                    val body: String = String(bytes, StandardCharsets.UTF_8)
                    log.info {
                        """
                        [Feign Response] 
                        Status:${response.status()}
                        Body: $body
                        """.trimIndent()
                    }

                    response.toBuilder().body(bytes).build()
                } else {
                    log.info { "[Feign Response]" }
                    response
                }
            } else {
                response
            }

        return super.logAndRebufferResponse(configKey, logLevel, res, elapsedTime)
    }

    override fun log(p0: String?, p1: String?, vararg p2: Any?) {
    }
}
