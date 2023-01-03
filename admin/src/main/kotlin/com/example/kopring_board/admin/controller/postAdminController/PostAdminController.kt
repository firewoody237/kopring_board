package com.example.kopring_board.admin.controller.postAdminController

import com.example.kopring_board.integrated.db.service.PostService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/post/v1")
class PostAdminController(
    private val postService: PostService
) {
}