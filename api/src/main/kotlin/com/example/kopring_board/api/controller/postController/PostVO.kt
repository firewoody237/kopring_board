package com.example.kopring_board.api.controller.postController

data class PostVO(
    val id: Long,
    val authorId : String,
    val title: String,
    val content: String,
    val comment: List<Map<String, Any?>>,
    val commentCount :Long,
)
