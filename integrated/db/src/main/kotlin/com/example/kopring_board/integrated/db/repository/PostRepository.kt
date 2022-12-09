package com.example.kopring_board.integrated.db.repository

import com.example.kopring_board.integrated.db.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime


@Repository
interface PostRepository : JpaRepository<Post, Long?> {

    fun findTop100PostsByDeletedAtAfterOrderByCreatedAt(current: LocalDateTime?): List<Post>

}