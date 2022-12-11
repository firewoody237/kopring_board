package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.dto.post.DeletePostDTO
import com.example.kopring_board.integrated.db.dto.post.GetPostDTO
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.mapper.PostMapper
import com.example.kopring_board.integrated.db.repository.PostRepository
import com.example.kopring_board.integrated.db.repository.UserRepository
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*


@Service
class PostService(
    private val postMapper: PostMapper,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) {

    companion object {
        private val log = LogManager.getLogger()
    }

    fun getPosts(getPostDTO: GetPostDTO?): List<Post> {
        log.debug("getPosts, getPostDTO = '$getPostDTO'")

        //한번에 모든 Post를 조회할 일이 거의 없음
        var posts = postRepository.findByAuthorAndCategory(getPostDTO?.author, getPostDTO?.category)
        if (posts.isEmpty()) {
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.INFO)
        } else {
            return posts
        }
    }

    fun createPost(post: Post): Post?{
        log.debug("createPost, post='$post'")

        var optionalPost = postRepository.findByAuthorAndTitle(post.author, post.title)
        if (optionalPost.isPresent) {
            throw ResultCodeException(ResultCode.ERROR_POST_TITLE_WITH_USER_ALREADY_EXISTS, loglevel = Level.INFO)
        }
        return postRepository.save(post)
    }

    fun updatePost(post: Post): Boolean? {
        log.debug("updatePost, post='$post'")

        if (postRepository.existsById(post.id!!)) {
            postRepository.save(post)
            return true
        } else {
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.INFO)
        }
    }

    fun deletePost(deletePostDTO: DeletePostDTO): Boolean? {
        log.debug("deletePost deletePostDTO='$deletePostDTO'")

        val user = deletePostDTO.author
        val post = deletePostDTO.post

        //find by id
        var optionalUser = userRepository.findById(user.id)
        var optionalPost = postRepository.findById(post.id!!)
        log.info(optionalPost.get())

        //empty check return;
        if (!optionalUser.isPresent) {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.INFO)
        }
        if (!optionalPost.isPresent) {
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.INFO)
        }

        //author id check return;
        if (optionalUser.get().id != optionalPost.get().author?.id) {
            throw ResultCodeException(ResultCode.ERROR_REQUESTER_NOT_POST_AUTHOR, loglevel = Level.INFO)
        }

        optionalPost.get().deletedAt = LocalDateTime.now()
        postRepository.save(optionalPost.get())
        return true
    }
}