package kr.co.baseapi.repository

import jakarta.persistence.LockModeType
import kr.co.baseapi.entity.Child
import kr.co.baseapi.entity.Family
import kr.co.baseapi.entity.Member
import kr.co.baseapi.entity.Parent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface MemberRepository : JpaRepository<Member, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Member set isAuth = :isAuth where memberId in (:ids)")
    fun saveIsAuth(isAuth: Boolean, ids: List<Long>)

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from Member m where m.memberId = :id")
    fun findByIdForLock(id: Long): Optional<Member>
}

interface FamilyRepository : JpaRepository<Family, Long>

interface ParentRepository : JpaRepository<Parent, Long>

interface ChildRepository : JpaRepository<Child, Long>