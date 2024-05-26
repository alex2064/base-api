# base-api

---

## 소개
이 프로젝트는 Kotlin 으로 프로젝트를 진행할때 기본 프레임워크로 사용하고 여러 예시들을 가지고 있는 Spring Boot 애플리케이션입니다.

---

## 기술 스택
- Kotlin 1.9.22 (JAVA 17)
- Spring Boot 3.2.2
- Undertow
- JPA
- Querydsl
- Validation
- OpenFeign
- MariaDB 11.1
- Redis alpine
- RabbitMQ 3-management
- Swagger
- Kotlin-logging
- P6spy
- Kotest
- Mockk
- Gradle

---

## 패키지 구조
```
base-api
└── src
  ├── main
  │ ├── kotlin.kr.co.baseapi
  │ │ ├── client        -> API 호출 OpenFeign 
  │ │ ├── common
  │ │ │ ├── aop             -> AOP 설정 대상
  │ │ │ ├── async           -> 비동기 실행하는 Executor 설정
  │ │ │ ├── auditor         -> JpaAuditing 설정
  │ │ │ ├── cache           -> Redis 사용한 캐시 설정
  │ │ │ ├── converter       -> DB Code와 Enum 변환 추상클래스, GET 호출시 쿼리스트링에 있는 EnumType 변환 Converter
  │ │ │ ├── feign           -> OpenFeign 관련 설정
  │ │ │ ├── handler         -> Response, Async, MQ 처리 Handler
  │ │ │ ├── interceptor     -> API Log, Get 호출 공통 Converter 인터셉터 설정
  │ │ │ ├── mq              -> RabbitMQ 설정
  │ │ │ ├── p6spy           -> SQL Log 설정
  │ │ │ ├── property        -> ConfigurationProperties 예시
  │ │ │ ├── querydsl        -> Querydsl 설정
  │ │ │ ├── redis
  │ │ │ │ ├── annotation        -> Redis로 중복요청 컨트롤할 Annotation
  │ │ │ │ ├── component         -> 중복요청 컨트롤 Manager, AOP 설정
  │ │ │ │ └── enums             -> 중복요청 컨트롤 할 object-type
  │ │ │ ├── swagger         -> swagger 설정 
  │ │ │ ├── util            -> 공통 Util, Object or 확장함수
  │ │ │ └── validator       -> Spring Validation 에서 추가적으로 체크할 validator(email, enum, phone)
  │ │ ├── controller    -> controller 예시
  │ │ ├── dto           -> dto 예시, Page처리 Request, Response 공통화
  │ │ ├── entity        -> entity 예시
  │ │ │ └── converter       -> entity에 적용할 Converter
  │ │ ├── enums         -> enum 예시
  │ │ ├── internal      -> 내부적으로 사용할 서비스
  │ │ ├── mq            -> mq 예시
  │ │ ├── question      -> 트랜잭션과 영속성 컨텍스트에서 JPA가 어떻게 DB와 데이터를 주고받는지 질문 리스트
  │ │ │ ├── controller
  │ │ │ ├── entity
  │ │ │ ├── repository
  │ │ │ └── service
  │ │ ├── redis         -> redis 예시
  │ │ │ ├── entity
  │ │ │ ├── repository
  │ │ │ └── service
  │ │ ├── repository    -> repository 예시, Querydsl page 처리 기준 
  │ │ ├── service       -> service 예시
  │ │ └── BaseApiApplication.kt
  │ └── resources
  │   └── config
  └── test              -> kotest(BDD), mockk를 활용한 테스트 코드
    ├── kotlin.kr.co.baseapi
    │ ├── common
    │ ├── controller
    │ ├── entity
    │ ├── repository
    │ └── service
    └── resources
      └── config
```

---

## 컨벤션
- 각 파일별로 `GUIDE` 작성
- TODO 나 소스에 GUIDE 로 검색시 확인 가능

---

## 예시 및 사용법

### 1. Converter
Database 에 저장되어있는 코드값과 Enum 값을 서로 변환

<br/>

#### <관련 코드>

##### `kr.co.baseapi.common.converter.ConvertType`
Converter 를 생성해줄 Enum에 구현해줄 인터페이스

##### `kr.co.baseapi.common.converter.AbstractEnumTypeConverter`
DB Code와 Enum 변환 추상클래스

##### `kr.co.baseapi.common.util.EnumUtil`
Enum 에서 값 기준으로 해당 Enum을 찾는 object

##### `kr.co.baseapi.entity.converter.GenderTypeConverter`
AbstractEnumTypeConverter 를 상속받아 Entity에 걸어줄 Converter

<br/>

#### <사용 방법>

##### 1) 적용할 Enum에 ConvertType 구현

- name : Kotlin code 에서 사용할 Enum 이름
- code : Database 에 저장할 Code 값
- desc : Enum 에 대한 설명(개발시 참고용)

```kotlin
enum class GenderType(
    override val code: String,
    override val desc: String
) : ConvertType {

    MAN("man", "남자"),
    WOMAN("woman", "여자");

    companion object {
        @JvmStatic
        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        fun get(name: String?): GenderType? = EnumUtil.getEnumByNameOrCode(GenderType::class.java, name)
    }
}
```

##### 2) AbstractEnumTypeConverter 를 상속받은 @Converter 생성

```kotlin
@Converter
class GenderTypeConverter : AbstractEnumTypeConverter<GenderType>(GenderType::class.java)
```

##### 3) Entity에 Converter 적용

```kotlin
@Entity
@Table(schema = "dev", name = "EXAMPLE")
class Example protected constructor() : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null
        protected set

    @Convert(converter = GenderTypeConverter::class)
    @Column(name = "GENDER")
    var gender: GenderType? = null
        protected set

    companion object {
        fun of(
            gender: GenderType?,
        ): Example = Example().apply {
            this.gender = gender
        }
    }
}
```

<br/>

### 2. RedisLock
Redis에 Key를 등록해서 동시 요청 처리

<br/>

#### <관련 코드>

##### `kr.co.baseapi.common.redis.annotation.RedisLock`
동시 요청 처리할 함수에 걸어줄 Annotation

#### `kr.co.baseapi.common.redis.component.RedisLockManager`
Redis 에 값을 넣고 빼면서 Lock 관리하는 컴포넌트

#### `kr.co.baseapi.common.redis.component.RedisLockAspect`
RedisLock Annotation 에 AOP를 걸어 Lock 관리하는 컴포넌트

#### `kr.co.baseapi.common.redis.enums.LockType`
Lock object-type 관리하는 Enum

<br/>

#### <사용 방법>

##### 1) 필요한 object-type Enum 에 정의

- objectType : Redis에 저장할때 사용할 object-type(object-type:id 를 keyName으로 사용)
- desc : Enum 에 대한 설명(개발시 참고용)
- keyName : 함수의 파라미터 중 id로 사용할 파라미터의 명칭

```kotlin
enum class LockType(
    val objectType: String,
    val desc: String,
    val keyName: String?
) {
    /* 예시 enum */
    FIND("find", "조회", "id");
}
```

##### 2) 동시 요청을 처리할 함수에 @RedisLock 적용

```kotlin
@RedisLock(LockType.FIND)
override fun findExample(id: Long): ExamResult {
    val example: Example = exampleRepository.findById(id).orElseThrow()
    val result: ExamResult = ExamResult.exampleOf(example)

    return result
}
```

<br/>

### 3. MQ
MQ 사용 예시

<br/>

#### <관련 코드>

##### `kr.co.baseapi.common.mq.RabbitConfig`
RabbitMQ 설정

##### `kr.co.baseapi.common.handler.MessageQueueErrorHandler`
MessageQueue Error Handler, 3번 재시도 후 DEAD LETTER QUEUE 로 이동

<br/>

#### <사용 방법>

##### 1) Message를 보낼 Producer 정의

- exchange -> routing 보고 queue 선택

```kotlin
@Component
class MessageSender(
    private val rabbitTemplate: RabbitTemplate
) {
    fun send(message: String) {
        rabbitTemplate.convertAndSend("example.exchange", "example.routing", message)
    }

    fun sendDto(param: ExamParam) {
        rabbitTemplate.convertAndSend("example.exchange", "example.routing.dto", param)
    }
}
```

##### 2) Message를 받아 처리할 Consumer 정의

```kotlin
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


    @RabbitListener(queues = ["example.queue.dto"])
    fun receiveDto(param: ExamParam, channel: Channel, @Header(AmqpHeaders.DELIVERY_TAG) deliveryTag: Long) {
        try {
            log.info { "Received DTO: $param" }
            channel.basicAck(deliveryTag, false)

        } catch (e: Exception) {
            channel.basicNack(deliveryTag, false, false)
            throw e
        }
    }
}
```

<br/>

### 4. Page 처리
페이지 요청 처리

<br/>

#### <관련 코드>

##### `kr.co.baseapi.dto.PageParam`
pageNumber, pageSize를 받아 Pageable 생성하는 추상클래스, 요청받는 DTO에 상속

##### `kr.co.baseapi.dto.PageResult`
List 형태의 결과값을 Response에 보낼때 사용하는 클래스(summary, Page는 팩토리 함수로 분류해서 처리)

<br/>

#### <사용 방법>

##### paging 처리 순서
1. PageParam() 상속받은 DTO 파라미터로 받기
2. 사용할 entity 나열
3. pageable 추출
4. 공통 query 생성(BooleanBuilder, JPAQuery)
5. content query fetch
6. total query fetchOne
7. PageImpl 생성 / return

##### 1) 페이지 처리 요청 받을 DTO에 PageParam 상속

```kotlin
data class ExamPageParam(
    @field:Schema(description = "이름")
    val name: String?
    
    // 1. PageParam() 상속받은 DTO 파라미터로 받기
) : PageParam()
```

##### 2) Controller에 @Valid 로 유효성 체크

```kotlin
@Tag(name = "예시 controller")
@RequestMapping("/exam")
@RestController
class ExampleController(
    private val exampleService: ExampleService
) {
    @ApiForExam
    @Operation(summary = "조회 페이징 처리", description = "조회 페이징 처리 설명")
    @GetMapping("/example/page")
    fun findExampleDslPage(@ParameterObject @Valid param: ExamPageParam): PageResult<ExamResult, Nothing?> {
        return exampleService.findExampleDslPage(param)
    }
}
```

##### 3) Querydsl에서 페이지 처리

```kotlin
override fun findByNamePage(param: ExamPageParam): PageImpl<ExamResult> {
        // 2. 사용할 entity 나열
        val example: QExample = QExample.example

        // 3. pageable 추출
        val pageable: Pageable = param.pageable!!

        // where 조건
        val booleanBuilder: BooleanBuilder = BooleanBuilder()
            .and(example.name.contains(param.name))
            .and(example.isAuth.eq(true))

        // 4. 공통 query 생성(BooleanBuilder, JPAQuery)
        val query: JPAQuery<Example> = jpaQueryFactory
            .selectFrom(example)
            .where(booleanBuilder)
            .orderBy(example.id.asc())

        // 5. content query fetch
        val content: MutableList<ExamResult> =
            query.clone()
                .select(
                    Projections.constructor(
                        ExamResult::class.java,
                        example.id,
                        example.name,
                        example.age,
                        example.amount,
                        example.height,
                        example.gender,
                        example.isAuth,
                        example.baseDate
                    )
                )
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()

        // 6. total query fetchOne
        val total: Long = query.clone().select(example.id.count()).fetchOne() ?: 0L

        // 7. PageImpl 생성 / return
        return PageImpl<ExamResult>(content, pageable, total)
    }
```

##### 4) 페이지 처리해야하는 함수에서 PageResult 팩토리 함수로 Response 생성

```kotlin
override fun findExampleDslPage(param: ExamPageParam): PageResult<ExamResult, Nothing?> {
    val page: Page<ExamResult> = exampleRepositorySupport.findByNamePage(param)
    val result: PageResult<ExamResult, Nothing?> = PageResult.pageOf(page)

    return result
}
```