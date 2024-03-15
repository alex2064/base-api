package kr.co.baseapi.controller

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import kr.co.baseapi.common.handler.dto.BaseResponse
import kr.co.baseapi.dto.ExamResult
import kr.co.baseapi.enums.GenderType
import kr.co.baseapi.service.ExamService
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
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
})
