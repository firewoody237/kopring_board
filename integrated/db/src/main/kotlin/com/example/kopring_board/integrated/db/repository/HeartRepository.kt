package com.example.kopring_board.integrated.db.repository

import com.example.kopring_board.integrated.db.entity.Heart
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface HeartRepository: JpaRepository<Heart, Long?> {
    fun findByUser(user: User): List<Heart>
    fun findByUserAndPost(user: User, post: Post): Heart?
}