package kr.co.baseapi.service

import kr.co.baseapi.common.property.KeyProperties
import kr.co.baseapi.dto.*
import kr.co.baseapi.entity.Example
import kr.co.baseapi.repository.ExampleRepository
import kr.co.baseapi.repository.ExampleRepositorySupport
import mu.KotlinLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val log = KotlinLogging.logger {}

/**
 * Service 생성
 * 1. 구현할 Service Interface 와 순서 일치
 * 2. class 단위의 @Transactional 기본 사용
 * 3. 메서드 단위로 트랜잭션이 필요한 경우 서비스를 따로 빼서 별도로 사용
 * 4. 트랜잭션을 따로 빼는 경우 대상을 추출하는 트랜잭션이 걸리지 않은 class 는 {Target~ServiceImpl}로 생성
 * 5. 메서드 명 사용
 *      1) {find~} : 조회
 *      2) {save~} : 저장
 *      3) {delete~} : 삭제
 *      4) {proc~} : 여러 작업 처리
 *      5) {~Cache} : @Cacheable, @CacheEvict
 *      6) {~CachePut} : CachePut
 */
@Transactional
@Service
class ExampleServiceImpl(
    private val exampleRepository: ExampleRepository,
    private val exampleRepositorySupport: ExampleRepositorySupport,
    private val keyProperties: KeyProperties
) : ExampleService {

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

    override fun saveExampleInfo(id: Long, param: ExamParam): Boolean {
        val example: Example = exampleRepository.findById(id).orElseThrow()
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

    override fun findExampleDslList(param: ExamPageParam): List<ExamResult> {
        val result: List<ExamResult> = exampleRepositorySupport.findByNameList(param)

        return result
    }

    override fun findExampleDslPage(param: ExamPageParam): PageResult<ExamResult> {
        val page: Page<ExamResult> = exampleRepositorySupport.findByNamePage(param)
        val result: PageResult<ExamResult> = PageResult.pageOf(page)

        return result
    }

    override fun findKey(): String {
        return keyProperties.apiKey
    }

    @Cacheable(cacheNames = ["ExampleCache"], key = "#id")
    override fun findExampleCache(id: Long): ExamResult {
        val example: Example = exampleRepository.findById(id).orElseThrow()
        val result: ExamResult = ExamResult.exampleOf(example)

        return result
    }

    @CachePut(cacheNames = ["ExampleCache"], key = "#id")
    override fun findExampleCachePut(id: Long): ExamResult {
        val example: Example = exampleRepository.findById(id).orElseThrow()
        val result: ExamResult = ExamResult.exampleOf(example)

        return result
    }

    @CacheEvict(cacheNames = ["ExampleCache"], key = "#id")
    override fun deleteExampleCache(id: Long): Boolean {
        val example: Example = exampleRepository.findById(id).orElseThrow()
        exampleRepository.delete(example)

        return true
    }
}