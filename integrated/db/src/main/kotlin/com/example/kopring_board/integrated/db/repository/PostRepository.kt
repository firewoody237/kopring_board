package com.example.kopring_board.integrated.db.repository

import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.post.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*


@Repository
interface PostRepository : JpaRepository<Post, Long?> {

    fun findByAuthorAndCategory(author: User?, category: Category?): List<Post>
    abstract fun findByAuthorAndTitle(author: User?, title: String?): Optional<Post>
}