package com.example.kopring_board.admin.dto

import com.example.kopring_board.integrated.user.Authority

data class AdminUserDeleteDTO(
    val id: String,
    var name: String?,
    var email: String?,
    var authority: String? = Authority.NORMAL.toString()
)
