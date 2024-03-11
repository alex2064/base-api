package kr.co.baseapi.common.feign

import feign.Logger
import feign.Request
import feign.Response
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class FeignLoggerTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    Given("FeignLogger가 주어지고") {
        // FeignLogger(override 해서 접근제어자 public으로 변경)
        val feignLogger = object : FeignLogger() {
            public override fun logRequest(configKey: String?, logLevel: Level?, request: Request?) {
                super.logRequest(configKey, logLevel, request)
            }

            public override fun logAndRebufferResponse(
                configKey: String?,
                logLevel: Level?,
                response: Response?,
                elapsedTime: Long
            ): Response {
                return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime)
            }
        }

        And("Request가 주어지면") {
            val request: Request = mockk<Request>()
            every { request.protocolVersion() } returns Request.ProtocolVersion.MOCK
            every { request.url() } returns "http://example.com"
            every { request.httpMethod() } returns Request.HttpMethod.GET
            every { request.body() } returns "request body example".toByteArray(Charset.forName("UTF-8"))
            every { request.headers() } returns mapOf()
            every { request.length() } returns 0
            every { request.charset() } returns null

            When("logRequest를 실행할 때") {
                feignLogger.logRequest("configKey", Logger.Level.FULL, request)

                Then("request 로그가 출력되어야 한다.") {
                }
            }
        }

        And("Response와 builder가 주어지면") {
            val response: Response = mockk<Response>()
            every { response.status() } returns 200
            every { response.headers() } returns mapOf()
            every {
                response.body().asInputStream()
            } returns "response body example".byteInputStream(StandardCharsets.UTF_8)
            every { response.request() } returns mockk<Request>()
            every { response.protocolVersion() } returns Request.ProtocolVersion.MOCK
            every { response.reason() } returns "test"

            // logAndRebufferResponse 처리시 builder 필요
            val builder: Response.Builder = mockk<Response.Builder>()
            every { builder.body(any<ByteArray>()) } returns builder
            every { builder.build() } returns response
            every { response.toBuilder() } returns builder

            When("logAndRebufferResponse를 실행할 때") {
                val res: Response = feignLogger.logAndRebufferResponse(
                    "configKey",
                    Logger.Level.FULL,
                    response,
                    1_000L
                )

                Then("response가 반환되고 로그가 출력되어야 한다.") {
                    res shouldBe response
                }
            }
        }
    }
})
