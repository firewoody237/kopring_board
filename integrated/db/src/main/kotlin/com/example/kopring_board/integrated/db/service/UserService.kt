package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.db.dto.user.*
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.user.Grade

interface UserService {
    fun getUsers(getUserDTO: GetUserDTO): List<User>
    fun getUser(id: String): User
    fun createUser(createUserDTO: CreateUserDTO): User
    fun updateUser(updateUserDTO: UpdateUserDTO): Boolean
    fun deleteUser(deleteUserDTO: DeleteUserDTO): Boolean
    //TODO: Grade와 Point는 Admin 쪽에 있어야 할 것 같은데?
    //fun updateUserGrade(updateUserGradeDTO: UpdateUserGradeDTO): Grade
    //fun updateUserPoint(updateUserPointDTO: UpdateUserPointDTO): Int
}