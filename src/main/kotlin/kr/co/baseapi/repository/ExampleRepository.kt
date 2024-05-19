package kr.co.baseapi.repository

import jakarta.persistence.LockModeType
import kr.co.baseapi.entity.Example
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

/**
 * GUIDE
 * Repository 생성
 * 1. JpaRepository 구현
 * 2. 하나의 kt 파일 안에 여러 엔티티들의 Repository 를 작성
 * 3. select 의 return type 은 Optional<>, List<> 로 통일
 * 4. bulk 연산의 경우 바로 JPQL 로 DB call 을 하니 영속성 컨텍스트와 차이 발생을 없애기위해 @Modifying(clearAutomatically = true) 사용
 * 5. select Lock 이 필요한 경우 특정 data 에 대해 @Lock(LockModeType.PESSIMISTIC_WRITE) 사용
 * 6. 메서드 명 사용
 *      1) {save~} : insert, update
 *      2) {delete~} : delete
 *      3) {~ForLock} : Lock 호출
 */
interface ExampleRepository : JpaRepository<Example, Long> {

    fun findByName(name: String): List<Example>

    @Modifying(clearAutomatically = true)
    @Query("update Example set isAuth = :isAuth where id in (:ids)")
    fun saveIsAuth(isAuth: Boolean, ids: List<Long>)

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from Example e where e.id = :id")
    fun findByIdForLock(id: Long): Optional<Example>
}