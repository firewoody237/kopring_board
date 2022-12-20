package com.example.kopring_board.api.controller.postController

import com.example.kopring_board.integrated.db.dto.comment.CreateCommentDTO
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
import org.springframework.web.bind.annotation.*

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
    fun getPosts(@RequestBody getPostDTO: GetPostDTO): List<Post> {
        log.debug("getPosts, getPostDTO = '$getPostDTO'")
        return postService.getPosts(getPostDTO)
    }

    @ApiRequestMapping("/posts/{id}", method = [RequestMethod.GET])
    fun getPosts(@PathVariable id: Long): Post {
        log.debug("getPost, id = '$id'")
        return postService.getPost(id)
    }

    @ApiRequestMapping("/posts", method = [RequestMethod.POST])
    fun createPost(@RequestBody createPostDTO: CreatePostDTO): Post {
        log.debug("createPost. createPostDTO = '$createPostDTO'")
        return postService.createPost(createPostDTO)
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
    fun getHeartUsers(@PathVariable id: Long): List<Heart> {
        log.debug("getHeartUsers, id='$id'")
        return heartService.getHeartUsers(id)
    }

    @ApiRequestMapping("/posts/{id}/hearts", method = [RequestMethod.PUT])
    fun toggleHeart(@PathVariable id: Long, @RequestBody toggleHeartDTO: ToggleHeartDTO) {
        log.debug("getHeartUsers, id='$id'")
        return heartService.toggleHeart(id, toggleHeartDTO)
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
    fun toggleComment(@PathVariable id: Long, @RequestBody updateCommentDTO: UpdateCommentDTO): Boolean {
        log.debug("toggleComment, id='${id}', updateCommentDTO='${updateCommentDTO}'")
        return commentService.toggleComment(id, updateCommentDTO)
    }


    //like

    //like rollback

    //paging pageable


}