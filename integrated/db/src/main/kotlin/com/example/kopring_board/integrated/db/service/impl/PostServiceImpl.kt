package com.example.kopring_board.integrated.db.service.impl

import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.dto.post.*
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.mapper.PostMapper
import com.example.kopring_board.integrated.db.repository.PostRepository
import com.example.kopring_board.integrated.db.repository.UserRepository
import com.example.kopring_board.integrated.db.service.PostService
import com.example.kopring_board.integrated.db.service.UserService
import com.example.kopring_board.integrated.post.Category
import org.apache.commons.lang.StringUtils
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class PostServiceImpl(
    private val postMapper: PostMapper,
    private val postRepository: PostRepository,
    private val userService: UserService
): PostService {

    //TODO: try catch 씌우기

    companion object {
        private val log = LogManager.getLogger()
    }

    /**
     * Category + AuthorId/Title/Content
     * 2가지 조합으로 사용
     */
    override fun getPosts(getPostDTO: GetPostDTO, pageRequest: PageRequest): List<Post> {
        log.debug("getPosts, getPostDTO = '$getPostDTO'")

        if (getPostDTO.authorId.isNullOrEmpty() && getPostDTO.title.isNullOrEmpty() && getPostDTO.content.isNullOrEmpty()) {
            return postRepository.findByCategoryAndDeletedAtIsNotNull(Category.valueOf(getPostDTO.category), pageRequest)
        }

        return when {
            //저자(+카테고리)만 존재
            getPostDTO.title.isNullOrEmpty() && getPostDTO.content.isNullOrEmpty() -> {
                postRepository.findByAuthorAndCategoryAndDeletedAtIsNotNull(
                    User(
                        id = getPostDTO.authorId!!
                    ),
                    Category.valueOf(getPostDTO.category),
                    pageRequest
                )
            }

            //제목(+카테고리)만 존재
            getPostDTO.authorId.isNullOrEmpty() && getPostDTO.content.isNullOrEmpty() -> {
                postRepository.findByTitleLikeAndCategoryAndDeletedAtIsNotNull(
                    getPostDTO.title!!,
                    Category.valueOf(getPostDTO.category),
                    pageRequest
                )
            }

            //내용(+카테고리)만 존재
            getPostDTO.authorId.isNullOrEmpty() && getPostDTO.title.isNullOrEmpty() -> {
                postRepository.findByContentLikeAndCategoryAndDeletedAtIsNotNull(
                    getPostDTO.content!!,
                    Category.valueOf(getPostDTO.category),
                    pageRequest
                )
            }

            else -> {
                throw ResultCodeException(
                    ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                    loglevel = Level.INFO
                )
            }
        }
    }

    override fun getPost(id: Long): Post {
        log.debug("getPost, id = '$id'")

        val post = postRepository.findById(id) //TODO: IsNotNull 추가
        if (post.isPresent) {
            return post.get()
        } else {
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.INFO)
        }
    }

    override fun createPost(createPostDTO: CreatePostDTO): Post {
        log.debug("createPost, createPostDTO='$createPostDTO'")

        val author = userService.getUser(createPostDTO.authorId)

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

        val post = Post(
            title = createPostDTO.title!!,
            content = createPostDTO.content!!,
            category = Category.valueOf(createPostDTO.category),
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

    override fun updatePost(updatePostDTO: UpdatePostDTO): Boolean {
        log.debug("updatePost, updatePostDTO='$updatePostDTO'")

        //Post Check
        val foundPost = getPost(updatePostDTO.id)

        //User Check
        val foundUser = userService.getUser(updatePostDTO.authorId)

        //Author Check
        if (foundPost.author != foundUser) {
            throw ResultCodeException(ResultCode.ERROR_REQUESTER_NOT_POST_AUTHOR, loglevel = Level.WARN)
        }

        var isChange = false

        if (updatePostDTO.title?.isNotEmpty() == true) {
            foundPost.title = updatePostDTO.title!!
            isChange = true
        }

        if (updatePostDTO.content?.isNotEmpty() == true) {
            foundPost.content = updatePostDTO.content!!
            isChange = true
        }

        if (updatePostDTO.category?.isNotEmpty() == true) {
            val category = Category.valueOf(updatePostDTO.category!!)
            foundPost.category = category
            isChange = true
        }


        return try {
            when(isChange) {
                true -> {
                    postRepository.save(foundPost)
                    true
                }

                else -> {
                    false
                }
            }
        } catch (e: Exception) {
            throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.ERROR)
        }
    }

    override fun deletePost(deletePostDTO: DeletePostDTO): Boolean {
        log.debug("deletePost deletePostDTO='$deletePostDTO'")

        if (deletePostDTO.id == null || deletePostDTO.authorId == null) {
            throw ResultCodeException(ResultCode.ERROR_PARAMETER_NOT_EXISTS, loglevel = Level.WARN,
            "게시글ID나 사용자ID를 모두 입력 해 주세요")
        }

        val foundUser = userService.getUser(deletePostDTO.authorId)
        val foundPost = getPost(deletePostDTO.id)

        if (foundPost.author != foundUser) {
            throw ResultCodeException(ResultCode.ERROR_REQUESTER_NOT_POST_AUTHOR, loglevel = Level.WARN)
        }

        foundPost.deletedAt = LocalDateTime.now()
        return try {
            postRepository.save(foundPost)
            true
        } catch (e: Exception) {
            throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.ERROR)
        }
    }
}