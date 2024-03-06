package kr.co.baseapi.common.async

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@SpringBootTest(classes = [AsyncConfig::class])
class AsyncConfigTest(
    private val asyncConfigurer: AsyncConfigurer,
    private val customAsyncExecutor: ThreadPoolTaskExecutor
) : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    Given("AsyncConfig 설정이 주어졌을 때") {

        When("기본 Async Executor를 확인하면") {
            val executor: ThreadPoolTaskExecutor = asyncConfigurer.asyncExecutor as ThreadPoolTaskExecutor

            Then("corePoolSize는 2여야 한다") {
                executor.corePoolSize shouldBe 2
            }
            Then("maxPoolSize는 10이여야 한다") {
                executor.maxPoolSize shouldBe 10
            }
            Then("queueCapacity는 100이여야 한다") {
                executor.queueCapacity shouldBe 100
            }
            Then("ThreadNamePrefix는 async-default- 여야 한다") {
                executor.threadNamePrefix shouldBe "async-default-"
            }
        }

        When("customAsyncExecutor bean이 생성되면") {

            Then("corePoolSize는 2여야 한다") {
                customAsyncExecutor.corePoolSize shouldBe 2
            }
            Then("maxPoolSize는 2이여야 한다") {
                customAsyncExecutor.maxPoolSize shouldBe 2
            }
            Then("queueCapacity는 10이여야 한다") {
                customAsyncExecutor.queueCapacity shouldBe 10
            }
            Then("ThreadNamePrefix는 async-custom- 여야 한다") {
                customAsyncExecutor.threadNamePrefix shouldBe "async-custom-"
            }
        }
    }
})
