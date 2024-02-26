package kr.co.baseapi.entity

import jakarta.persistence.*

@Entity
@Table(schema = "dev", name = "PARENT")
class Parent private constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARENT_ID")
    var parentId: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    var member: Member? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FAMILY_ID")
    var family: Family? = null

) : BaseEntity() {

    companion object {
        fun of(member: Member?, family: Family?): Parent = Parent(member = member, family = family)
    }
}
