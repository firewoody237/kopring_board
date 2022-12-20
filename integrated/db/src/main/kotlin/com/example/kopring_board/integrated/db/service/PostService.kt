package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.db.dto.post.CreatePostDTO
import com.example.kopring_board.integrated.db.dto.post.DeletePostDTO
import com.example.kopring_board.integrated.db.dto.post.GetPostDTO
import com.example.kopring_board.integrated.db.dto.post.UpdatePostDTO
import com.example.kopring_board.integrated.db.entity.Post

interface PostService {
    fun getPosts(getPostDTO: GetPostDTO): List<Post>
    fun getPost(id: Long): Post
    fun createPost(createPostDTO: CreatePostDTO): Post
    fun updatePost(updatePostDTO: UpdatePostDTO): Boolean
    fun deletePost(deletePostDTO: DeletePostDTO): Boolean
}