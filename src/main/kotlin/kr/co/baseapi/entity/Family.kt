package kr.co.baseapi.entity

import jakarta.persistence.*
import kr.co.baseapi.entity.converter.GenderTypeConverter
import kr.co.baseapi.enums.GenderType
import org.hibernate.type.YesNoConverter
import java.time.LocalDate

@Entity
@Table(schema = "dev", name = "FAMILY")
class Family private constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAMILY_ID")
    var familyId: Long? = null,

    @Column(name = "NAME")
    var name: String? = null

) : BaseEntity() {

    companion object {
        fun of(name: String?): Family = Family(name = name)
    }

    fun updateName(name: String?) {
        this.name = name
    }
}
