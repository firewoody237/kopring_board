package com.example.kopring_board.api.controller.userController


import com.example.kopring_board.integrated.db.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/v1")
class UserController(
private val userService: UserService
) {


    @GetMapping("/users")
    fun getUsers(): Any? {
        return userService.getUsers()
    }
}