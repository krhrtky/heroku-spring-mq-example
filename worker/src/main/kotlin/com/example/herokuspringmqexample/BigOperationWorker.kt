package com.example.herokuspringmqexample

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class BigOperationWorker {

    @RabbitListener(queues = ["hello.world.queue"])
    fun execute(@Payload bigOp: BigOperation) {
        println("Received from RabbitMQ: $bigOp")
    }
}
