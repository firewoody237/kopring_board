package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.db.dto.comment.CreateCommentDTO
import com.example.kopring_board.integrated.db.dto.comment.DeleteCommentDTO
import com.example.kopring_board.integrated.db.dto.comment.GetCommentDTO
import com.example.kopring_board.integrated.db.dto.comment.UpdateCommentDTO
import com.example.kopring_board.integrated.db.entity.Comment

interface CommentService {
    fun getPostComments(postId: Long): List<Comment>
    fun createComment(postId: Long, createCommentDTO: CreateCommentDTO): Comment
    fun updateComment(postId: Long, updateCommentDTO: UpdateCommentDTO): Boolean
    fun deleteComment(postId: Long, deleteCommentDTO: DeleteCommentDTO): Boolean
}