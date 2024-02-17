package kr.co.baseapi.entity

import jakarta.persistence.*
import kr.co.baseapi.entity.converter.GenderTypeConverter
import kr.co.baseapi.enums.GenderType
import org.hibernate.type.YesNoConverter

@Entity
@Table(schema = "dev", name = "MEMBER")
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    var memberId: Long? = null,

    @Column(name = "NAME")
    var name: String? = null,

    @Column(name = "AGE")
    var age: Int? = null,

    @Convert(converter = GenderTypeConverter::class)
    @Column(name = "GENDER")
    var gender: GenderType? = null,

    @Convert(converter = YesNoConverter::class)
    @Column(name = "IS_AUTH")
    var isAuth: Boolean? = null

) : BaseEntity() {

    companion object {
        fun of(name: String?, age: Int?, gender: GenderType?, isAuth: Boolean?): Member =
            Member(name = name, age = age, gender = gender, isAuth = isAuth)
    }
}
