package com.example.kopring_board.integrated.db.dto.post

import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.entity.User

data class DeletePostDTO(
    var author: User,
    var post: Post,
)
