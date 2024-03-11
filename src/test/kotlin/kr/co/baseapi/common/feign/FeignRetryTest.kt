package kr.co.baseapi.common.feign

import feign.RequestInterceptor
import feign.RequestTemplate
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class FeignRetryTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    Given("FeignRetry가 주어지고") {
        val feignRetry: FeignRetry = FeignRetry()

        And("GET 요청을 나타내는 RequestTemplate가 주어지면") {
            val requestTemplate: RequestTemplate = mockk<RequestTemplate>(relaxed = true)
            every { requestTemplate.method() } returns "GET"
            every { requestTemplate.body() } returns ByteArray(0)

            When("requestInterceptor가 호출될 때") {
                val interceptor: RequestInterceptor = feignRetry.requestInterceptor()
                interceptor.apply(requestTemplate)

                Then("Body를 변경하지 않아야 한다.") {
                    verify(exactly = 0) { requestTemplate.body("{}") }
                }
            }
        }

        And("POST 요청과 빈 Body를 가진 RequestTemplate가 주어지면") {
            val requestTemplate: RequestTemplate = mockk<RequestTemplate>(relaxed = true)
            every { requestTemplate.method() } returns "POST"
            every { requestTemplate.body() } returns ByteArray(0)

            When("requestInterceptor가 호출될 때") {
                val interceptor: RequestInterceptor = feignRetry.requestInterceptor()
                interceptor.apply(requestTemplate)

                Then("Body가 '{}'로 설정되어야 한다.") {
                    verify { requestTemplate.body("{}") }
                }
            }
        }
    }
})
