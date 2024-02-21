package kr.co.baseapi.repository

import kr.co.baseapi.entity.Child
import kr.co.baseapi.entity.Family
import kr.co.baseapi.entity.Member
import kr.co.baseapi.entity.Parent
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>

interface FamilyRepository : JpaRepository<Family, Long>

interface ParentRepository : JpaRepository<Parent, Long>

interface ChildRepository : JpaRepository<Child, Long>