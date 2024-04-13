package kr.co.baseapi.question.service

import kr.co.baseapi.question.entity.Temp
import kr.co.baseapi.question.repository.TempRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@Service
class QuestionServiceImpl(
    private val tempRepository: TempRepository,
    private val otherService: OtherService
) : QuestionService {


    /*****************************************************************/


    @Transactional
    override fun question1(): Boolean {
        val temp: Temp = Temp.of(10L)
        tempRepository.save(temp)

        question1Sub(temp.id!!)

        return true
    }

    @Transactional
    override fun question1Sub(id: Long): Boolean {
        val temp: Temp = tempRepository.findById(id).orElseThrow()
        temp.upData = 20L

        return true
    }


    /*****************************************************************/

    @Transactional
    override fun question2(): Boolean {
        val temp: Temp = Temp.of(10L)
        tempRepository.save(temp)

        question2Sub(temp.id!!)

        return true
    }

    override fun question2Sub(id: Long): Boolean {
        val temp: Temp = tempRepository.findById(id).orElseThrow()
        temp.upData = 20L

        return true
    }


    /*****************************************************************/


    override fun question3(): Boolean {
        val temp: Temp = Temp.of(10L)
        tempRepository.save(temp)

        question3Sub(temp.id!!)

        return true
    }

    @Transactional
    override fun question3Sub(id: Long): Boolean {
        val temp: Temp = tempRepository.findById(id).orElseThrow()
        temp.upData = 20L

        return true
    }


    /*****************************************************************/


    override fun question4(): Boolean {
        val temp: Temp = Temp.of(10L)
        tempRepository.save(temp)

        question4Sub(temp.id!!)

        return true
    }

    override fun question4Sub(id: Long): Boolean {
        val temp: Temp = tempRepository.findById(id).orElseThrow()
        temp.upData = 20L

        return true
    }


    /*****************************************************************/


    @Transactional
    override fun question5(): Boolean {
        val temps: MutableList<Temp> = tempRepository.findAll()
        for (temp in temps) {
            question5Sub(temp.id!!)
        }

        return true
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun question5Sub(id: Long): Boolean {
        val temp: Temp = tempRepository.findById(id).orElseThrow()
        temp.upData = (temp.upData ?: 0L) + 10L

        Thread.sleep(5_000L)
        return true
    }


    /*****************************************************************/


    @Transactional
    override fun question6(): Boolean {
        otherService.success6()
        otherService.failure6()
        return true
    }


    /*****************************************************************/


    @Transactional
    override fun question7(): Boolean {
        otherService.success7()
        otherService.failure7()
        return true
    }


    /*****************************************************************/


    override fun question8(): Boolean {
        otherService.success8()
        otherService.failure8()
        return true
    }


    /*****************************************************************/


    override fun question9(): Boolean {
        otherService.success9()
        otherService.failure9()
        return true
    }


    /*****************************************************************/


    @Transactional
    override fun question10(): Boolean {
        val temps: MutableList<Temp> = tempRepository.findAll()
        for (temp in temps) {
            try {
                otherService.success10(temp.id!!)
                otherService.failure10(temp.id!!)
            } catch (e: Exception) {
                println(e.message)
            }
        }

        return true
    }


    /*****************************************************************/


    @Transactional
    override fun question11(): Boolean {
        val temp1: Temp = tempRepository.findById(1L).orElseThrow()
        val temp2: Temp = tempRepository.findByInData(100L).orElseThrow()
        val temp3: Temp = tempRepository.findByInDataQuery(100L).orElseThrow()

        val temp4: Temp = tempRepository.findByInDataQuery(200L).orElseThrow()
        val temp5: Temp = tempRepository.findByInData(200L).orElseThrow()
        val temp6: Temp = tempRepository.findById(2L).orElseThrow()

        return true
    }


    /*****************************************************************/


    @Transactional
    override fun question12(): Boolean {
        val temp1: Temp = tempRepository.findById(1L).orElseThrow()
        temp1.upData = 10L

        val temp2: Temp = tempRepository.findByInData(100L).orElseThrow()
        temp2.upData = 20L

        val temp3: Temp = tempRepository.findByInDataQuery(100L).orElseThrow()
        temp3.upData = 30L

        val temp4: Temp = tempRepository.findByInDataQuery(200L).orElseThrow()
        temp4.upData = 40L

        val temp5: Temp = tempRepository.findByInData(200L).orElseThrow()
        temp5.upData = 50L

        val temp6: Temp = tempRepository.findById(2L).orElseThrow()
        temp6.upData = 60L

        return true
    }
}