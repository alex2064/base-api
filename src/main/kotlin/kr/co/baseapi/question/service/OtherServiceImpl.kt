package kr.co.baseapi.question.service

import kr.co.baseapi.question.entity.Temp
import kr.co.baseapi.question.repository.TempRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@Service
class OtherServiceImpl(
    private val tempRepository: TempRepository
) : OtherService {


    /*****************************************************************/


    @Transactional
    override fun success6(): Boolean {
        val temp: Temp = Temp.of(10L)
        tempRepository.save(temp)

        return true
    }

    @Transactional
    override fun failure6(): Boolean {
        throw RuntimeException()
    }


    /*****************************************************************/


    override fun success7(): Boolean {
        val temp: Temp = Temp.of(10L)
        tempRepository.save(temp)

        return true
    }

    override fun failure7(): Boolean {
        throw RuntimeException()
    }


    /*****************************************************************/


    @Transactional
    override fun success8(): Boolean {
        val temp: Temp = Temp.of(10L)
        tempRepository.save(temp)

        return true
    }

    @Transactional
    override fun failure8(): Boolean {
        throw RuntimeException()
    }


    /*****************************************************************/


    override fun success9(): Boolean {
        val temp: Temp = Temp.of(10L)
        tempRepository.save(temp)

        return true
    }

    override fun failure9(): Boolean {
        throw RuntimeException()
    }


    /*****************************************************************/


    @Transactional
    override fun success10(id: Long): Boolean {
        val temp: Temp = tempRepository.findById(id).orElseThrow()
        temp.upData = (temp.upData ?: 0L) + 10L

        Thread.sleep(5_000L)
        return true
    }

    @Transactional
    override fun failure10(id: Long): Boolean {
        throw RuntimeException()
    }
}