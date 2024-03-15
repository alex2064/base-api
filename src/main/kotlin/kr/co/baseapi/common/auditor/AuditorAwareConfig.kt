package kr.co.baseapi.common.auditor

import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing
class AuditorAwareConfig : AuditorAware<Long> {
    override fun getCurrentAuditor(): Optional<Long> {
        return Optional.of(100L)    // 작업자의 User Id
    }
}