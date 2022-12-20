package com.example.kopring_board.integrated.db.dto.comment

data class DeleteCommentDTO(
    val id: Long,
    val postId: Long,
    val userId: String
)
