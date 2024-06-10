package kr.co.baseapi.common.redis.config

import kr.co.baseapi.common.redis.component.RedisKeyExpirationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter

// Redis 서버가 키 만료 이벤트를 게시하도록 설정(redis.conf or docker-compose.yml 수정)
@Configuration
class RedisConfig {

    @Bean
    fun redisMessageListenerContainer(
        redisConnectionFactory: RedisConnectionFactory,
        messageListenerAdapter: MessageListenerAdapter
    ): RedisMessageListenerContainer {
        val container: RedisMessageListenerContainer = RedisMessageListenerContainer()
        container.setConnectionFactory(redisConnectionFactory)
        container.addMessageListener(messageListenerAdapter, ChannelTopic("__keyevent@0__:expired"))

        return container
    }

    @Bean
    fun listenerAdapter(redisKeyExpirationListener: RedisKeyExpirationListener): MessageListenerAdapter =
        MessageListenerAdapter(redisKeyExpirationListener, "removeRedisHashKey")
}