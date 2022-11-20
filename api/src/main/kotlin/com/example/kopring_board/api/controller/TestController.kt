package com.example.kopring_board.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/test")
    public fun test() : Any?{
        return mapOf(
            "test" to "ok"
        )
    }


}