plugins {
    id("org.springframework.boot") version "2.6.2"
}

dependencies {
    implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-amqp")
}
