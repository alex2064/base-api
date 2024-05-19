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
 * GUIDE
 * RepositorySupport 생성
 * 1. JPAQueryFactory 주입받아 사용
 * 2. 조회 조건이 많은 SQL, Join, Page 처리가 필요한 select 만 QueryDSL 로 사용
 * 3. select 의 return type 은 Optional<>, List<>, PageImpl<> 로 통일
 * 4. override 된 메서드는 Interface 에 간략한 주석, 내부 메서드는 해당 메서드 위에 간단한 주석
 * 5. 메서드 명 사용
 *      1) {~Page} : paging 처리
 *      2) JPA Query Methods 와 최대한 비슷하게 함수명 사용({find~}, {By~})
 * 6. paging 처리 순서
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

    override fun findByNameList(param: ExamPageParam): List<ExamResult> {
        val example: QExample = QExample.example

        val booleanBuilder: BooleanBuilder = BooleanBuilder()
            .and(example.name.contains(param.name))
            .and(example.isAuth.eq(true))

        return jpaQueryFactory
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
            .from(example)
            .where(booleanBuilder)
            .orderBy(example.id.asc())
            .fetch()
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
        val query: JPAQuery<Example> = jpaQueryFactory
            .selectFrom(example)
            .where(booleanBuilder)
            .orderBy(example.id.asc())

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