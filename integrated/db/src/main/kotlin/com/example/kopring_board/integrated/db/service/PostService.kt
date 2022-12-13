package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.dto.post.CreatePostDTO
import com.example.kopring_board.integrated.db.dto.post.GetPostDTO
import com.example.kopring_board.integrated.db.dto.post.ModifiedPostDTO2
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.mapper.PostMapper
import com.example.kopring_board.integrated.db.repository.PostRepository
import com.example.kopring_board.integrated.db.repository.UserRepository
import com.example.kopring_board.integrated.post.Category
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
        val posts =
            postRepository.findByAuthorAndCategoryAndDeletedAtIsNotNull(getPostDTO?.author, getPostDTO?.category)
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

    fun createPost(createPostDTO: CreatePostDTO): Post? {
        log.debug("createPost, createPostDTO='$createPostDTO'")

        val authorId = createPostDTO.authorId
        val optionalUser = userRepository.findById(authorId)
        if (optionalUser.isEmpty) {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.WARN)
        }

        when {
            createPostDTO.title.isNullOrEmpty() -> {
                throw ResultCodeException(
                    ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                    loglevel = Level.WARN,
                    message = "title이 없습니다."
                )
            }

            createPostDTO.content.isNullOrEmpty() -> {
                throw ResultCodeException(
                    ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                    loglevel = Level.WARN,
                    message = "content가 없습니다."
                )
            }

            createPostDTO.category.isNullOrEmpty() -> {
                throw ResultCodeException(
                    ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                    loglevel = Level.WARN,
                    message = "category가 없습니다."
                )
            }
        }

        val author = optionalUser.get()

        val post = Post(
            title = createPostDTO.title!!,
            content = createPostDTO.content!!,
            category = Category.valueOf(createPostDTO.category!!),
            author = author
        )

        return try {
            postRepository.save(post)
        } catch (e: Exception) {
            throw ResultCodeException(
                ResultCode.ERROR_DB,
                loglevel = Level.WARN
            )
        }
    }

    fun updatePost(modifiedPost: ModifiedPostDTO2): Post? {
        log.debug("updatePost, modifiedPost='$modifiedPost'")
        val authorID = modifiedPost.authorId
        val postID = modifiedPost.postId

        val optionalPost = postRepository.findById(postID)
        if (optionalPost.isEmpty) {
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.WARN)
        }

        val post = optionalPost.get()
        if (post.author?.id != authorID) {
            throw ResultCodeException(ResultCode.ERROR_REQUESTER_NOT_POST_AUTHOR, loglevel = Level.WARN)
        }

        var isChange = false

        if (modifiedPost.title?.isNotEmpty() == true) {
            post.title = modifiedPost.title!!
            isChange = true
        }

        if (modifiedPost.content?.isNotEmpty() == true) {
            post.content = modifiedPost.content!!
            isChange = true
        }

        if (modifiedPost.category?.isNotEmpty() == true) {
            val category = Category.valueOf(modifiedPost.category!!)
            //post.category = category
            isChange = true
        }


        return try {
            when {
                isChange -> {
                    postRepository.save(post)
                }

                else -> {
                    post
                }
            }
        } catch (e: Exception) {
            throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.ERROR)
        }
    }

    fun deletePost(modifiedPostDTO2: ModifiedPostDTO2): Boolean? {
        log.debug("deletePost modifiedPostDTO2='$modifiedPostDTO2'")

        val authorId = modifiedPostDTO2.authorId

        //find by id
        val optionalUser = userRepository.findById(authorId)
        //empty check return;
        if (optionalUser.isEmpty) {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.WARN)
        }


        val postId = modifiedPostDTO2.postId
        val optionalPost = postRepository.findById(postId)
        if (optionalPost.isEmpty) {
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.WARN)
        }

        val post = optionalPost.get()

        //author id check return;
        if (optionalUser.get().id != post.author?.id) {
            throw ResultCodeException(ResultCode.ERROR_REQUESTER_NOT_POST_AUTHOR, loglevel = Level.WARN)
        }

        post.deletedAt = LocalDateTime.now()
        return try {
            postRepository.save(post)
            true
        } catch (e: Exception) {
            throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.ERROR)
        }
    }
}