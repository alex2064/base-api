package kr.co.baseapi.service

import kr.co.baseapi.entity.Member
import kr.co.baseapi.enums.GenderType
import kr.co.baseapi.repository.MemberRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

private val log = KotlinLogging.logger {}

@Transactional
@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository
) : MemberService {

    override fun saveMember(): Boolean {
//        val member: Member = Member.of("kim", 35, GenderType.MAN, true)
//        memberRepository.save(member)
//
//        Thread.sleep(10_000L)
//        member.name = "park"

        val member: Member? = memberRepository.findById(3L).getOrNull()
        member?.addAge(1)

        return true
    }
}