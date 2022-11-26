package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.repository.UserRepository
import com.example.kopring_board.integrated.user.Grade
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(classes=[UserRepository.class])
@Transactional
internal class UserServiceTest(
    @Autowired val userRepository: UserRepository
){

    @Test
    fun getUsers() {
        //given
        var user1 = User("apple", "Steve", "apple@gmail.com", Grade.GOLD, 1000)
        var user2 = User("micro", "Bill", "micro@gmail.com", Grade.GREEN, 500)

        //when
        userRepository.save(user1)
        userRepository.save(user2)

        //then
        Assertions.assertThat(userRepository.findAll().size).isEqualTo(2)
    }

    @Test
    fun getUser() {
    }
}