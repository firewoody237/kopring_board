package com.example.kopring_board.admin.service

import com.example.kopring_board.admin.dto.AdminLogDTO
import com.example.kopring_board.admin.dto.AdminUserDeleteDTO
import com.example.kopring_board.admin.dto.AdminUserUpdateDTO
import com.example.kopring_board.integrated.db.dto.user.DeleteUserDTO
import com.example.kopring_board.integrated.db.dto.user.UpdateUserDTO
import com.example.kopring_board.integrated.db.entity.AdminLog
import com.example.kopring_board.integrated.db.repository.AdminLogRepository
import com.example.kopring_board.integrated.db.repository.UserRepository
import com.example.kopring_board.integrated.db.service.UserService
import com.example.kopring_board.integrated.db.service.impl.UserServiceImpl
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service

@Service
class UserAdminService(
    private val userService: UserService,
    private val adminLogRepository: AdminLogRepository
) {
    companion object {
        private val log = LogManager.getLogger()
    }

    fun updateUser(adminLogDTO: AdminLogDTO, adminUserUpdateDTO: AdminUserUpdateDTO): Boolean {
        log.debug("updateUser : updateUserDTO = '$adminLogDTO'")

        addAdminActionLog(adminLogDTO.log, adminLogDTO.userId)

        return userService.updateUser(
            UpdateUserDTO(
                id = adminUserUpdateDTO.id,
                name = adminUserUpdateDTO.name,
                email = adminUserUpdateDTO.email
            )
        )
    }

    fun deleteUser(adminLogDTO: AdminLogDTO, adminUserDeleteDTO: AdminUserDeleteDTO): Boolean {
        log.debug("updateUser : updateUserDTO = '$adminLogDTO'")

        addAdminActionLog(adminLogDTO.log, adminLogDTO.userId)

        return userService.deleteUser(
            DeleteUserDTO(
                id = adminUserDeleteDTO.id
            )
        )
    }

    fun addAdminActionLog(calledFunctionName: String, userId: String) {
        adminLogRepository.save(
            AdminLog(
                userId = userId,
                log = "Admin $userId called function $calledFunctionName"
            )
        )
    }
}