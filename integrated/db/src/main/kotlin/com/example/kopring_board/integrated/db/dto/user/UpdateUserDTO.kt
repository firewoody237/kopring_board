package com.example.kopring_board.integrated.db.dto.user

import com.example.kopring_board.integrated.user.Authority

data class UpdateUserDTO(
    val id: String?,
    var name: String?,
    var email: String?,
)