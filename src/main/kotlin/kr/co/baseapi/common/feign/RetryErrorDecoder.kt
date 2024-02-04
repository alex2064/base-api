package kr.co.baseapi.common.feign

import feign.FeignException
import feign.Response
import feign.RetryableException
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus
import java.lang.Exception

class RetryErrorDecoder : ErrorDecoder {

    override fun decode(methodKey: String?, response: Response?): Exception {
        val feignException: FeignException = FeignException.errorStatus(methodKey, response)
        val httpStatus: Int = response?.status() ?: return feignException

        return if (HttpStatus.valueOf(httpStatus).is5xxServerError) {
            RetryableException(
                httpStatus,
                feignException.message,
                response.request().httpMethod(),
                feignException,
                RETRY_AFTER,
                response.request()
            )
        } else {
            feignException
        }
    }

    companion object {
        private const val RETRY_AFTER: Long = 10_000L
    }
}