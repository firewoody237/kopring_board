package com.example.kopring_board.api.controller.postController

data class CreatePostDTO(
    val authorId : String?,
    val title  : String?,
    val content : String?
)
