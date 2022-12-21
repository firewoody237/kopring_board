package com.example.kopring_board.api.controller.userController


import com.example.kopring_board.integrated.db.dto.common.PageDTO
import com.example.kopring_board.integrated.db.dto.user.*
import com.example.kopring_board.integrated.db.entity.Heart
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.service.HeartService
import com.example.kopring_board.integrated.db.service.UserService
import com.example.kopring_board.integrated.webservice.api.ApiRequestMapping
import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user/v1")
class UserController(
    private val userService: UserService,
) {
    companion object {
        private val log = LogManager.getLogger()
    }

    @ApiRequestMapping("/users", method = [RequestMethod.GET])
    fun getUsers(@RequestBody getUserDTO: GetUserDTO, @RequestBody pageDTO: PageDTO): Page<User> {
        log.debug("call getUsers : getUserDTO = '$getUserDTO', pageDTO = '$pageDTO'")
        return userService.getUsers(getUserDTO, pageDTO)
    }

    @ApiRequestMapping("/users/{id}", method = [RequestMethod.GET])
    fun getUser(@PathVariable id: String): User {
        log.debug("call getUser : id = '$id'")
        return userService.getUser(id)
    }

    @ApiRequestMapping("/users", method = [RequestMethod.POST])
    fun createUser(@RequestBody createUserDTO: CreateUserDTO): User {
        log.debug("call createUser : createUserDTO = '$createUserDTO'")
        return userService.createUser(createUserDTO)
    }

    //TODO: Password 변경 추가
    @ApiRequestMapping("/users", method = [RequestMethod.PUT])
    fun updateUser(@RequestBody updateUserDTO: UpdateUserDTO): Boolean {
        log.debug("call updateUser : updateUserDTO = '$updateUserDTO'")
        return userService.updateUser(updateUserDTO)
    }

    @ApiRequestMapping("/users", method = [RequestMethod.DELETE])
    fun deleteUser(@RequestBody deleteUserDTO: DeleteUserDTO): Boolean {
        log.debug("call deleteUser, deleteUserDTO = '$deleteUserDTO'")
        return userService.deleteUser(deleteUserDTO)
    }
}