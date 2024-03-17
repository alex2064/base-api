package kr.co.baseapi.controller

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import kr.co.baseapi.common.handler.dto.BaseResponse
import kr.co.baseapi.dto.ExamParam
import kr.co.baseapi.dto.ExamResult
import kr.co.baseapi.enums.GenderType
import kr.co.baseapi.service.ExamService
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.time.LocalDate

@WebMvcTest(ExamController::class)
class ExamControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    @MockkBean private val examService: ExamService
) : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    Given("[/exam/example] 조회할 Example의 id가 주어지면") {
        val path: String = "/exam/example"
        val id: Long = 1L
        val name: String = "kim"
        val age: Int = 35
        val amount: Long = 10_000L
        val height: BigDecimal = BigDecimal(20_000L)
        val gender: GenderType = GenderType.MAN
        val isAuth: Boolean = true
        val baseDate: LocalDate = LocalDate.now()
        every { examService.findExample(id) } returns
                ExamResult(id, name, age, amount, height, gender, isAuth, baseDate)

        When("Request의 결과를 확인할 때") {
            val mvcResult: MvcResult = mockMvc.perform(
                get(path)
                    .param("id", id.toString())
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()

            Then("Response의 id가 일치해야 한다.") {
                val body: String = mvcResult.response.contentAsString
                val result: BaseResponse<ExamResult> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<ExamResult>>() {})
                result.isSuccess shouldBe true
                result.method shouldBe "GET"
                result.path shouldBe path
                result.data.id shouldBe id
            }
        }
    }

    Given("[Post][/exam/example] 저장할 Example의 DTO가 주어지면") {
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

        When("Request의 결과를 확인할 때") {
            val mvcResult: MvcResult = mockMvc.perform(
                post(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(param))
            ).andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()

            Then("Response의 data가 true여야 한다.") {
                val body: String = mvcResult.response.contentAsString
                val result: BaseResponse<Boolean> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<Boolean>>() {})
                result.isSuccess shouldBe true
                result.method shouldBe "POST"
                result.path shouldBe path
                result.data shouldBe true
            }
        }
    }

    Given("[Put][/exam/example/{id}] 수정할 Example의 id와 DTO가 주어지면") {
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

        When("Request의 결과를 확인할 때") {
            val mvcResult: MvcResult = mockMvc.perform(
                put(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(param))
            ).andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()

            Then("Response의 data가 true여야 한다.") {
                val body: String = mvcResult.response.contentAsString
                val result: BaseResponse<Boolean> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<Boolean>>() {})
                result.isSuccess shouldBe true
                result.method shouldBe "PUT"
                result.path shouldBe path
                result.data shouldBe true
            }
        }
    }

    Given("[Delete][/exam/example/{id}] 삭제할 Example의 id가 주어지면") {
        val id: Long = 1L
        val path: String = "/exam/example/$id"
        every { examService.deleteExample(id) } returns true

        When("Request의 결과를 확인할 때") {
            val mvcResult: MvcResult = mockMvc.perform(
                delete(path)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()

            Then("Response의 data가 true여야 한다.") {
                val body: String = mvcResult.response.contentAsString
                val result: BaseResponse<Boolean> =
                    objectMapper.readValue(body, object : TypeReference<BaseResponse<Boolean>>() {})
                result.isSuccess shouldBe true
                result.method shouldBe "DELETE"
                result.path shouldBe path
                result.data shouldBe true
            }
        }
    }
})
