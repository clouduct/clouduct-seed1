package org.clouduct.springboot.helloworld

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/")
class HelloWorldController() {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/", "/api/hello")
    fun retrieveConfig(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello World")
    }


}