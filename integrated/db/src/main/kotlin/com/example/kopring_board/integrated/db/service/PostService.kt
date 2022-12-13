package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.dto.post.ModifiedPostDTO
import com.example.kopring_board.integrated.db.dto.post.GetPostDTO
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.mapper.PostMapper
import com.example.kopring_board.integrated.db.repository.PostRepository
import com.example.kopring_board.integrated.db.repository.UserRepository
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.time.LocalDateTime


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
        //저자와 카테고리로 조회(delete 되지 않은 것)
        val posts = postRepository.findByAuthorAndCategoryAndDeletedAtIsNotNull(getPostDTO?.author, getPostDTO?.category)
        if (posts.isEmpty()) {
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.INFO)
        } else {
            return posts
        }
    }

    fun getPost(id: Long): Post? {
        log.debug("getPost, id = '$id'")

        val post = postRepository.findById(id)
        if (post.isPresent) {
            return post.get()
        } else {
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.INFO)
        }
    }

    fun createPost(modifiedPostDTO: ModifiedPostDTO): Post?{
        log.debug("createPost, modifiedPost='$modifiedPostDTO'")

        val user = modifiedPostDTO.author
        val post = modifiedPostDTO.post

        val optionalUser = userRepository.findById(user.id)
        if (!optionalUser.isPresent) {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.INFO)
        }

        if (optionalUser.get().id != post.author?.id) {
            throw ResultCodeException(ResultCode.ERROR_REQUESTER_NOT_POST_AUTHOR, loglevel = Level.INFO)
        }

        val optionalPost = postRepository.findByAuthorAndContent(optionalUser.get(), post.content)
        if (optionalPost.isPresent) {
            throw ResultCodeException(ResultCode.ERROR_POST_ALREADY_EXISTS, loglevel = Level.INFO)
        } else {
            try {
                return postRepository.save(optionalPost.get())
            } catch (e:Exception) {
                log.error("create post failed. $post", e)
                throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
            }
        }
    }

    fun updatePost(modifiedPost: ModifiedPostDTO): Post? {
        log.debug("updatePost, modifiedPost='$modifiedPost'")
        val user = modifiedPost.author
        val post = modifiedPost.post

        if (user.id != post.author?.id) {
            throw ResultCodeException(ResultCode.ERROR_REQUESTER_NOT_POST_AUTHOR, loglevel = Level.INFO)
        }

        val optionalPost = postRepository.findById(post.id)

        if (optionalPost.isPresent) {
            try {
                if (post.title.isNotEmpty()) {
                    optionalPost.get().title = post.title
                }
                if (post.content.isNotEmpty()) {
                    optionalPost.get().content = post.content
                }
                return optionalPost.get()
            } catch(e: Exception) {
                log.error("update post failed. $modifiedPost")
                throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.ERROR)
            }
        } else {
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.INFO)
        }
    }

    fun deletePost(modifiedPostDTO: ModifiedPostDTO): Boolean? {
        log.debug("deletePost deletePostDTO='$modifiedPostDTO'")

        val user = modifiedPostDTO.author
        val post = modifiedPostDTO.post

        //find by id
        val optionalUser = userRepository.findById(user.id)
        val optionalPost = postRepository.findById(post.id)

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
        return true
    }
}