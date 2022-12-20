package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.db.dto.post.CreatePostDTO
import com.example.kopring_board.integrated.db.dto.post.DeletePostDTO
import com.example.kopring_board.integrated.db.dto.post.GetPostDTO
import com.example.kopring_board.integrated.db.dto.post.UpdatePostDTO
import com.example.kopring_board.integrated.db.entity.Post
import org.springframework.data.domain.PageRequest

interface PostService {
    fun getPosts(getPostDTO: GetPostDTO, pageRequest: PageRequest): List<Post>
    fun getPost(id: Long): Post
    fun createPost(createPostDTO: CreatePostDTO): Post
    fun updatePost(updatePostDTO: UpdatePostDTO): Boolean
    fun deletePost(deletePostDTO: DeletePostDTO): Boolean
}