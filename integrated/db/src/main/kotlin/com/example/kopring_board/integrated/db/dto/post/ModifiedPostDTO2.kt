package com.example.kopring_board.integrated.db.dto.post

data class ModifiedPostDTO2(
    val authorId: String,
    val postId: Long,
    val title: String?,
    val content: String?,
    val category: String?
)
