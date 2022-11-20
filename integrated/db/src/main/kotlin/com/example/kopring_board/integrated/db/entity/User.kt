package com.example.kopring_board.integrated.db.entity

import javax.persistence.*

@Entity
@Table(name = "user")
class User {

    @Id
    var id: String? = null
    @Column
    var name: String? = null
    @Column
    var email: String? = null

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
        return "User(id=$id, name='$name', email='$email')"
    }
}