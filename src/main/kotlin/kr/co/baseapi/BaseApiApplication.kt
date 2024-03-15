package kr.co.baseapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
class BaseApiApplication

fun main(args: Array<String>) {
    runApplication<BaseApiApplication>(*args)
}
