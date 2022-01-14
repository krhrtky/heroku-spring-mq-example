package com.example.herokuspringmqexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class BigOperationWorkerApplication

fun main(args: Array<String>) {
    runApplication<BigOperationWorkerApplication>(*args)
}
