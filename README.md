```kotlin
enum class LockType(
    val objectType: String,
    val desc: String,
    val keyName: String?
) {
    /* 예시 enum */
    APPLICATION("application", "신청", "userId"),
    CANCEL("cancel", "취소", "userId"),
    PROC("proc", "일괄작업", null);
}
```
> 이것은 인용구입니다.

# base-api

---

## 소개
이 프로젝트는 Kotlin 으로 프로젝트를 진행할때 기본 base가 되고 여러 예시들을 가지고 있는 Spring Boot 애플리케이션입니다.

---

## 기술스택
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
  └── test              -> kotest, BDD, mockk를 활용한 테스트 코드
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
- TODO 에 GUIDE 로 검색시 확인 가능

---

## 코드 설명

### ㅁㅁㅁ
