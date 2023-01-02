package com.example.kopring_board.integrated.db.dto.post

import com.example.kopring_board.integrated.post.Category

data class UpdatePostDTO(
    val id: Long,
    val authorId: String,
    var title: String?,
    var content: String?,
    var category: String?
)
