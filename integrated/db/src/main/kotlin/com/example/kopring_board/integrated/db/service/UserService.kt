package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.mapper.UserMapper
import com.example.kopring_board.integrated.db.repository.UserRepository
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service

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

        val optionalUser = userRepository.findById(id)
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
            } catch (e:Exception) {
                log.error("create user failed. $user", e)
                throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
            }
        }
    }

    fun updateUser(user: User): User? {
        log.debug("updateUser, user='$user'")
        val optionalUser = userRepository.findById(user.id)

        if (optionalUser.isPresent) {
            try {
                if (optionalUser.get().name?.isNotEmpty() == true) {
                    optionalUser.get().name = user.name
                }
                if (optionalUser.get().email?.isNotEmpty() == true) {
                    optionalUser.get().email = user.email
                }
                return optionalUser.get()
            } catch (e:Exception) {
                log.error("update user failed. $user", e)
                throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.ERROR)
            }
        } else {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.INFO)
        }
    }

    fun deleteUser(user: User): Boolean? {
        log.debug("deleteUser, user='$user'")

        if (userRepository.existsById(user.id)) {
            try {
                userRepository.deleteById(user.id)
                return true
            } catch (e: Exception) {
                throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.ERROR)
            }
        } else {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.INFO)
        }
    }
}