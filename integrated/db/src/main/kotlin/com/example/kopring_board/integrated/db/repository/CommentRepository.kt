package com.example.kopring_board.integrated.db.repository

import com.example.kopring_board.integrated.db.entity.Comment
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CommentRepository : JpaRepository<Comment, Long?> {
    fun findByAuthorAndPostAndContentAndDeletedAtIsNotNull(author: User?, post: Post?, content: String): Optional<Comment>
    fun findByPostAndDeletedAtIsNotNull(post: Post?): List<Comment>
}