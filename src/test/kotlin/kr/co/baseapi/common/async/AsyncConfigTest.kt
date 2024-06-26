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

    Given("AsyncConfig 설정이 주어지면") {

        When("기본 Async Executor를 확인할 때") {
            val executor: ThreadPoolTaskExecutor = asyncConfigurer.asyncExecutor as ThreadPoolTaskExecutor

            Then("현재 실행대기 쓰레드 갯수는 2 여야 한다.") {
                executor.corePoolSize shouldBe 2
            }
            Then("최고 실행 쓰레드 갯수는 10 이여야 한다.") {
                executor.maxPoolSize shouldBe 10
            }
            Then("큐 사이즈는 100 이여야 한다.") {
                executor.queueCapacity shouldBe 100
            }
            Then("ThreadNamePrefix는 'async-default-' 여야 한다.") {
                executor.threadNamePrefix shouldBe "async-default-"
            }
        }

        When("customAsyncExecutor bean을 확인할 때") {

            Then("현재 실행대기 쓰레드 갯수는 2 여야 한다.") {
                customAsyncExecutor.corePoolSize shouldBe 2
            }
            Then("최고 실행 쓰레드 갯수는 2 여야 한다.") {
                customAsyncExecutor.maxPoolSize shouldBe 2
            }
            Then("큐 사이즈는 10 이여야 한다.") {
                customAsyncExecutor.queueCapacity shouldBe 10
            }
            Then("ThreadNamePrefix는 'async-custom-' 이여야 한다.") {
                customAsyncExecutor.threadNamePrefix shouldBe "async-custom-"
            }
        }
    }
})
