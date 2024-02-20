package kr.co.baseapi.repository

import kr.co.baseapi.entity.Member
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.Optional

interface MemberRepositorySupport {
    fun findByIdDsl(id: Long): Optional<Member>

    fun findDslPage(pageable: Pageable): PageImpl<Member>
}