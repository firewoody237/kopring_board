package com.example.kopring_board.integrated.db.service.impl

import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.dto.comment.*
import com.example.kopring_board.integrated.db.entity.Comment
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.repository.CommentRepository
import com.example.kopring_board.integrated.db.repository.PostRepository
import com.example.kopring_board.integrated.db.repository.UserRepository
import com.example.kopring_board.integrated.db.service.CommentService
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class CommentServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
): CommentService {

    companion object {
        private val log = LogManager.getLogger()
    }

    override fun getPostComments(postId: Long): List<Comment> {
        val optionalPost = postRepository.findById(postId)
        if (optionalPost.isEmpty) {
            throw ResultCodeException(
                ResultCode.ERROR_POST_NOT_EXIST,
                loglevel = Level.ERROR
            )
        }

        return commentRepository.findByPostAndDeletedAtIsNotNull(
            Post(
                id = postId
            )
        )
    }



    override fun createComment(postId: Long, createCommentDTO: CreateCommentDTO): Comment {
        log.debug("createComment, createCommentDTO='$createCommentDTO'")

        val optionalPost = postRepository.findById(postId)
        if (optionalPost.isEmpty) {
            throw ResultCodeException(
                ResultCode.ERROR_POST_NOT_EXIST,
                loglevel = Level.ERROR
            )
        }

        val optionalUser = userRepository.findById(createCommentDTO.userId)
        if (optionalUser.isEmpty) {
            throw ResultCodeException(
                ResultCode.ERROR_USER_NOT_EXISTS,
                loglevel = Level.ERROR
            )
        }

        if (optionalPost.get().author?.id != optionalUser.get().id) {
            throw ResultCodeException(ResultCode.ERROR_REQUESTER_NOT_POST_AUTHOR, loglevel = Level.INFO)
        }

        if (createCommentDTO.content.isEmpty()) {
            throw ResultCodeException(
                ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.INFO
            )
        }

        return commentRepository.save(
            Comment(
                content = createCommentDTO.content,
                post = optionalPost.get(),
                author = optionalUser.get()
            )
        )
    }

    override fun updateComment(postId: Long, updateCommentDTO: UpdateCommentDTO): Boolean {
        log.debug("updateComment, updateCommentDTO = $updateCommentDTO")
        val optionalPost = postRepository.findById(postId)
        if (optionalPost.isEmpty) {
            throw ResultCodeException(
                ResultCode.ERROR_POST_NOT_EXIST,
                loglevel = Level.ERROR
            )
        }

        val optionalUser = userRepository.findById(updateCommentDTO.userId)
        if (optionalUser.isEmpty) {
            throw ResultCodeException(
                ResultCode.ERROR_USER_NOT_EXISTS,
                loglevel = Level.ERROR
            )
        }

        val optionalComment = commentRepository.findById(updateCommentDTO.id)
        if (optionalComment.isEmpty) {
            throw ResultCodeException(
                ResultCode.ERROR_COMMENT_NOT_EXIST,
                loglevel = Level.ERROR
            )
        }

        /*if (optionalPost.get().author?.id != optionalUser.get().id) {
            throw ResultCodeException(ResultCode.ERROR_REQUESTER_NOT_POST_AUTHOR, loglevel = Level.INFO)
        }*/

        if (optionalComment.get().author?.id != optionalUser.get().id) {
            throw ResultCodeException(
                ResultCode.ERROR_COMMENT_AUTHOR_NOT_MATCHED_WITH_USER,
                loglevel = Level.ERROR
            )
        }

        if (optionalComment.get().post?.id != optionalPost.get().id) {
            throw ResultCodeException(
                ResultCode.ERROR_COMMENT_NOT_MATCHED_WITH_POST,
                loglevel = Level.ERROR
            )
        }

        val reply = optionalComment.get()
        reply.content = updateCommentDTO.content
        commentRepository.save(
            reply
        )
        return true
    }

    override fun deleteComment(postId: Long, deleteCommentDTO: DeleteCommentDTO): Boolean {
        log.debug("deleteComment deleteCommentDTO = '$deleteCommentDTO'")
        val optionalPost = postRepository.findById(postId)
        if (optionalPost.isEmpty) {
            throw ResultCodeException(
                ResultCode.ERROR_POST_NOT_EXIST,
                loglevel = Level.ERROR
            )
        }

        val optionalUser = userRepository.findById(deleteCommentDTO.userId)
        if (optionalUser.isEmpty) {
            throw ResultCodeException(
                ResultCode.ERROR_USER_NOT_EXISTS,
                loglevel = Level.ERROR
            )
        }

        val optionalReply = commentRepository.findById(deleteCommentDTO.id)
        if (optionalReply.isEmpty) {
            throw ResultCodeException(
                ResultCode.ERROR_COMMENT_NOT_EXIST,
                loglevel = Level.ERROR
            )
        }

        /*if (optionalPost.get().author?.id != optionalUser.get().id) {
            throw ResultCodeException(ResultCode.ERROR_REQUESTER_NOT_POST_AUTHOR, loglevel = Level.INFO)
        }*/

        if (optionalReply.get().author?.id != optionalUser.get().id) {
            throw ResultCodeException(
                ResultCode.ERROR_COMMENT_AUTHOR_NOT_MATCHED_WITH_USER,
                loglevel = Level.ERROR
            )
        }

        if (optionalReply.get().post?.id != optionalPost.get().id) {
            throw ResultCodeException(
                ResultCode.ERROR_COMMENT_NOT_MATCHED_WITH_POST,
                loglevel = Level.ERROR
            )
        }

        val reply = optionalReply.get()
        reply.deletedAt = LocalDateTime.now()
        commentRepository.save(
            reply
        )
        return true
    }
}