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
import com.example.kopring_board.integrated.post.Category
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class PostServiceImpl(
    private val postMapper: PostMapper,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
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

        //한번에 모든 Post를 조회할 일이 거의 없음
        //저자와 카테고리로 조회(delete 되지 않은 것)
        return when {
            getPostDTO.title.isNullOrEmpty() && getPostDTO.content.isNullOrEmpty() -> {
                postRepository.findByAuthorAndCategoryAndDeletedAtIsNotNull(
                    User(
                        id = getPostDTO.authorId!!
                    ),
                    Category.valueOf(getPostDTO.category),
                    pageRequest
                )
            }

            getPostDTO.authorId.isNullOrEmpty() && getPostDTO.content.isNullOrEmpty() -> {
                postRepository.findByTitleLikeAndCategoryAndDeletedAtIsNotNull(
                    getPostDTO.title!!,
                    Category.valueOf(getPostDTO.category),
                    pageRequest
                )
            }

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

//    fun getPostsByAuthorAndCategory(getPostDTO: GetPostDTO): List<Post> {
//        return getPostsByAuthorAndCategory(getPostDTO.authorId!!, getPostDTO.category!!)
//    }

//    fun getPostsByAuthorAndCategory(authorId: String, category: String): List<Post> {
//        val posts =
//            postRepository.findByAuthorAndCategoryAndDeletedAtIsNotNull(
//                User(
//                    id = authorId
//                ), Category.valueOf(category)
//            )
//        if (posts.isEmpty()) {
//            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.INFO)
//        } else {
//            return posts
//        }
//    }


//    fun getPostsByUser(user: User): List<Post> {
//        return getPostsByUser(user.id)
//    }

//    fun getPostsByUser(authorId: String): List<Post> {
//        try {
//            return postRepository.findByAuthorAndDeletedAtIsNotNull(
//                User(
//                    id = authorId
//                )
//            )
//        } catch (e: Exception) {
//            throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.WARN)
//        }
//    }


//    fun getPostsByCateogry(category: String): List<Post> {
//        try {
//            return getPostsByCateogry(Category.valueOf(category))
//        } catch (e: Exception) {
//            throw ResultCodeException(ResultCode.ERROR_ETC, loglevel = Level.INFO)
//        }
//    }

//    fun getPostsByCateogry(category: Category): List<Post> {
//        val posts =
//            postRepository.findByCategoryAndDeletedAtIsNotNull(
//                category
//            )
//        if (posts.isEmpty()) {
//            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.INFO)
//        } else {
//            return posts
//        }
//    }


    override fun getPost(id: Long): Post {
        log.debug("getPost, id = '$id'")

        val post = postRepository.findById(id)
        if (post.isPresent) {
            return post.get()
        } else {
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.INFO)
        }
    }

    override fun createPost(createPostDTO: CreatePostDTO): Post {
        log.debug("createPost, createPostDTO='$createPostDTO'")

        val optionalUser = userRepository.findById(createPostDTO.authorId)
        if (optionalUser.isEmpty) {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.WARN)
        }

        when {
            //TODO: Entity에 nullable로 안해두면 이게 필요할까?
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

    override fun updatePost(updatePostDTO: UpdatePostDTO): Boolean {
        log.debug("updatePost, updatePostDTO='$updatePostDTO'")
//        val authorID = modifiedPost.authorId
//        val postID = modifiedPost.postId

        //Post Check
        val optionalPost = postRepository.findById(updatePostDTO.id)
        if (optionalPost.isEmpty) {
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.WARN)
        }

        //User Check
        val optionalUser = userRepository.findById(updatePostDTO.authorId)
        if (optionalUser.isEmpty) {
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.WARN)
        }

        //Author Check
        if (optionalPost.get().author?.id != optionalUser.get().id) {
            throw ResultCodeException(ResultCode.ERROR_REQUESTER_NOT_POST_AUTHOR, loglevel = Level.WARN)
        }

        var isChange = false
        val post = optionalPost.get()

        if (!updatePostDTO.title.isNullOrEmpty()) {
            post.title = updatePostDTO.title!!
            isChange = true
        }

        if (!updatePostDTO.content.isNullOrEmpty()) {
            post.content = updatePostDTO.content!!
            isChange = true
        }

        if (!updatePostDTO.category.isNullOrEmpty()) {
            val category = Category.valueOf(updatePostDTO.category!!)
            post.category = category
            isChange = true
        }


        return try {
            when {
                isChange -> {
                    postRepository.save(post)
                    return true
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

        //find by id
        val optionalUser = userRepository.findById(deletePostDTO.authorId)
        //empty check return;
        if (optionalUser.isEmpty) {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.WARN)
        }

        val optionalPost = postRepository.findById(deletePostDTO.id)
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