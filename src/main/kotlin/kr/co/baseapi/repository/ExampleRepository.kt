package kr.co.baseapi.repository

import jakarta.persistence.LockModeType
import kr.co.baseapi.entity.Example
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

/**
 * Repository 생성
 * 1. JpaRepository 구현
 * 2. 하나의 kt 파일 안에 여러 엔티티들의 Repository를 작성
 * 3. bulk연산의 경우 바로 JPQL로 DB call을 하니 영속성 컨텍스트와 차이 발생을 없애기위해 @Modifying(clearAutomatically = true) 사용
 * 4. insert, update의 경우 {save~}, delete의 경우 {delete~} 로 함수명 사용
 * 4. select Lock이 필요한 경우 특정 data에 대해 @Lock(LockModeType.PESSIMISTIC_WRITE) 사용
 * 5. Lock 호출은 {~ForLock} 으로 함수명 사용
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