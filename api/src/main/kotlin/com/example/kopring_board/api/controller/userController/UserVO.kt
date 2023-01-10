package com.example.kopring_board.api.controller.userController

data class UserVO(
    val id: String,
    val name: String,
    val email: String,
    val grade: String,
    val point: Long,
)
