package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.db.dto.heart.ToggleHeartDTO
import com.example.kopring_board.integrated.db.entity.Heart
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.entity.User

interface HeartService {
    fun getHeartPosts(userId: String): List<Heart>
    fun getHeartUsers(postId: Long): List<Heart>
    fun toggleHeart(postId: Long, toggleHeartDTO: ToggleHeartDTO)
}