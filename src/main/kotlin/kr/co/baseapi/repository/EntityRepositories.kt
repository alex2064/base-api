package kr.co.baseapi.repository

import kr.co.baseapi.entity.Child
import kr.co.baseapi.entity.Family
import kr.co.baseapi.entity.Member
import kr.co.baseapi.entity.Parent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface MemberRepository : JpaRepository<Member, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Member set isAuth = :isAuth where memberId in (:ids)")
    fun saveIsAuth(isAuth: Boolean, ids: List<Long>)
}

interface FamilyRepository : JpaRepository<Family, Long>

interface ParentRepository : JpaRepository<Parent, Long>

interface ChildRepository : JpaRepository<Child, Long>