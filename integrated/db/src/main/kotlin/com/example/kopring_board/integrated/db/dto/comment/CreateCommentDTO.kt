package com.example.kopring_board.integrated.db.dto.comment

data class CreateCommentDTO(
    val content: String,
    val postId: Long,
    val userId: String
)
