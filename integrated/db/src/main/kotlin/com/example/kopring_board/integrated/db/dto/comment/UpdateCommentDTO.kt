package com.example.kopring_board.integrated.db.dto.comment

data class UpdateCommentDTO(
    val id: Long,
    val content: String?,
    val userId: String?
)
