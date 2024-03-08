package kr.co.baseapi.common.feign

import feign.Logger
import feign.Request
import feign.Response
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import java.nio.charset.Charset

class FeignLoggerTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    Given("FeignLogger와 Request, Response가 주어졌을 때") {
        val obj = object : FeignLogger() {
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

        val request: Request = mockk<Request>()
        every { request.protocolVersion() } returns Request.ProtocolVersion.MOCK
        every { request.url() } returns "http://example.com"
        every { request.httpMethod() } returns Request.HttpMethod.GET
        every { request.body() } returns "request body example".toByteArray(Charset.forName("UTF-8"))
        every { request.headers() } returns mapOf()
        every { request.length() } returns 0
        every { request.charset() } returns null

        val response: Response = mockk<Response>()
        every { response.body() } returns null

        When("logRequest를 실행하면") {
            obj.logRequest("configKey", Logger.Level.FULL, request)

            Then("request 로그가 출력된다") {
            }
        }

        When("logAndRebufferResponse를 실행하면") {
            obj.logAndRebufferResponse("configKey", Logger.Level.FULL, response, 1L)

            Then("response 로그가 출력된다") {
            }
        }
    }
})
