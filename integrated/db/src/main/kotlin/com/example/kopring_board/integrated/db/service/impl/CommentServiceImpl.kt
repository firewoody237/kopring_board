package com.example.kopring_board.integrated.db.service.impl

import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.dto.comment.*
import com.example.kopring_board.integrated.db.entity.Comment
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.repository.CommentRepository
import com.example.kopring_board.integrated.db.service.CommentService
import com.example.kopring_board.integrated.db.service.PostService
import com.example.kopring_board.integrated.db.service.UserService
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class CommentServiceImpl(
    private val userService: UserService,
    private val postService: PostService,
    private val commentRepository: CommentRepository,
): CommentService {

    companion object {
        private val log = LogManager.getLogger()
    }

    //게시글의 댓글들 검색
    override fun getPostComments(postId: Long): List<Comment> {
        val foundPost = postService.getPost(postId)

        try {
            return commentRepository.findByPostAndDeletedAtIsNotNull(
                Post(
                    id = postId
                )
            )
        } catch(e: Exception) {
            throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.ERROR)
        }
    }



    override fun createComment(postId: Long, createCommentDTO: CreateCommentDTO): Comment {
        log.debug("createComment, createCommentDTO='$createCommentDTO'")

        if (createCommentDTO.userId == null || createCommentDTO.content == null) {
            throw ResultCodeException(ResultCode.ERROR_PARAMETER_NOT_EXISTS, loglevel = Level.WARN)
        }

        val foundUser = userService.getUser(createCommentDTO.userId)
        val foundPost = postService.getPost(postId)

        return commentRepository.save(
            Comment(
                content = createCommentDTO.content,
                post = foundPost,
                author = foundUser
            )
        )
    }

    override fun updateComment(postId: Long, updateCommentDTO: UpdateCommentDTO): Boolean {
        log.debug("updateComment, updateCommentDTO = $updateCommentDTO")

        if (updateCommentDTO.userId == null || updateCommentDTO.content == null) {
            throw ResultCodeException(ResultCode.ERROR_PARAMETER_NOT_EXISTS, loglevel = Level.WARN)
        }

        val foundUser = userService.getUser(updateCommentDTO.userId)
        val foundPost = postService.getPost(postId)

        val optionalComment = commentRepository.findById(updateCommentDTO.id)
        if (optionalComment.isEmpty) {
            throw ResultCodeException(
                ResultCode.ERROR_COMMENT_NOT_EXIST,
                loglevel = Level.ERROR
            )
        }

        if (optionalComment.get().author?.id != foundUser.id) {
            throw ResultCodeException(
                ResultCode.ERROR_COMMENT_AUTHOR_NOT_MATCHED_WITH_USER,
                loglevel = Level.ERROR
            )
        }

        if (optionalComment.get().post?.id != foundPost.id) {
            throw ResultCodeException(
                ResultCode.ERROR_COMMENT_NOT_MATCHED_WITH_POST,
                loglevel = Level.ERROR
            )
        }

        val comment = optionalComment.get()
        if (comment.content != updateCommentDTO.content) {
            comment.content = updateCommentDTO.content
            try {
                commentRepository.save(comment)
                return true
            } catch(e: Exception) {
                throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.ERROR)
            }
        } else {
            throw ResultCodeException(ResultCode.ERROR_NO_DIFFERENCE, loglevel = Level.WARN)
        }
    }

    override fun deleteComment(postId: Long, deleteCommentDTO: DeleteCommentDTO): Boolean {
        log.debug("deleteComment deleteCommentDTO = '$deleteCommentDTO'")

        if (deleteCommentDTO.userId == null) {
            throw ResultCodeException(ResultCode.ERROR_PARAMETER_NOT_EXISTS, loglevel = Level.WARN)
        }

        val foundPost = postService.getPost(postId)
        val foundUser = userService.getUser(deleteCommentDTO.userId)

        val optionalComment = commentRepository.findById(deleteCommentDTO.id)
        if (optionalComment.isEmpty) {
            throw ResultCodeException(
                ResultCode.ERROR_COMMENT_NOT_EXIST,
                loglevel = Level.ERROR
            )
        }

        if (optionalComment.get().author?.id != foundUser.id) {
            throw ResultCodeException(
                ResultCode.ERROR_COMMENT_AUTHOR_NOT_MATCHED_WITH_USER,
                loglevel = Level.ERROR
            )
        }

        if (optionalComment.get().post?.id != foundPost.id) {
            throw ResultCodeException(
                ResultCode.ERROR_COMMENT_NOT_MATCHED_WITH_POST,
                loglevel = Level.ERROR
            )
        }

        val comment = optionalComment.get()
        try {
            comment.deletedAt = LocalDateTime.now()
            commentRepository.save(
                comment
            )
            return true
        } catch (e: Exception) {
            throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.ERROR)
        }
    }
}