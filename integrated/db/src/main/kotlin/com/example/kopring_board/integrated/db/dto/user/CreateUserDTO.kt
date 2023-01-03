package com.example.kopring_board.integrated.db.dto.user

import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.user.Authority

data class CreateUserDTO(
    val id: String?,
    var name: String?,
    var email: String?,
    var password: String?,
    var authority: String = Authority.NORMAL.toString(),
) {
    fun toEntity(): User {
        val aUser = User(
            id = this.id!!,
            name = this.name,
            email = this.email,
        )
        aUser.password = this.password
        return aUser
    }
}
