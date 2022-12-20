package com.example.kopring_board.integrated.db.dto.post

data class UpdatePostDTO(
    val id: Long,
    val authorId: String,
    var title: String?,
    var content: String?,
    var category: String?
)
