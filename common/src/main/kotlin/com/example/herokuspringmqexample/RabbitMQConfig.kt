package com.example.herokuspringmqexample

import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.System.getenv
import java.net.URI
import java.net.URISyntaxException

@Configuration
@EnableRabbit
class RabbitMQConfig {
    private val cloudamqpUrlKey = "CLOUDAMQP_URL"
    protected val helloWorldQueueName = "hello.world.queue"

    @Bean
    fun connectionFactory(): ConnectionFactory {

        val urlString = getenv(cloudamqpUrlKey) ?: throw IllegalStateException(
            "Environment variable [$cloudamqpUrlKey] is not set."
        )

        val ampqUrl = try {
            URI(urlString)
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }

        val factory = CachingConnectionFactory()
        factory.username = ampqUrl.userInfo.split(":")[0]
        factory.setPassword(ampqUrl.userInfo.split(":")[1])
        factory.host = ampqUrl.host
        factory.port = ampqUrl.port
        factory.virtualHost = ampqUrl.path.substring(1)
        return factory
    }

    @Bean
    fun amqpAdmin(): AmqpAdmin? {
        return RabbitAdmin(connectionFactory())
    }

    @Bean
    fun rabbitTemplate(): RabbitTemplate {
        val template = RabbitTemplate(connectionFactory())
        template.routingKey = helloWorldQueueName
        template.setDefaultReceiveQueue(helloWorldQueueName)
        return template
    }

    @Bean
    fun queue(): Queue = Queue(helloWorldQueueName)

    @Bean
    fun rabbitListenerContainerFactory(): SimpleRabbitListenerContainerFactory {
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory())
        factory.setConcurrentConsumers(3)
        factory.setMaxConcurrentConsumers(10)
        return factory
    }
}
