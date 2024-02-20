package kr.co.baseapi.service

import kr.co.baseapi.entity.Member
import kr.co.baseapi.enums.GenderType
import kr.co.baseapi.repository.MemberRepository
import kr.co.baseapi.repository.MemberRepositorySupport
import mu.KotlinLogging
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

private val log = KotlinLogging.logger {}

@Transactional
@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val memberRepositorySupport: MemberRepositorySupport
) : MemberService {

    override fun saveMember(): Boolean {
//        val member: Member = Member.of("kim", 35, GenderType.MAN, true)
//        memberRepository.save(member)
//
//        Thread.sleep(10_000L)
//        member.name = "park"

//        val member: Member? = memberRepository.findById(3L).getOrNull()
//        member?.addAge(1)

//        val member: Member? = memberRepositorySupport.findByIdDsl(10).getOrNull()
//        member?.addAge(1)

        val pageable: Pageable = PageRequest.of(0, 10)
        val pageImpl: PageImpl<Member> = memberRepositorySupport.findDslPage(pageable)

        log.info { "pageImpl.totalPages : ${pageImpl.totalPages} ${pageImpl.number} ${pageImpl.size} ${pageImpl.content.joinToString { it.toString() }}" }

        return true
    }
}