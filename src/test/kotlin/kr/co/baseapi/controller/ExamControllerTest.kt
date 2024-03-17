package kr.co.baseapi.controller

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.maps.shouldContainKey
import io.kotest.matchers.shouldBe
import io.mockk.every
import kr.co.baseapi.common.handler.dto.BaseResponse
import kr.co.baseapi.dto.ExamParam
import kr.co.baseapi.dto.ExamResult
import kr.co.baseapi.dto.ExamVaildParam
import kr.co.baseapi.enums.GenderType
import kr.co.baseapi.service.ExamService
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@WebMvcTest(ExamController::class)
class ExamControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    @MockkBean private val examService: ExamService
) : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    Given("[GET][/exam/vaild] vaildation 검증할 값이 주어지면") {
        val path: String = "/exam/valid"
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val string: String = "string"
        val stringNotBlank: String = "string"
        val int: Int = 0
        val intNotNull: Int = 0
        val intPositive: Int = 1
        val intNotNullPositive: Int = 1
        val long: Long = 0
        val longNotNull: Long = 0
        val longPositive: Long = 1
        val longNotNullPositive: Long = 1
        val bigDecimal: BigDecimal = BigDecimal.ZERO
        val bigDecimalNotNull: BigDecimal = BigDecimal.ZERO
        val bigDecimalDecimalMin: BigDecimal = BigDecimal.ONE
        val bigDecimalNotNullDecimalMin: BigDecimal = BigDecimal.ONE
        val examType: GenderType = GenderType.MAN
        val examTypeEnumValid: GenderType = GenderType.MAN
        val localdate: LocalDate = LocalDate.of(2024, 1, 2)
        val localdateNotNull: LocalDate = LocalDate.of(2024, 1, 2)
        val emailEmailValid: String = "abcde@naver.com"
        val emailNotBlankEmailValid: String = "abcde@naver.com"

        When("정상적인 Request를 보낼 때") {
            val resultActions: ResultActions = mockMvc.perform(
                get(path)
                    .param("string", string)
                    .param("stringNotBlank", stringNotBlank)
                    .param("int", int.toString())
                    .param("intNotNull", intNotNull.toString())
                    .param("intPositive", intPositive.toString())
                    .param("intNotNullPositive", intNotNullPositive.toString())
                    .param("long", long.toString())
                    .param("longNotNull", longNotNull.toString())
                    .param("longPositive", longPositive.toString())
                    .param("longNotNullPositive", longNotNullPositive.toString())
                    .param("bigDecimal", bigDecimal.toString())
                    .param("bigDecimalNotNull", bigDecimalNotNull.toString())
                    .param("bigDecimalDecimalMin", bigDecimalDecimalMin.toString())
                    .param("bigDecimalNotNullDecimalMin", bigDecimalNotNullDecimalMin.toString())
                    .param("examType", examType.name)
                    .param("examTypeEnumValid", examTypeEnumValid.name)
                    .param("localdate", localdate.format(dateTimeFormatter))
                    .param("localdateNotNull", localdateNotNull.format(dateTimeFormatter))
                    .param("emailEmailValid", emailEmailValid)
                    .param("emailNotBlankEmailValid", emailNotBlankEmailValid)
                    .contentType(MediaType.APPLICATION_JSON)
            )

            Then("Response가 정상적으로 확인되어야 한다.") {
                val mvcResult: MvcResult = resultActions
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                val body: String = mvcResult.response.contentAsString
                val result: BaseResponse<ExamVaildParam> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<ExamVaildParam>>() {})

                result.isSuccess shouldBe true
                result.method shouldBe "GET"
                result.path shouldBe path
            }
        }

        When("비정상적인 Request를 보낼 때") {
            val resultActions: ResultActions = mockMvc.perform(
                get(path)
                    .param("intPositive", "0")
                    .param("intNotNullPositive", "0")
                    .param("longPositive", "0")
                    .param("longNotNullPositive", "0")
                    .param("bigDecimalDecimalMin", "-1")
                    .param("bigDecimalNotNullDecimalMin", "-1")
                    .param("emailNotBlankEmailValid", "abcde@")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Accept-Language", "ko")
            )

            Then("Response에 vaildation에 걸린 필드가 확인되어야 한다.") {
                val mvcResult: MvcResult = resultActions
                    .andExpect(status().isBadRequest)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                val body: String = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
                val result: BaseResponse<ProblemDetail> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<ProblemDetail>>() {})
                val fieldErrors: Map<String, String> =
                    (result.data.properties?.getOrDefault("fieldErrors", null) as? Map<String, String>) ?: mapOf()

                result.isSuccess shouldBe false
                result.method shouldBe "GET"
                result.path shouldBe path
                fieldErrors shouldContainKey "stringNotBlank"
                fieldErrors shouldContainKey "intNotNull"
                fieldErrors shouldContainKey "intPositive"
                fieldErrors shouldContainKey "intNotNullPositive"
                fieldErrors shouldContainKey "longNotNull"
                fieldErrors shouldContainKey "longPositive"
                fieldErrors shouldContainKey "longNotNullPositive"
                fieldErrors shouldContainKey "bigDecimalNotNull"
                fieldErrors shouldContainKey "bigDecimalDecimalMin"
                fieldErrors shouldContainKey "bigDecimalNotNullDecimalMin"
                fieldErrors shouldContainKey "examTypeEnumValid"
                fieldErrors shouldContainKey "localdateNotNull"
                fieldErrors shouldContainKey "emailNotBlankEmailValid"
            }
        }
    }

    Given("[POST][/exam/vaild] vaildation 검증할 값이 주어지면") {
        val path: String = "/exam/valid"
        val param: Map<String, String> = mutableMapOf<String, String>().apply {
            this["string"] = "string"
            this["stringNotBlank"] = "string"
            this["int"] = "0"
            this["intNotNull"] = "0"
            this["intPositive"] = "1"
            this["intNotNullPositive"] = "1"
            this["long"] = "0"
            this["longNotNull"] = "0"
            this["longPositive"] = "1"
            this["longNotNullPositive"] = "1"
            this["bigDecimal"] = "0"
            this["bigDecimalNotNull"] = "0"
            this["bigDecimalDecimalMin"] = "1"
            this["bigDecimalNotNullDecimalMin"] = "1"
            this["examType"] = "MAN"
            this["examTypeEnumValid"] = "MAN"
            this["localdate"] = "2024-01-02"
            this["localdateNotNull"] = "2024-01-02"
            this["emailEmailValid"] = "abcde@naver.com"
            this["emailNotBlankEmailValid"] = "abcde@naver.com"
        }

        val badParam: Map<String, String> = mutableMapOf<String, String>().apply {
            this["intPositive"] = "0"
            this["intNotNullPositive"] = "0"
            this["longPositive"] = "0"
            this["longNotNullPositive"] = "0"
            this["bigDecimalDecimalMin"] = "-1"
            this["bigDecimalNotNullDecimalMin"] = "-1"
            this["emailEmailValid"] = "abcde@"
        }

        When("정상적인 Request를 보낼 때") {
            val resultActions: ResultActions = mockMvc.perform(
                post(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(param))
            )

            Then("Response가 정상적으로 확인되어야 한다.") {
                val mvcResult: MvcResult = resultActions
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                val body: String = mvcResult.response.contentAsString
                val result: BaseResponse<ExamVaildParam> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<ExamVaildParam>>() {})

                result.isSuccess shouldBe true
                result.method shouldBe "POST"
                result.path shouldBe path
            }
        }

        When("비정상적인 Request를 보낼 때") {
            val resultActions: ResultActions = mockMvc.perform(
                post(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Accept-Language", "ko")
                    .content(objectMapper.writeValueAsString(badParam))
            )

            Then("Response에 vaildation에 걸린 필드가 확인되어야 한다.") {
                val mvcResult: MvcResult = resultActions
                    .andExpect(status().isBadRequest)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                val body: String = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
                val result: BaseResponse<ProblemDetail> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<ProblemDetail>>() {})
                val fieldErrors: Map<String, String> =
                    (result.data.properties?.getOrDefault("fieldErrors", null) as? Map<String, String>) ?: mapOf()

                result.isSuccess shouldBe false
                result.method shouldBe "POST"
                result.path shouldBe path
                fieldErrors shouldContainKey "stringNotBlank"
                fieldErrors shouldContainKey "intNotNull"
                fieldErrors shouldContainKey "intPositive"
                fieldErrors shouldContainKey "intNotNullPositive"
                fieldErrors shouldContainKey "longNotNull"
                fieldErrors shouldContainKey "longPositive"
                fieldErrors shouldContainKey "longNotNullPositive"
                fieldErrors shouldContainKey "bigDecimalNotNull"
                fieldErrors shouldContainKey "bigDecimalDecimalMin"
                fieldErrors shouldContainKey "bigDecimalNotNullDecimalMin"
                fieldErrors shouldContainKey "examTypeEnumValid"
                fieldErrors shouldContainKey "localdateNotNull"
                fieldErrors shouldContainKey "emailNotBlankEmailValid"
            }
        }
    }

    Given("[GET][/exam/example] 조회할 Example의 id가 주어지면") {
        val path: String = "/exam/example"
        val id: Long = 1L
        every { examService.findExample(id) } returns
                ExamResult(id, null, null, null, null, null, null, null)

        When("정상적인 Request를 보낼 때") {
            val resultActions: ResultActions = mockMvc.perform(
                get(path)
                    .param("id", id.toString())
                    .contentType(MediaType.APPLICATION_JSON)
            )

            Then("Response의 id가 일치해야 한다.") {
                val mvcResult: MvcResult = resultActions
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                val body: String = mvcResult.response.contentAsString
                val result: BaseResponse<ExamResult> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<ExamResult>>() {})

                result.isSuccess shouldBe true
                result.method shouldBe "GET"
                result.path shouldBe path
                result.data.id shouldBe id
            }
        }

        When("비정상적인 Request를 보낼 때") {
            val resultActions: ResultActions = mockMvc.perform(
                get(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Accept-Language", "ko")
            )

            Then("Response에 vaildation에 걸린 필드가 확인되어야 한다.") {
                val mvcResult: MvcResult = resultActions
                    .andExpect(status().isBadRequest)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                val body: String = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
                val result: BaseResponse<ProblemDetail> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<ProblemDetail>>() {})
                val fieldErrors: Map<String, String> =
                    (result.data.properties?.getOrDefault("fieldErrors", null) as? Map<String, String>) ?: mapOf()

                result.isSuccess shouldBe false
                result.method shouldBe "GET"
                result.path shouldBe path
                fieldErrors shouldContainKey "id"
            }
        }
    }

    Given("[POST][/exam/example] 저장할 Example의 DTO가 주어지면") {
        val path: String = "/exam/example"
        val name: String = "kim"
        val age: Int = 35
        val amount: Long = 10_000L
        val height: BigDecimal = BigDecimal(20_000L)
        val gender: GenderType = GenderType.MAN
        val isAuth: Boolean = true
        val baseDate: LocalDate = LocalDate.now()
        val param: ExamParam = ExamParam(name, age, amount, height, gender, isAuth, baseDate)
        every { examService.saveExample(param) } returns true

        When("정상적인 Request를 보낼 때") {
            val resultActions: ResultActions = mockMvc.perform(
                post(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(param))
            )

            Then("Response의 data가 true여야 한다.") {
                val mvcResult: MvcResult = resultActions
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                val body: String = mvcResult.response.contentAsString
                val result: BaseResponse<Boolean> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<Boolean>>() {})

                result.isSuccess shouldBe true
                result.method shouldBe "POST"
                result.path shouldBe path
                result.data shouldBe true
            }
        }

        When("비정상적인 Request를 보낼 때") {
            val badParam: ExamParam = ExamParam(null, null, null, null, null, null, null)
            val resultActions: ResultActions = mockMvc.perform(
                post(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Accept-Language", "ko")
                    .content(objectMapper.writeValueAsString(badParam))
            )

            Then("Response에 vaildation에 걸린 필드가 확인되어야 한다.") {
                val mvcResult: MvcResult = resultActions
                    .andExpect(status().isBadRequest)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                val body: String = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
                val result: BaseResponse<ProblemDetail> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<ProblemDetail>>() {})
                val fieldErrors: Map<String, String> =
                    (result.data.properties?.getOrDefault("fieldErrors", null) as? Map<String, String>) ?: mapOf()

                result.isSuccess shouldBe false
                result.method shouldBe "POST"
                result.path shouldBe path
                fieldErrors shouldContainKey "name"
                fieldErrors shouldContainKey "age"
                fieldErrors shouldContainKey "amount"
                fieldErrors shouldContainKey "height"
                fieldErrors shouldContainKey "gender"
                fieldErrors shouldContainKey "isAuth"
                fieldErrors shouldContainKey "baseDate"
            }
        }
    }

    Given("[PUT][/exam/example/{id}] 수정할 Example의 id와 DTO가 주어지면") {
        val id: Long = 1L
        val path: String = "/exam/example/$id"
        val name: String = "kim"
        val age: Int = 35
        val amount: Long = 10_000L
        val height: BigDecimal = BigDecimal(20_000L)
        val gender: GenderType = GenderType.MAN
        val isAuth: Boolean = true
        val baseDate: LocalDate = LocalDate.now()
        val param: ExamParam = ExamParam(name, age, amount, height, gender, isAuth, baseDate)
        every { examService.saveExampleInfo(id, param) } returns true

        When("정상적인 Request를 보낼 때") {
            val resultActions: ResultActions = mockMvc.perform(
                put(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(param))
            )

            Then("Response의 data가 true여야 한다.") {
                val mvcResult: MvcResult = resultActions
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                val body: String = mvcResult.response.contentAsString
                val result: BaseResponse<Boolean> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<Boolean>>() {})

                result.isSuccess shouldBe true
                result.method shouldBe "PUT"
                result.path shouldBe path
                result.data shouldBe true
            }
        }

        When("비정상적인 Request(id)를 보낼 때") {
            val badPath: String = "/exam/example/0"
            val resultActions: ResultActions = mockMvc.perform(
                put(badPath)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Accept-Language", "ko")
                    .content(objectMapper.writeValueAsString(param))
            )

            Then("Response에 id 문제가 확인되어야 한다.") {
                val mvcResult: MvcResult = resultActions
                    .andExpect(status().isInternalServerError)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                val body: String = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
                val result: BaseResponse<ProblemDetail> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<ProblemDetail>>() {})

                result.isSuccess shouldBe false
                result.method shouldBe "PUT"
                result.path shouldBe badPath
                result.data.detail shouldBe "id는 0보다 커야합니다."
            }
        }

        When("비정상적인 Request(DTO)를 보낼 때") {
            val badParam: ExamParam = ExamParam(null, null, null, null, null, null, null)
            val resultActions: ResultActions = mockMvc.perform(
                put(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Accept-Language", "ko")
                    .content(objectMapper.writeValueAsString(badParam))
            )

            Then("Response에 vaildation에 걸린 필드가 확인되어야 한다.") {
                val mvcResult: MvcResult = resultActions
                    .andExpect(status().isBadRequest)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                val body: String = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
                val result: BaseResponse<ProblemDetail> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<ProblemDetail>>() {})
                val fieldErrors: Map<String, String> =
                    (result.data.properties?.getOrDefault("fieldErrors", null) as? Map<String, String>) ?: mapOf()

                result.isSuccess shouldBe false
                result.method shouldBe "PUT"
                result.path shouldBe path
                fieldErrors shouldContainKey "name"
                fieldErrors shouldContainKey "age"
                fieldErrors shouldContainKey "amount"
                fieldErrors shouldContainKey "height"
                fieldErrors shouldContainKey "gender"
                fieldErrors shouldContainKey "isAuth"
                fieldErrors shouldContainKey "baseDate"
            }
        }
    }

    Given("[DELETE][/exam/example/{id}] 삭제할 Example의 id가 주어지면") {
        val id: Long = 1L
        val path: String = "/exam/example/$id"
        every { examService.deleteExample(id) } returns true

        When("정상적인 Request를 보낼 때") {
            val resultActions: ResultActions = mockMvc.perform(
                delete(path)
                    .contentType(MediaType.APPLICATION_JSON)
            )

            Then("Response의 data가 true여야 한다.") {
                val mvcResult: MvcResult = resultActions
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                val body: String = mvcResult.response.contentAsString
                val result: BaseResponse<Boolean> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<Boolean>>() {})

                result.isSuccess shouldBe true
                result.method shouldBe "DELETE"
                result.path shouldBe path
                result.data shouldBe true
            }
        }

        When("비정상적인 Request(id)를 보낼 때") {
            val badPath: String = "/exam/example/0"
            val resultActions: ResultActions = mockMvc.perform(
                delete(badPath)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Accept-Language", "ko")
            )

            Then("Response에 id 문제가 확인되어야 한다.") {
                val mvcResult: MvcResult = resultActions
                    .andExpect(status().isInternalServerError)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                val body: String = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
                val result: BaseResponse<ProblemDetail> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<ProblemDetail>>() {})

                result.isSuccess shouldBe false
                result.method shouldBe "DELETE"
                result.path shouldBe badPath
                result.data.detail shouldBe "id는 0보다 커야합니다."
            }
        }
    }
})
