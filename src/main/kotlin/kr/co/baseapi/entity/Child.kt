package kr.co.baseapi.entity

import jakarta.persistence.*

@Entity
@Table(schema = "dev", name = "CHILD")
class Child private constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHILD_ID")
    var childId: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    var member: Member? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FAMILY_ID")
    var family: Family? = null

) : BaseEntity() {

    companion object {
        fun of(member: Member?, family: Family?): Child = Child(member = member, family = family)
    }
}
