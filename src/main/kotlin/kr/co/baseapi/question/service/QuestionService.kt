package kr.co.baseapi.question.service

interface QuestionService {

    /**
     * Q1. 트랜잭션과 영속성 컨텍스트
     *      SQL이 DB에 날아가는 시점, commit / rollback 시점, 최종적으로 DB에 데이터는 어떻게 저장되어 있는가요?
     */
    fun question1(): Boolean
    fun question1Sub(id: Long): Boolean

    /**
     * Q2. 트랜잭션과 영속성 컨텍스트
     *      SQL이 DB에 날아가는 시점, commit / rollback 시점, 최종적으로 DB에 데이터는 어떻게 저장되어 있는가요?
     */
    fun question2(): Boolean
    fun question2Sub(id: Long): Boolean

    /**
     * Q3. 트랜잭션과 영속성 컨텍스트
     *      SQL이 DB에 날아가는 시점, commit / rollback 시점, 최종적으로 DB에 데이터는 어떻게 저장되어 있는가요?
     */
    fun question3(): Boolean
    fun question3Sub(id: Long): Boolean

    /**
     * Q4. 트랜잭션과 영속성 컨텍스트
     *      SQL이 DB에 날아가는 시점, commit / rollback 시점, 최종적으로 DB에 데이터는 어떻게 저장되어 있는가요?
     */
    fun question4(): Boolean
    fun question4Sub(id: Long): Boolean

    /**
     * Q5. 트랜잭션과 영속성 컨텍스트
     *      해당 코드의 문제점이 있다면 어떻게 개선할 수 있을까요?
     */
    fun question5(): Boolean
    fun question5Sub(id: Long): Boolean

    /**
     * Q6. 트랜잭션과 영속성 컨텍스트
     *      SQL이 DB에 날아가는 시점, commit / rollback 시점, 최종적으로 DB에 데이터는 어떻게 저장되어 있는가요?
     */
    fun question6(): Boolean

    /**
     * Q7. 트랜잭션과 영속성 컨텍스트
     *      SQL이 DB에 날아가는 시점, commit / rollback 시점, 최종적으로 DB에 데이터는 어떻게 저장되어 있는가요?
     */
    fun question7(): Boolean

    /**
     * Q8. 트랜잭션과 영속성 컨텍스트
     *      SQL이 DB에 날아가는 시점, commit / rollback 시점, 최종적으로 DB에 데이터는 어떻게 저장되어 있는가요?
     */
    fun question8(): Boolean

    /**
     * Q9. 트랜잭션과 영속성 컨텍스트
     *      SQL이 DB에 날아가는 시점, commit / rollback 시점, 최종적으로 DB에 데이터는 어떻게 저장되어 있는가요?
     */
    fun question9(): Boolean

    /**
     * Q10. 트랜잭션과 영속성 컨텍스트
     *      해당 코드의 문제점이 있다면 어떻게 개선할 수 있을까요?
     */
    fun question10(): Boolean

    /**
     * Q11. 트랜잭션과 영속성 컨텍스트
     *      SQL은 어떻게 날아가나요?
     */
    fun question11(): Boolean

    /**
     * Q12. 트랜잭션과 영속성 컨텍스트
     *      SQL은 어떻게 날아가나요?
     */
    fun question12(): Boolean
}