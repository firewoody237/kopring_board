package com.example.kopring_board.api.controller.userController


import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.service.UserService
import com.example.kopring_board.integrated.webservice.api.ApiRequestMapping
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/v1")
class UserController(
    private val userService: UserService
) {
    companion object {
        private val log = LogManager.getLogger()
    }

    @ApiRequestMapping("/users", method = [RequestMethod.GET])
    fun getUsers(): Any? {
        log.debug("getUsers")
        return userService.getUsers()
    }

    @ApiRequestMapping("/users/{id}", method = [RequestMethod.GET])
    fun getUser(@PathVariable id: String): Any? {
        log.debug("getUsers")
        return userService.getUser(id)
    }

    @ApiRequestMapping("/users", method = [RequestMethod.POST])
    fun createUser(@RequestBody user: User): User? {
        log.debug("createUser")
        return userService.createUser(user)
    }

    @ApiRequestMapping("/users/{id}", method = [RequestMethod.POST])
    fun updateUser(@PathVariable id: String, @RequestBody user: User): Boolean? {
        log.debug("updateUser")
        return userService.updateUser(id, user)
    }

    @ApiRequestMapping("/users/{id}", method = [RequestMethod.DELETE])
    fun deleteUser(@PathVariable id: String): Boolean? {
        log.debug("deleteUser")
        return userService.deleteUser(id)
    }
}