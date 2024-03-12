package kr.co.baseapi.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "CREATE_DATE")
    protected var createDate: LocalDateTime? = null

    @CreatedBy
    @Column(name = "CREATE_BY")
    protected var createBy: Long? = null

    @LastModifiedDate
    @Column(name = "UPDATE_DATE")
    protected var updateDate: LocalDateTime? = null

    @LastModifiedBy
    @Column(name = "UPDATE_BY")
    protected var updateBy: Long? = null
}