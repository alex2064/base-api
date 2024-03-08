package kr.co.baseapi.repository

import jakarta.persistence.LockModeType
import kr.co.baseapi.entity.Example
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ExampleRepository : JpaRepository<Example, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Example set isAuth = :isAuth where id in (:ids)")
    fun saveIsAuth(isAuth: Boolean, ids: List<Long>)

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from Example e where e.id = :id")
    fun findByIdForLock(id: Long): Optional<Example>
}