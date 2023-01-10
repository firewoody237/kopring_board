package com.example.kopring_board.integrated.db.repository

import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.post.Category
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface PostRepository : JpaRepository<Post, Long?> {

    //fun findByAuthorAndCategoryAndDeletedAtIsNotNull(author: User?, category: Category?): List<Post>
    //fun findByAuthorAndContent(author: User, content: String): Optional<Post>
    fun findByCategoryAndDeletedAtIsNotNull(category: Category, pageRequest: PageRequest) : List<Post>
    fun findByAuthorAndCategoryAndDeletedAtIsNotNull(author: User, category: Category, pageRequest: PageRequest): List<Post>
    fun findByTitleLikeAndCategoryAndDeletedAtIsNotNull(title: String, category: Category, pageRequest: PageRequest) : List<Post>
    fun findByContentLikeAndCategoryAndDeletedAtIsNotNull(title: String, category: Category, pageRequest: PageRequest) : List<Post>
    fun findAllByAuthorAndDeletedAtIsNotNull(author: User)
}