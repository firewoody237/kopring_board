package com.example.kopring_board.api.controller

import com.example.kopring_board.integrated.webservice.api.ApiRequestMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @ApiRequestMapping("/test", method = [RequestMethod.GET])
    fun test(): Any? {
        return mapOf(
            "test" to "ok"
        )
    }


}