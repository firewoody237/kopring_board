package com.example.kopring_board.api.controller.commentController

import com.example.kopring_board.integrated.db.dto.comment.ModifiedCommentDTO
import com.example.kopring_board.integrated.db.entity.Comment
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.service.CommentService
import com.example.kopring_board.integrated.webservice.api.ApiRequestMapping
import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/comment/v1")
class CommentController(
    private val commentService: CommentService
) {

    companion object {
        private val log = LogManager.getLogger()
    }

    @ApiRequestMapping("/comments", method = [RequestMethod.GET])
    fun getComments(@RequestBody post: Post): List<Comment>? {
        log.debug("getComments, post='$post'")
        return commentService.getComments(post)
    }

    @ApiRequestMapping("/comments", method = [RequestMethod.POST])
    fun createComment(@RequestBody modifiedCommentDTO: ModifiedCommentDTO): Comment? {
        log.debug("createComment, modifiedCommentDTO = '$modifiedCommentDTO'")
        return commentService.createComment(modifiedCommentDTO)
    }

    @ApiRequestMapping("/comments", method = [RequestMethod.PATCH, RequestMethod.PUT])
    fun updateComment(@RequestBody modifiedCommentDTO: ModifiedCommentDTO): Comment? {
        log.debug("updateComment, modifiedCommentDTO = '$modifiedCommentDTO'")
        return commentService.updateComment(modifiedCommentDTO)
    }

    @ApiRequestMapping("/comments", method = [RequestMethod.DELETE])
    fun deleteComment(@RequestBody modifiedCommentDTO: ModifiedCommentDTO): Boolean? {
        log.debug("deleteComment, mdofiedCommentDTO = '$modifiedCommentDTO'")
        return commentService.deleteComment(modifiedCommentDTO)
    }
}