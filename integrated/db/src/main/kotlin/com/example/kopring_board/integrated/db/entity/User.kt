package com.example.kopring_board.integrated.db.entity

import com.example.kopring_board.integrated.user.Grade
import com.fasterxml.jackson.annotation.JsonIgnore
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id
    val id: String = "",
    @Column(nullable = false, length = 15)
    val name: String? = null,
    @Column(nullable = false)
    val email: String? = null,

    @CreatedDate
    val createdAt: LocalDateTime? = null,
    @LastModifiedDate
    val modifiedAt: LocalDateTime? = null,
    ) {

    @JsonIgnore
    @Column
    @Convert(converter = PasswordConverter::class)
    var password: String? =null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "User(id=$id, name='$name', email='$email', createdAt='$createdAt', modifiedAt='$modifiedAt')"
    }
}