package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.db.dto.common.PageDTO
import com.example.kopring_board.integrated.db.dto.user.*
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.user.Grade
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface UserService {
    fun getUsers(getUserDTO: GetUserDTO, pageDTO: PageDTO): Page<User>
    fun getUser(id: String): User
    fun createUser(createUserDTO: CreateUserDTO): User
    fun updateUser(updateUserDTO: UpdateUserDTO): Boolean
    fun deleteUser(deleteUserDTO: DeleteUserDTO): Boolean
    //TODO: 추후 Admin 기능
    //fun updateUserGrade(updateUserGradeDTO: UpdateUserGradeDTO): Grade
    //fun updateUserPoint(updateUserPointDTO: UpdateUserPointDTO): Int
}