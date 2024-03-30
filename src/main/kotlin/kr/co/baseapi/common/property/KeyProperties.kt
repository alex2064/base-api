package kr.co.baseapi.common.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("keys")
data class KeyProperties(
    val apiKey: String,
    val apikey2: String
)