package com.example.kopring_board.admin.controller.userAdminController

import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.dto.user.CreateUserDTO
import com.example.kopring_board.integrated.db.dto.user.DeleteUserDTO
import com.example.kopring_board.integrated.db.dto.user.UpdateUserDTO
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.service.UserService
import com.example.kopring_board.integrated.user.Authority
import com.example.kopring_board.integrated.webservice.api.ApiRequestMapping
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/user/v1")
class UserAdminController(
    private val userService: UserService,
) {

    //TODO: 아직은 딱히 어드민일 때만 할 만한게 떠오르지 않는다
    companion object {
        private val log = LogManager.getLogger()
    }

    //TODO: 조회 따로 만들어야 하나?

    @ApiRequestMapping("/users/{id}", method = [RequestMethod.POST])
    fun createUser(@RequestBody createUserDTO: CreateUserDTO): User {
        log.debug("call admin createUser: createUserDTO = '$createUserDTO")
        return when (createUserDTO.authority) {

            Authority.NORMAL.toString() -> {
                userService.createUser(createUserDTO)
            }

            //TODO: 이거 컨트롤러에서 검증해도 되나..?
            else -> {
                throw ResultCodeException(ResultCode.ERROR_USER_NOT_ADMIN, loglevel = Level.WARN)
            }
        }
    }

    @ApiRequestMapping("/users", method = [RequestMethod.PUT])
    fun updateUser(@RequestBody updateUserDTO: UpdateUserDTO): Boolean {
        log.debug("call admin updateUser : updateUserDTO = '$updateUserDTO")

        return when (updateUserDTO.authority) {
            Authority.ADMIN.toString() -> {
                userService.updateUser(updateUserDTO)
            }

            else -> {
                throw ResultCodeException(ResultCode.ERROR_USER_NOT_ADMIN, loglevel = Level.WARN)
            }
        }
    }

    @ApiRequestMapping("/users", method = [RequestMethod.DELETE])
    fun deleteUser(@RequestBody deleteUserDTO: DeleteUserDTO): Boolean {
        log.debug("call admin deleteUser : deleteUserDTO = '$deleteUserDTO")

        return when (deleteUserDTO.authority) {
            Authority.ADMIN.toString() -> {
                userService.deleteUser(deleteUserDTO)
            }

            else -> {
                throw ResultCodeException(ResultCode.ERROR_USER_NOT_ADMIN, loglevel = Level.WARN)
            }
        }
    }
}