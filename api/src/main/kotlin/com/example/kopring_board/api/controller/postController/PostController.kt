package com.example.kopring_board.api.controller.postController

import com.example.kopring_board.integrated.common.PAGE
import com.example.kopring_board.integrated.common.SIZE
import com.example.kopring_board.integrated.db.dto.comment.CreateCommentDTO
import com.example.kopring_board.integrated.db.dto.comment.DeleteCommentDTO
import com.example.kopring_board.integrated.db.dto.comment.GetCommentDTO
import com.example.kopring_board.integrated.db.dto.comment.UpdateCommentDTO
import com.example.kopring_board.integrated.db.dto.heart.ToggleHeartDTO
import com.example.kopring_board.integrated.db.dto.post.*
import com.example.kopring_board.integrated.db.entity.Comment
import com.example.kopring_board.integrated.db.entity.Heart
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.service.CommentService
import com.example.kopring_board.integrated.db.service.HeartService
import com.example.kopring_board.integrated.db.service.PostService
import com.example.kopring_board.integrated.webservice.api.ApiRequestMapping
import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import kotlin.contracts.contract

@RestController
@RequestMapping("/post/v1")
class PostController(
    private val postService: PostService,
    private val heartService: HeartService,
    private val commentService: CommentService
) {
    companion object {
        private val log = LogManager.getLogger()
    }

    @ApiRequestMapping("/posts", method = [RequestMethod.GET])
    fun getPosts(
        @RequestBody getPostDTO: GetPostDTO,
        @RequestParam page: Int = PAGE,
        @RequestParam size: Int = SIZE
    ): List<PostVO> {
        log.debug("getPosts, getPostDTO = '$getPostDTO'")
        val pageRequest = PageRequest.of(page, size)
        return postService.getPosts(getPostDTO, pageRequest).map {
            post -> PostVO(
                id = post.id,
                authorId = post.author!!.id,
                title = post.title,
                content =  post.content,
                comment = post.commentList.map {
                    mapOf(
                        "content" to it.content
                    )
                }.toList(),
                commentCount = post.commentList.size.toLong()
            )
        }
    }

    @ApiRequestMapping("/posts/{id}", method = [RequestMethod.GET])
    fun getPosts(@PathVariable id: Long): Any? {
        log.debug("getPost, id = '$id'")

        val post = postService.getPost(id)
        return PostVO(
            id = post.id,
            authorId = post.author!!.id,
            title = post.title,
            content =  post.content,
            comment = post.commentList.map {
                mapOf(
                    "content" to it.content
                )
            }.toList(),
            commentCount = post.commentList.size.toLong()
        )
    }

    @ApiRequestMapping("/posts", method = [RequestMethod.POST])
    fun createPost(@RequestBody createPostDTO: CreatePostDTO): Any? {
        log.debug("createPost. createPostDTO = '$createPostDTO'")
        val post = postService.createPost(createPostDTO)
        return PostVO(
            id = post.id,
            authorId = post.author!!.id,
            title = post.title,
            content = post.content,
            comment = post.commentList.map {
                mapOf(
                    "content" to it.content
                )
            }.toList(),
            commentCount = post.commentList.size.toLong()
        )
    }

    @ApiRequestMapping("/posts", method = [RequestMethod.PATCH, RequestMethod.PUT])
    fun updatePost(@RequestBody updatePostDTO: UpdatePostDTO): Boolean {
        log.debug("updatePost. updatePostDTO : $updatePostDTO")
        return postService.updatePost(updatePostDTO)
    }


    @ApiRequestMapping("/posts", method = [RequestMethod.DELETE])
    fun deletePost(@RequestBody deletePostDTO: DeletePostDTO): Boolean? {
        log.debug("deletePostDTO. deletePost = '$deletePostDTO'")
        return postService.deletePost(deletePostDTO)
    }

    //TODO: 이 설계가 맞을까..?
    @ApiRequestMapping("/posts/{id}/hearts", method = [RequestMethod.GET])
    fun getHeartUsers(@PathVariable id: Long): MutableList<Heart>? {
        log.debug("getHeartUsers, id='$id'")
        return heartService.getHeartUsers(id)
    }

    @ApiRequestMapping("/posts/{id}/hearts", method = [RequestMethod.POST])
    fun heart(@PathVariable id: Long, @RequestBody toggleHeartDTO: ToggleHeartDTO): Boolean {
        log.debug("heart, id='$id'")
        return heartService.heart(id, toggleHeartDTO)
    }

    @ApiRequestMapping("/posts/{id}/heart", method = [RequestMethod.DELETE])
    fun unheart(@PathVariable id: Long, @RequestBody toggleHeartDTO: ToggleHeartDTO): Boolean {
        log.debug("unheart, id='$id'")
        return heartService.unheart(id, toggleHeartDTO)
    }

    @ApiRequestMapping("/posts/{id}/comments", method = [RequestMethod.GET])
    fun getPostComments(@PathVariable id: Long): List<Comment> {
        log.debug("getComment, id='${id}'")
        return commentService.getPostComments(id)
    }

    @ApiRequestMapping("/posts/{id}/comments", method = [RequestMethod.POST])
    fun createComment(@PathVariable id: Long, @RequestBody createCommentDTO: CreateCommentDTO): Comment {
        log.debug("getComment, id='${id}', createCommentDTO='${createCommentDTO}'")
        return commentService.createComment(id, createCommentDTO)
    }

    @ApiRequestMapping("/posts/{id}/comments", method = [RequestMethod.PUT])
    fun updateComment(@PathVariable id: Long, @RequestBody updateCommentDTO: UpdateCommentDTO): Boolean {
        log.debug("toggleComment, id='${id}', updateCommentDTO='${updateCommentDTO}'")
        return commentService.updateComment(id, updateCommentDTO)
    }

    @ApiRequestMapping("/posts/{id}/comments", method = [RequestMethod.DELETE])
    fun deleteComment(@PathVariable id: Long, @RequestBody deleteCommentDTO: DeleteCommentDTO): Boolean {
        log.debug("toggleComment, id='${id}', deleteCommentDTO='${deleteCommentDTO}'")
        return commentService.deleteComment(id, deleteCommentDTO)
    }


    //like

    //like rollback

    //paging pageable


}