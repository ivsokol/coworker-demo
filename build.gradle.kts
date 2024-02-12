plugins {
    kotlin("jvm") version "1.9.22"
}

group = "org.example"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val zeebeVersion = "8.2.7" // 8.4.1 or 8.2.7

configurations.all {
    resolutionStrategy {
        force("io.camunda:zeebe-client-java:$zeebeVersion")
    }
}

dependencies {
    implementation("org.camunda.community.extension.kotlin.coworker:coworker-core:0.5.0")
    implementation("io.camunda:zeebe-client-java:$zeebeVersion")
    implementation("ch.qos.logback:logback-classic:1.4.14")

    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("org.testcontainers:testcontainers:1.19.3")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}