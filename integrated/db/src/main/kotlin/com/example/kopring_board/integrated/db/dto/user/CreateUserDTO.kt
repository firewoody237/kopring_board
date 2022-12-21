package com.example.kopring_board.integrated.db.dto.user

import com.example.kopring_board.integrated.db.entity.User

data class CreateUserDTO(
    val id: String?,
    var name: String?,
    var email: String?,
    var password: String?,
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
