package kr.co.baseapi.service

import kr.co.baseapi.dto.*
import kr.co.baseapi.entity.Example
import kr.co.baseapi.repository.ExampleRepository
import kr.co.baseapi.repository.ExampleRepositorySupport
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val log = KotlinLogging.logger {}

/**
 * Service 생성
 * 1. class 단위의 @Transactional 기본 사용
 * 2. 구현할 Service Interface와 순서 일치
 * 3. 조회는 {find~}, 저장은 {save~}, 삭제는 {delete~}, 여러 작업 처리는 {proc~} 로 메소드명 사용
 */
@Transactional
@Service
class ExamServiceImpl(
    private val exampleRepository: ExampleRepository,
    private val exampleRepositorySupport: ExampleRepositorySupport
) : ExamService {

    override fun findExample(id: Long): ExamResult {
        val example: Example = exampleRepository.findById(id).orElseThrow()
        val result: ExamResult = ExamResult.exampleOf(example)

        return result
    }

    override fun saveExample(param: ExamParam): Boolean {
        val example: Example = Example.of(
            name = param.name,
            age = param.age,
            amount = param.amount,
            height = param.height,
            gender = param.gender,
            isAuth = param.isAuth,
            baseDate = param.baseDate
        )

        exampleRepository.save(example)
        return true
    }

    override fun saveExampleInfo(param: ExamParam): Boolean {
        val example: Example = exampleRepository.findById(param.id!!).orElseThrow()
        example.updateInfo(
            name = param.name,
            age = param.age,
            amount = param.amount,
            height = param.height,
            gender = param.gender,
            isAuth = param.isAuth,
            baseDate = param.baseDate
        )

        return true
    }

    override fun deleteExample(id: Long): Boolean {
        val example: Example = exampleRepository.findById(id).orElseThrow()
        exampleRepository.delete(example)

        return true
    }

    override fun saveIsAuth(param: ExamIsAuthParam): Boolean {
        exampleRepository.saveIsAuth(param.isAuth!!, param.ids!!)

        return true
    }

    override fun findExampleForLock(id: Long): ExamResult {
        val example: Example = exampleRepository.findByIdForLock(id).orElseThrow()
        val result: ExamResult = ExamResult.exampleOf(example)

        return result
    }

    override fun findExampleDsl(id: Long): ExamResult {
        val example: Example = exampleRepositorySupport.findById(id).orElseThrow()
        val result: ExamResult = ExamResult.exampleOf(example)

        return result
    }

    override fun findExampleDslPage(param: ExamPageParam): PageResult<Example> {
        val page: Page<Example> = exampleRepositorySupport.findByNamePage(param)
        val result: PageResult<Example> = PageResult.pageOf(page)

        return result
    }
}