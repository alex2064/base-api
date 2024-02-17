package kr.co.baseapi.repository

import kr.co.baseapi.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>