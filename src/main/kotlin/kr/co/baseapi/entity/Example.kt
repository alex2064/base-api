package kr.co.baseapi.entity

import jakarta.persistence.*
import kr.co.baseapi.entity.converter.GenderTypeConverter
import kr.co.baseapi.enums.GenderType
import org.hibernate.type.YesNoConverter
import java.math.BigDecimal
import java.time.LocalDate


/**
 * Entity 생성
 * 1. class(var + null 허용) 로 만들기
 * 2. 주 생성자 Private로 막고 팩토리 메서드만 사용
 * 3. BaseEntity() 상속
 * 4. entity 변경은 메소드 사용
 */
@Entity
@Table(schema = "dev", name = "EXAMPLE")
class Example private constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null,

    @Column(name = "NAME")
    var name: String? = null,

    @Column(name = "AGE")
    var age: Int? = null,

    @Column(name = "AMOUNT")
    var amount: Long? = null,

    @Column(name = "HEIGHT")
    var height: BigDecimal? = null,

    @Convert(converter = GenderTypeConverter::class)
    @Column(name = "GENDER")
    var gender: GenderType? = null,

    @Convert(converter = YesNoConverter::class)
    @Column(name = "IS_AUTH")
    var isAuth: Boolean? = null,

    @Column(name = "BASE_DATE")
    var baseDate: LocalDate? = null

) : BaseEntity() {

    fun updateInfo(
        name: String?,
        age: Int?,
        amount: Long?,
        height: BigDecimal?,
        gender: GenderType?,
        isAuth: Boolean?,
        baseDate: LocalDate?
    ) {
        this.name = name
        this.age = age
        this.amount = amount
        this.height = height
        this.gender = gender
        this.isAuth = isAuth
        this.baseDate = baseDate
    }

    companion object {
        fun of(
            name: String?,
            age: Int?,
            amount: Long?,
            height: BigDecimal?,
            gender: GenderType?,
            isAuth: Boolean?,
            baseDate: LocalDate?
        ): Example =
            Example(
                name = name,
                age = age,
                amount = amount,
                height = height,
                gender = gender,
                isAuth = isAuth,
                baseDate = baseDate
            )
    }
}
