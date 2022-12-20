package com.example.kopring_board.integrated.db.repository

import com.example.kopring_board.integrated.db.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional


@Repository
interface UserRepository : JpaRepository<User, String> {

    fun findByIdLikeAndNameLike(id: String, name: String): List<User>
    fun findByIdLike(id: String): List<User>
    fun findByNameLike(name: String): List<User>
}