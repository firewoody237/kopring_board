package com.example.kopring_board.integrated.db.dto.post

data class ModifiedPostDTO2(
    var authorId: String,
    var postId: Long,
    var title: String?,
    var content: String?,
    var category: String?
)
