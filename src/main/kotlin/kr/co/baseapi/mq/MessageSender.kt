package kr.co.baseapi.mq

import kr.co.baseapi.dto.ExamParam
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class MessageSender(
    private val rabbitTemplate: RabbitTemplate
) {
    fun send(message: String) {
        rabbitTemplate.convertAndSend("example.exchange", "example.routing", message)
    }

    fun sendDto(param: ExamParam) {
        rabbitTemplate.convertAndSend("example.exchange", "example.routing.dto", param)
    }
}