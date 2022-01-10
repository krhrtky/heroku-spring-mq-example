plugins {
    id("org.springframework.boot") version "2.6.2"
}

dependencies {
    implementation("org.springframework:spring-messaging")
    implementation("org.springframework.amqp:spring-rabbit")
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}
