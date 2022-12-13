package com.example.kopring_board.integrated.db.dto.comment

import com.example.kopring_board.integrated.db.entity.Comment
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.entity.User

data class ModifiedCommentDTO(
    val author: User,
    val post: Post,
    val comment: Comment
)
