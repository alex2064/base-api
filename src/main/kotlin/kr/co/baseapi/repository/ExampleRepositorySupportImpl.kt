package kr.co.baseapi.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import kr.co.baseapi.dto.ExamPageParam
import kr.co.baseapi.entity.Example
import kr.co.baseapi.entity.QExample
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ExampleRepositorySupportImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : ExampleRepositorySupport {

    override fun findById(id: Long): Optional<Example> {
        val example: QExample = QExample.example

        return Optional.ofNullable(
            jpaQueryFactory
                .selectFrom(example)
                .where(example.id.eq(id))
                .fetchOne()
        )
    }

    override fun findByNamePage(param: ExamPageParam): PageImpl<Example> {
        // entity
        val example: QExample = QExample.example

        // pageable
        val pageable: Pageable = param.pageable!!

        // where 조건
        val booleanBuilder: BooleanBuilder = BooleanBuilder()
            .and(example.name.contains(param.name))
            .and(example.isAuth.eq(true))

        // 공통 query
        val query: JPAQuery<Example> = jpaQueryFactory.selectFrom(example).where(booleanBuilder)

        // content
        val content: MutableList<Example> =
            query.clone()
                .select(example)
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()

        // total
        val total: Long = query.clone().select(example.id.count()).fetchOne() ?: 0L

        return PageImpl<Example>(content, pageable, total)
    }
}