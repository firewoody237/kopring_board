package com.example.kopring_board.api.controller.boardController

data class CreatePostDTO(
    val authorId : String?,
    val title  : String?,
    val content : String?
)
