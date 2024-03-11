package kr.co.baseapi.common.feign

import feign.FeignException
import feign.Request
import feign.Response
import feign.RetryableException
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldNotBeInstanceOf
import org.springframework.http.HttpStatus
import java.lang.Exception
import java.nio.charset.StandardCharsets

class RetryErrorDecoderTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    Given("RetryErrorDecoder가 주어지면") {
        val decoder: RetryErrorDecoder = RetryErrorDecoder()

        When("5xx 서버 에러를 나타내는 Response가 주어졌을 때") {
            val serverErrorResponse: Response = Response.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value()) // 500번대 error
                .reason("Server Error")
                .request(Request.create(Request.HttpMethod.GET, "", emptyMap(), Request.Body.empty(), null))
                .body("Server error", StandardCharsets.UTF_8)
                .build()

            val exception: Exception = decoder.decode("methodKey", serverErrorResponse)

            Then("RetryableException을 반환해야 한다.") {
                exception.shouldBeInstanceOf<RetryableException>()
            }
        }

        When("4xx 클라이언트 에러를 나타내는 Response가 주어졌을 때") {
            val clientErrorResponse: Response = Response.builder()
                .status(HttpStatus.BAD_REQUEST.value()) // 400번대 error
                .reason("Client Error")
                .request(Request.create(Request.HttpMethod.GET, "", emptyMap(), Request.Body.empty(), null))
                .body("Client error", StandardCharsets.UTF_8)
                .build()

            val exception: Exception = decoder.decode("methodKey", clientErrorResponse)

            Then("FeignException을 반환해야 한다.") {
                exception.shouldNotBeInstanceOf<RetryableException>()
                exception.shouldBeInstanceOf<FeignException>()
            }
        }
    }
})
