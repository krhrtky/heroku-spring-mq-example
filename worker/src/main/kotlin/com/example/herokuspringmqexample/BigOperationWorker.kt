package com.example.herokuspringmqexample

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.support.converter.SimpleMessageConverter
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main(args: Array<String>) {
    val rabbitConfig: ApplicationContext = AnnotationConfigApplicationContext(RabbitMQConfig::class.java)
    val rabbitConnectionFactory: ConnectionFactory = rabbitConfig.getBean(ConnectionFactory::class.java)
    val rabbitQueue: Queue = rabbitConfig.getBean(Queue::class.java)
    val messageConverter = SimpleMessageConverter()

    // create a listener container, which is required for asynchronous message consumption.
    // AmqpTemplate cannot be used in this case
    val listenerContainer = SimpleMessageListenerContainer(rabbitConnectionFactory)
    listenerContainer.setQueueNames(rabbitQueue.name)

    // set the callback for message handling
    listenerContainer.setMessageListener {
        val bigOp: BigOperation = messageConverter.fromMessage(it) as BigOperation

        // simply printing out the operation, but expensive computation could happen here
        println("Received from RabbitMQ: $bigOp")
    }

    // set a simple error handler
    listenerContainer.setErrorHandler { it.printStackTrace() }

    // register a shutdown hook with the JVM
    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            println("Shutting down BigOperationWorker")
            listenerContainer.shutdown()
        }
    })

    // start up the listener. this will block until JVM is killed.
    listenerContainer.start()
    println("BigOperationWorker started")
}
