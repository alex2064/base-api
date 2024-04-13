package kr.co.baseapi.question.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime


@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(schema = "dev", name = "TEMP")
class Temp protected constructor() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null
        protected set

    @Column(name = "IN_DATA")
    var inData: Long? = null
        protected set

    @Column(name = "UP_DATA")
    var upData: Long? = null

    @CreatedDate
    @Column(name = "CREATE_DATE")
    protected var createDate: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "UPDATE_DATE")
    protected var updateDate: LocalDateTime? = null


    companion object {
        fun of(inData: Long?): Temp = Temp().apply { this.inData = inData }
    }
}