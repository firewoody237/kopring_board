package com.example.kopring_board.api.controller.userController


import com.example.kopring_board.integrated.db.dto.user.*
import com.example.kopring_board.integrated.db.entity.Heart
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.service.HeartService
import com.example.kopring_board.integrated.db.service.UserService
import com.example.kopring_board.integrated.webservice.api.ApiRequestMapping
import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user/v1")
class UserController(
    private val userService: UserService,
    private val heartService: HeartService
) {
    companion object {
        private val log = LogManager.getLogger()
    }

    @ApiRequestMapping("/users", method = [RequestMethod.GET])
    fun getUsers(@RequestBody getUserDTO: GetUserDTO): List<User> {
        log.debug("getUsers By Parameter")
        return userService.getUsers(getUserDTO)
    }

    @ApiRequestMapping("/users/{id}", method = [RequestMethod.GET])
    fun getUser(@PathVariable id: String): User {
        log.debug("getUsers")
        return userService.getUser(id)
    }

    @ApiRequestMapping("/users", method = [RequestMethod.POST])
    fun createUser(@RequestBody createUserDTO: CreateUserDTO): User {
        log.debug("createUser, user='$createUserDTO'")
        return userService.createUser(createUserDTO)
    }

    //TODO: Password 변경 추가
    @ApiRequestMapping("/users", method = [RequestMethod.PUT])
    fun updateUser(@RequestBody updateUserDTO: UpdateUserDTO): Boolean {
        log.debug("updateUser, user='$updateUserDTO'")
        return userService.updateUser(updateUserDTO)
    }

    @ApiRequestMapping("/users", method = [RequestMethod.DELETE])
    fun deleteUser(@RequestBody deleteUserDTO: DeleteUserDTO): Boolean {

        log.debug("deleteUser, user='$deleteUserDTO'")
        return userService.deleteUser(deleteUserDTO)
    }

    //TODO: 이 구조가 맞나?
    @ApiRequestMapping("/users/{id}/heart", method = [RequestMethod.GET])
    fun getHeartPosts(@PathVariable id: String): List<Heart> {
        log.debug("getUserHeart, userId='$id'")
        return heartService.getHeartPosts(id)
    }
}