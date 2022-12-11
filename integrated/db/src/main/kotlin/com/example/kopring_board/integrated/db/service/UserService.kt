package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.dto.user.DeleteUserDTO
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.mapper.UserMapper
import com.example.kopring_board.integrated.db.repository.UserRepository
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.util.Objects

@Service
class UserService(
    private val userMapper: UserMapper,
    private val userRepository: UserRepository
) {
    companion object {
        private val log = LogManager.getLogger()
    }

    fun getUsers(): List<User> {
        log.debug("getUsers")
        return userRepository.findAll()
    }

    fun getUser(id: String): User? {
        log.debug("getUser, id='$id'")

        var optionalUser = userRepository.findById(id)
        if (optionalUser.isPresent) {
            return optionalUser.get()
        } else {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.INFO)
        }
    }

    fun createUser(user: User): User? {
        log.debug("createUser. user=$user")

        if (userRepository.existsById(user.id)) {
            throw ResultCodeException(ResultCode.ERROR_USER_ALREADY_EXISTS, loglevel = Level.INFO)
        } else {
            try {
                return userRepository.save(user)
            }catch (e:Exception){
                log.error("create user failed. $user", e)
                throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
            }
        }
    }

    fun updateUser(user: User): Boolean? {
        log.debug("updateUser, user='$user'")

        if (userRepository.existsById(user.id)) {
            userRepository.save(user)
            return true
        } else {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.INFO)
        }
    }

    fun deleteUser(deleteUserDTO: DeleteUserDTO): Boolean? {
        log.debug("deleteUser, deleteUserDTO='$deleteUserDTO'")

        if (userRepository.existsById(deleteUserDTO.id!!)) {
            userRepository.deleteById(deleteUserDTO.id!!)
            return true
        } else {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.INFO)
        }
    }
}