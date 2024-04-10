package kr.co.baseapi.common.mq

import kr.co.baseapi.common.handler.MessageQueueErrorHandler
import org.springframework.amqp.core.AcknowledgeMode
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {

    @Bean
    fun rabbitListenerContainerFactory(
        connectionFactory: ConnectionFactory,
        rabbitTemplate: RabbitTemplate
    ): SimpleRabbitListenerContainerFactory {
        val factory: SimpleRabbitListenerContainerFactory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        factory.setMessageConverter(Jackson2JsonMessageConverter())
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL)  // 메세지의 수동 승인 활성화(ack, nack, reject를 리스너에서 보내야함)
        factory.setErrorHandler(MessageQueueErrorHandler(rabbitTemplate))
        return factory
    }

    @Bean
    fun rabbitTemplate(
        connectionFactory: ConnectionFactory
    ): RabbitTemplate {
        val rabbitTemplate: RabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = Jackson2JsonMessageConverter()
        return rabbitTemplate
    }
}