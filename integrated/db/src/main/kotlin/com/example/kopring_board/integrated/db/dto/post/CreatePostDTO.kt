package com.example.kopring_board.integrated.db.dto.post

data class CreatePostDTO(
    var authorId: String,
    var title: String?,
    var content: String?,
    var category: String?
)