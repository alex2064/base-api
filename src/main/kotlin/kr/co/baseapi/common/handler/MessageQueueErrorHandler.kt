package kr.co.baseapi.common.handler

import mu.KotlinLogging
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException
import org.springframework.util.ErrorHandler

private val log = KotlinLogging.logger {}

class MessageQueueErrorHandler(
    private val rabbitTemplate: RabbitTemplate
) : ErrorHandler {

    override fun handleError(t: Throwable) {
        if (t is ListenerExecutionFailedException) {
            val failedMessage: Message = t.failedMessage
            val retryCnt: Int = failedMessage.messageProperties.headers[X_RETRY] as? Int ?: 0

            if (retryCnt < MAX_RETRY_CNT) {
                val retryMessage: Message = MessageBuilder.fromMessage(failedMessage)
                    .setHeader(X_RETRY, retryCnt + 1)
                    .build()

                rabbitTemplate.send(
                    failedMessage.messageProperties.receivedExchange,
                    failedMessage.messageProperties.receivedRoutingKey,
                    retryMessage
                )
            } else {
                log.error { "[Listener] | Error: $failedMessage" }
                rabbitTemplate.send(DEAD_LETTER_EXCHANGE, DEAD_LETTER_ROUTING_KEY, failedMessage)
            }
        } else {
            log.error { "[Listener] | Unknown Error: $t" }
        }
    }

    companion object {
        const val X_RETRY: String = "x-retry"
        const val MAX_RETRY_CNT: Int = 3
        const val DEAD_LETTER_EXCHANGE: String = "dlx.exchange"
        const val DEAD_LETTER_ROUTING_KEY: String = "dlx.routing.key"
    }
}