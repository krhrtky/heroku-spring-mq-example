package com.example.herokuspringmqexample

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduleWorker {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Scheduled(cron = "0 0,30 * * * *", zone = "Asia/Tokyo")
    fun execute() {
        logger.info("${this.javaClass.simpleName} execute!")
    }
}
