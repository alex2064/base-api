package kr.co.baseapi.mq

import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class MessageReceiver {

    @RabbitListener(queues = ["example.queue"])
    fun receive(message: String) {
        log.info { "Received Message: $message" }
    }
}