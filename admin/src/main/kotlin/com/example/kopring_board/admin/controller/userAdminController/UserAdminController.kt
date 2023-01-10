package com.example.kopring_board.admin.controller.userAdminController

import com.example.kopring_board.admin.dto.AdminLogDTO
import com.example.kopring_board.admin.dto.AdminUserDeleteDTO
import com.example.kopring_board.admin.dto.AdminUserUpdateDTO
import com.example.kopring_board.admin.service.UserAdminService
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
    private val userAdminService: UserAdminService,
    private val userService: UserService
) {

    companion object {
        private val log = LogManager.getLogger()
    }

    //who
    @ApiRequestMapping("/users", method = [RequestMethod.PUT])
    fun updateUser(@RequestBody adminUserUpdateDTO: AdminUserUpdateDTO): Boolean {
        log.debug("call admin updateUser : adminUserUpdateDTO = '$adminUserUpdateDTO")

        val foundUser = userService.getUser(adminUserUpdateDTO.id)
        if (foundUser.authority != Authority.ADMIN) {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_ADMIN, loglevel = Level.WARN)
        }

        return userAdminService.updateUser(
            AdminLogDTO(
                userId = adminUserUpdateDTO.id,
                log = "Admin - User Update"
            ),
            adminUserUpdateDTO
        )
    }

    @ApiRequestMapping("/users", method = [RequestMethod.DELETE])
    fun deleteUser(@RequestBody adminUserDeleteDTO: AdminUserDeleteDTO): Boolean {
        log.debug("call admin deleteUser : adminUserDeleteDTO = '$adminUserDeleteDTO")

        val foundUser = userService.getUser(adminUserDeleteDTO.id)
        if (foundUser.authority != Authority.ADMIN) {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_ADMIN, loglevel = Level.WARN)
        }

        return userAdminService.deleteUser(
            AdminLogDTO(
                userId = adminUserDeleteDTO.id,
                log = "Admin - User Delete"
            ),
            adminUserDeleteDTO
        )
    }
}