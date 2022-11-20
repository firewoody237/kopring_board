package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.mapper.UserMapper
import com.example.kopring_board.integrated.db.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userMapper: UserMapper,
    private val userRepository: UserRepository
) {

    fun getUsers(): List<User> {
        return userRepository.findAll()
    }


}