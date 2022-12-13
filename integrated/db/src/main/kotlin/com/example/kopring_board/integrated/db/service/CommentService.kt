package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.dto.comment.ModifiedCommentDTO
import com.example.kopring_board.integrated.db.entity.Comment
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.repository.CommentRepository
import com.example.kopring_board.integrated.db.repository.PostRepository
import com.example.kopring_board.integrated.db.repository.UserRepository
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class CommentService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
) {

    companion object {
        private val log = LogManager.getLogger()
    }

    fun getComments(post: Post): List<Comment>? {
        return commentRepository.findByPostAndDeletedAtIsNotNull(post)
    }

    fun createComment(modifiedCommentDTO: ModifiedCommentDTO): Comment? {
        log.debug("createComment, modifiedCommentDTO='$modifiedCommentDTO'")

        val user = modifiedCommentDTO.author
        val post = modifiedCommentDTO.post
        val comment = modifiedCommentDTO.comment

        if (comment.author?.id != user.id ||
                comment.author.id != post.author?.id) {
            throw ResultCodeException(ResultCode.ERROR_REQUESTER_NOT_POST_AUTHOR, loglevel = Level.INFO)
        }

        if (commentRepository.findByAuthorAndPostAndContentAndDeletedAtIsNotNull(user, post, comment.content).isPresent) {
            throw ResultCodeException(ResultCode.ERROR_COMMENT_ALREADY_EXIST, loglevel = Level.INFO)
        }

        return commentRepository.save(comment)
    }

    fun updateComment(modifiedCommentDTO: ModifiedCommentDTO): Comment? {
        log.debug("updateComment, modifiedCommentDTO = $modifiedCommentDTO")
        val comment = modifiedCommentDTO.comment

        if (modifiedCommentDTO.author.id != comment.author?.id ||
                modifiedCommentDTO.author.id != modifiedCommentDTO.post.author?.id) {
            throw ResultCodeException(ResultCode.ERROR_REQUESTER_NOT_POST_AUTHOR, loglevel = Level.INFO)
        }

        val optionalComment = commentRepository.findById(modifiedCommentDTO.comment.id)

        if (optionalComment.isPresent) {
            try {
                if (comment.content.isNotEmpty()) {
                    optionalComment.get().content = comment.content
                }
                return optionalComment.get()
            } catch(e: Exception) {
                log.error("update post failed. $modifiedCommentDTO")
                throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.ERROR)
            }
        } else {
            throw ResultCodeException(ResultCode.ERROR_COMMENT_NOT_EXIST, loglevel = Level.INFO)
        }
    }

    fun deleteComment(modifiedCommentDTO: ModifiedCommentDTO): Boolean? {
        log.debug("deleteComment deleteCommentDTO = '$modifiedCommentDTO'")
        //comment의 user와 authordml user가 같은지 검증
        val optionalUser = userRepository.findById(modifiedCommentDTO.author.id)
        val optionalPost = postRepository.findById(modifiedCommentDTO.post.id)
        val optionalComment = commentRepository.findById(modifiedCommentDTO.comment.id)

        if (optionalUser.get().id != optionalComment.get().author?.id) {
            throw ResultCodeException(ResultCode.ERROR_COMMENT_AUTHOR_NOT_MATCHED_WITH_USER, loglevel = Level.INFO)
        }

        //comment와 post를 검증해서 delete Date 추가
        if (optionalComment.get().post?.id != optionalPost.get().id) {
            throw ResultCodeException(ResultCode.ERROR_COMMENT_NOT_MATCHED_WITH_POST, loglevel = Level.INFO)
        }

        optionalComment.get().deletedAt = LocalDateTime.now()
        return true
    }
}