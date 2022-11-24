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
        log.debug("getUser")

        var optionalUser = userRepository.findById(id)
        if (optionalUser.isPresent) {
            return optionalUser.get()
        } else {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.INFO)
        }
    }


}