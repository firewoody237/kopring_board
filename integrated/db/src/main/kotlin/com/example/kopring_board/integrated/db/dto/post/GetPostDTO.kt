package com.example.kopring_board.integrated.db.dto.post

import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.post.Category
import java.time.LocalDateTime

data class GetPostDTO(
    val category: Category,
    val author: User,
)