package com.example.kopring_board.integrated.db.dto.post

import com.example.kopring_board.integrated.post.Category


data class GetPostDTO(
    val category: String = Category.UNCATEGORIZED.toString(),
    val authorId: String?,
    val title: String?,
    val content: String?,
)