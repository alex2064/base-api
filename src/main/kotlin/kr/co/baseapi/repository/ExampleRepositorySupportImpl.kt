package kr.co.baseapi.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import kr.co.baseapi.dto.ExamPageParam
import kr.co.baseapi.dto.ExamResult
import kr.co.baseapi.entity.Example
import kr.co.baseapi.entity.QExample
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.*


/**
 * RepositorySupport 생성
 * 1. JPAQueryFactory 주입받아 사용
 * 2. join이 필요한 select만 QueryDSL로 사용
 * 3. JPA Query Methods와 최대한 비슷하게 함수명 사용({find~}, {By~})
 * 4. paging 처리는 {~Page} 로 함수명 사용
 * 5. paging 처리 순서
 *      1. PageParam() 상속받은 DTO 파라미터로 받기
 *      2. 사용할 entity 나열
 *      3. pageable 추출
 *      4. 공통 query 생성(BooleanBuilder, JPAQuery)
 *      5. content query fetch
 *      6. total query fetchOne
 *      7. PageImpl 생성 / return
 */
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

    override fun findByNamePage(param: ExamPageParam): PageImpl<ExamResult> {
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
        val content: MutableList<ExamResult> =
            query.clone()
                .select(
                    Projections.constructor(
                        ExamResult::class.java,
                        example.id,
                        example.name,
                        example.age,
                        example.amount,
                        example.height,
                        example.gender,
                        example.isAuth,
                        example.baseDate
                    )
                )
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()

        // total
        val total: Long = query.clone().select(example.id.count()).fetchOne() ?: 0L

        return PageImpl<ExamResult>(content, pageable, total)
    }
}