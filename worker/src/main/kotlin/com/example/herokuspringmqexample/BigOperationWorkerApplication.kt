package com.example.herokuspringmqexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BigOperationWorkerApplication

fun main(args: Array<String>) {
    runApplication<BigOperationWorkerApplication>(*args)
}
