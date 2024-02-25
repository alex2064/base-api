package kr.co.baseapi.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import kr.co.baseapi.entity.Member
import kr.co.baseapi.entity.QMember
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MemberRepositorySupportImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : MemberRepositorySupport {

    override fun findByIdDsl(id: Long): Optional<Member> {
        val member: QMember = QMember.member

        return Optional.ofNullable(
            jpaQueryFactory
                .selectFrom(member)
                .where(member.memberId.eq(id))
                .fetchOne()
        )
    }

    override fun findDslPage(pageable: Pageable): PageImpl<Member> {
        // entity
        val member: QMember = QMember.member

        // 페이지 처리
        // pageable 생성

        // 공통 query
        val booleanBuilder: BooleanBuilder = BooleanBuilder()
            .and(member.isAuth.eq(true))

        val query: JPAQuery<Member> = jpaQueryFactory.selectFrom(member).where(booleanBuilder)

        // content
        val content: MutableList<Member> =
            query.clone()
                .select(member)
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()

        // content total 갯수
        val total: Long = query.clone().select(member.memberId.count()).fetchOne() ?: 0L

        return PageImpl<Member>(content, pageable, total)

    }
}