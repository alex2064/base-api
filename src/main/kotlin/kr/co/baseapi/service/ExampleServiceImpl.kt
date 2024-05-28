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
 * GUIDE
 * Service 생성
 * 1. 구현할 Service Interface 와 순서 일치
 * 2. class 단위의 @Transactional 기본 사용
 * 3. 메서드 단위로 트랜잭션이 필요한 경우 서비스를 따로 빼서 별도로 사용
 * 4. 트랜잭션을 따로 빼는 경우 대상을 추출하는 트랜잭션이 걸리지 않은 class 는 {Target~ServiceImpl}로 생성
 * 5. override 된 메서드는 Interface 에 간략한 주석, 내부 메서드는 해당 메서드 위에 간단한 주석
 * 6. Lock 이 필요한 경우 @RedisLock(Type = LockType.*) 사용
 * 7. 파라미터
 *      1) 외부 호출 : id(key)
 *          - 같은 트랜잭션 안이면 id 값으로 영속성 컨텍스트에서 가져오기
 *          - 다른 트랜잭션이면 select 쿼리 발생
 *          - 이렇게 해야 상황에 따라 자유롭게 사용하면서 재사용성이 좋음
 *      2) 내부 호출 : 값 -> entity -> DTO
 *          - 값 : 바로 사용하는 파라미터(Long, String...), 뭐가 사용되는지 명확해서 가독성이 좋음
 *          - 엔티티 : 엔티티의 값을 변경해야 하는 경우
 *          - DTO
 *          - 값이나 엔티티는 바로 보이지만 DTO 의 경우 정형화되어 있지 않으면 결국 그 안을 봐야 해서 가독성을 떨어뜨림
 * 8. 메서드 명 사용
 *      1) {find~} : 조회
 *      2) {save~} : 저장
 *      3) {delete~} : 삭제
 *      4) {proc~} : 여러 작업 처리
 *      5) {verify~} : 상태 확인
 *      6) {send~} : 전문 송신
 *      7) {check~} : 결과 확인
 *      8) {cancel~} : 취소
 *      9) {load~} : 사용할 엔티티 미리 적재
 *      10) {make~} : key, entity, Dto 생성 작업
 *      11) {extract~} : 인스턴스에서 값 추출
 *      12) {valid~} : 유효성 체크(return type : Unit, 유효성 체크 중 문제가 발생하면 throw Exception 으로 처리)
 *      13) {~Cache} : @Cacheable, @CacheEvict
 *      14) {~CachePut} : @CachePut
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
        example.modifyInfo(
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

    override fun findExampleDslList(param: ExamPageParam): PageResult<ExamResult, Nothing?> {
        val list: List<ExamResult> = exampleRepositorySupport.findByNameList(param)
        val result: PageResult<ExamResult, Nothing?> = PageResult.listOf(list)

        return result
    }

    override fun findExampleDslPage(param: ExamPageParam): PageResult<ExamResult, Nothing?> {
        val page: Page<ExamDto> = exampleRepositorySupport.findByNamePage(param)
        val result: PageResult<ExamResult, Nothing?> = PageResult.pageOf(page, ExamResult::examDtoOf)

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