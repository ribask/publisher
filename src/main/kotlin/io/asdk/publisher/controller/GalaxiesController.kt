package io.asdk.publisher.controller

import io.asdk.publisher.model.Galaxy
import io.asdk.publisher.service.ObjectService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus

@Controller
class GalaxiesController(
    private val objectService: ObjectService
) {

    var log = LoggerFactory.getLogger(GalaxiesController::class.java)!!

    @PostMapping("/galaxy")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun postGalaxy(@RequestBody galaxy: Galaxy) {
        try {
            objectService.process(galaxy)
        } catch (e: Exception) {
            log.error("an error occurred while trying to publish galaxy: ${e.message}")
        }
    }
}