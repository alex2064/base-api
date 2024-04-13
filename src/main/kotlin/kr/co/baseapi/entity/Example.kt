package kr.co.baseapi.entity

import jakarta.persistence.*
import kr.co.baseapi.entity.converter.GenderTypeConverter
import kr.co.baseapi.enums.GenderType
import org.hibernate.type.YesNoConverter
import java.math.BigDecimal
import java.time.LocalDate


/**
 * Entity 생성
 * 1. class 로 만들기
 * 2. BaseEntity() 상속
 * 3. var + null 허용 + protected set(필요한 경우만 제거)
 * 4. 주 생성자 protected 로 막기
 * 5. Entity 생성 : 팩토리 메서드 사용
 * 6. Entity 변경 : 메서드 사용
 * 7. Enum 은 Converter class 생성 후 사용
 * 8. Boolean 은 YesNoConverter 사용
 * 9. 메서드 명 사용
 *      1) {of, ~Of} : 팩토리 메서드
 *      2) {add~, modify~} : 엔티티 변경 메서드(행동이 나오고 뒤에 이어서 대상이 나오게 메서드명 사용)
 */
@Entity
@Table(schema = "dev", name = "EXAMPLE")
class Example protected constructor() : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null
        protected set

    @Column(name = "NAME")
    var name: String? = null
        protected set

    @Column(name = "AGE")
    var age: Int? = null
        protected set

    @Column(name = "AMOUNT")
    var amount: Long? = null
        protected set

    @Column(name = "HEIGHT")
    var height: BigDecimal? = null
        protected set

    @Convert(converter = GenderTypeConverter::class)
    @Column(name = "GENDER")
    var gender: GenderType? = null
        protected set

    @Convert(converter = YesNoConverter::class)
    @Column(name = "IS_AUTH")
    var isAuth: Boolean? = null
        protected set

    @Column(name = "BASE_DATE")
    var baseDate: LocalDate? = null
        protected set


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
        ): Example = Example().apply {
            this.name = name
            this.age = age
            this.amount = amount
            this.height = height
            this.gender = gender
            this.isAuth = isAuth
            this.baseDate = baseDate
        }
    }
}