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
    fun getUsers(): List<User> {
        log.debug("getUsers")
        return userService.getUsers()
    }

    @ApiRequestMapping("/users/{id}", method = [RequestMethod.GET])
    fun getUser(@PathVariable id: String): User? {
        log.debug("getUsers")
        return userService.getUser(id)
    }

    @ApiRequestMapping("/users", method = [RequestMethod.POST])
    fun createUser(@RequestBody user: User): User? {
        log.debug("createUser, user='$user'")
        return userService.createUser(user)
    }

    //PATCH로 상황에 따라 특정 컬럼만 받을 순 없나?
    @ApiRequestMapping("/users", method = [RequestMethod.PUT])
    fun updateUser(@RequestBody user: User): User? {
        log.debug("updateUser, user='$user'")
        return userService.updateUser(user)
    }

    @ApiRequestMapping("/users", method = [RequestMethod.DELETE])
    fun deleteUser(@RequestBody user: User): Boolean? {
        log.debug("deleteUser, user='$user'")
        return userService.deleteUser(user)
    }
}