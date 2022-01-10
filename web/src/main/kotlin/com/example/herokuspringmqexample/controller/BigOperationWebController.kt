package com.example.herokuspringmqexample.controller

import com.example.herokuspringmqexample.BigOperation
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bigOp")
class BigOperationWebController(
    @Autowired
    private val amqpTemplate: RabbitTemplate,
) {

    @ModelAttribute("bigOp")
    fun newBigOp(): BigOperation = BigOperation()

    @PostMapping
    fun process(@RequestBody bigOp: BigOperation, map: MutableMap<String?, Any?>): String? {
        amqpTemplate.convertAndSend(bigOp)
        println("Sent to RabbitMQ: $bigOp")

        // Send the bigOp back to the confirmation page for displaying details in view
        map["bigOp"] = bigOp
        return "bigOpReceivedConfirmation"
    }
}
