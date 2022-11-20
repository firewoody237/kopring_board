package com.example.kopring_board.api.controller.userController


import com.example.kopring_board.integrated.db.service.UserService
import com.example.kopring_board.integrated.webservice.api.ApiRequestMapping
import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/v1")
class UserController(
    private val userService: UserService
) {
    companion object {
        private val log = LogManager.getLogger()
    }

    @ApiRequestMapping("/users")
    fun getUsers(): Any? {
        log.debug("getUsers")
        return userService.getUsers()
    }

    @ApiRequestMapping("/users/{id}")
    fun getUser(@PathVariable id:String): Any? {
        log.debug("getUsers")
        return userService.getUser(id)
    }
}