package com.example.kopring_board.integrated.db.dto.user

import com.example.kopring_board.integrated.user.Authority

data class DeleteUserDTO(
    val id: String?,
    var authority: String = Authority.NORMAL.toString(),
)
