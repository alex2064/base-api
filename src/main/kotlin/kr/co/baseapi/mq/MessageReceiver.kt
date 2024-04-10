package kr.co.baseapi.mq

import com.rabbitmq.client.Channel
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class MessageReceiver {

    // RabbitMQ 에서 메세지 push
    @RabbitListener(queues = ["example.queue"])
    fun receive(message: String, channel: Channel, @Header(AmqpHeaders.DELIVERY_TAG) deliveryTag: Long) {
        try {
            log.info { "Received Message: $message" }
            channel.basicAck(deliveryTag, false)    // 두번째 인자 false로 하면 메세지 단건 처리

        } catch (e: Exception) {
            channel.basicNack(deliveryTag, false, false)    // 세번째 인자는 메세지를 큐에 다시 넣을지 여부
            throw e // MessageQueueErrorHandler 에서 retry 로직 대기 중
        }
    }
}