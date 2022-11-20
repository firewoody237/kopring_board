package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.mapper.UserMapper
import com.example.kopring_board.integrated.db.repository.UserRepository
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


}