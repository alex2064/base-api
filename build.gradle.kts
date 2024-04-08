import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion: String = "1.9.22"
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion
}

group = "kr.co"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2023.0.0"

dependencies {
    // === Spring ===
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    // === Kotlin ===
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // === Querydsl ===
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")
    kapt("jakarta.persistence:jakarta.persistence-api")
    kapt("jakarta.annotation:jakarta.annotation-api")

    // === mariadb ===
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    // === redis ===
    implementation("org.springframework.boot:spring-boot-starter-data-redis")


    // === mongodb ===

    // === AMQP(RabbitMQ) ===
    implementation("org.springframework.boot:spring-boot-starter-amqp")


    // === Swagger ===
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // === Logging ===
    implementation("io.github.microutils:kotlin-logging:3.0.5")

    // === p6spy(queryDsl Sql Log) ===
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.1")

    // === Test ===
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // === Kotest ===
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.8.0")
    testImplementation("io.kotest:kotest-framework-engine-jvm:5.8.0")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")

    // === Mockk ===
    testImplementation("io.mockk:mockk-jvm:1.13.10")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

noArg {
    annotation("jakarta.persistence.Entity")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

querydsl {
    jpa = true
    querydslSourcesDir = "src/main/generated"
}