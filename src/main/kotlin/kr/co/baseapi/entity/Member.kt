package kr.co.baseapi.entity

import jakarta.persistence.*
import kr.co.baseapi.entity.converter.GenderTypeConverter
import kr.co.baseapi.enums.GenderType
import org.hibernate.type.YesNoConverter
import java.time.LocalDate

@Entity
@Table(schema = "dev", name = "MEMBER")
class Member private constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    var memberId: Long? = null,

    @Column(name = "LOGIN_ID")
    var loginId: String? = null,

    @Column(name = "PASSWORD")
    var password: String? = null,

    @Column(name = "NAME")
    var name: String? = null,

    @Column(name = "BIRTH_DATE")
    var birthDate: LocalDate? = null,

    @Convert(converter = GenderTypeConverter::class)
    @Column(name = "GENDER")
    var gender: GenderType? = null,

    @Column(name = "EMAIL")
    var email: String? = null,

    @Convert(converter = YesNoConverter::class)
    @Column(name = "IS_AUTH")
    var isAuth: Boolean? = null

) : BaseEntity() {

    companion object {
        fun of(
            loginId: String?,
            password: String?,
            name: String?,
            birthDate: LocalDate?,
            gender: GenderType?,
            email: String?,
            isAuth: Boolean?
        ): Member =
            Member(
                loginId = loginId,
                password = password,
                name = name,
                birthDate = birthDate,
                gender = gender,
                email = email,
                isAuth = isAuth
            )
    }

    fun updatePassword(password: String?) {
        this.password = password
    }


    fun updateInfo(name: String?, birthDate: LocalDate?, gender: GenderType?, email: String?) {
        this.name = name
        this.birthDate = birthDate
        this.gender = gender
        this.email = email
    }
}
