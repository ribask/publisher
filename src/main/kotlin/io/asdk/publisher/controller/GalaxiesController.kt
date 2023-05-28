package io.asdk.publisher.controller

import io.asdk.publisher.models.Galaxy
import io.asdk.publisher.sqsService.GalaxiesService
import io.asdk.publisher.sqsService.SqsService
import org.slf4j.ILoggerFactory
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus

@Controller
class GalaxiesController(
    private val galaxiesService: GalaxiesService
) {

    var log = LoggerFactory.getLogger(SqsService::class.java)!!

    @PostMapping("/galaxy")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun getGalaxy(@RequestBody galaxy: Galaxy) {
        try {
            galaxiesService.process(galaxy)
        } catch (e: Exception) {
            log.error("an error occurred while trying to publish galaxy: ${e.message}")
        }
    }
}