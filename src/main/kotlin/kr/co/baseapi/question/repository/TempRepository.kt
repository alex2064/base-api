package kr.co.baseapi.question.repository

import kr.co.baseapi.question.entity.Temp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*


interface TempRepository : JpaRepository<Temp, Long> {

    fun findByInData(inData: Long): Optional<Temp>

    @Query("select t from Temp t where t.inData = :inData")
    fun findByInDataQuery(inData: Long): Optional<Temp>
}