package com.example.kopring_board.api.controller.postController

import com.example.kopring_board.integrated.db.dto.post.CreatePostDTO
import com.example.kopring_board.integrated.db.dto.post.ModifiedPostDTO
import com.example.kopring_board.integrated.db.dto.post.GetPostDTO
import com.example.kopring_board.integrated.db.dto.post.ModifiedPostDTO2
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.service.PostService
import com.example.kopring_board.integrated.webservice.api.ApiRequestMapping
import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/post/v1")
class PostController(
    private val postService: PostService
) {
    companion object {
        private val log = LogManager.getLogger()
    }

    @ApiRequestMapping("/posts", method = [RequestMethod.GET])
    fun getPosts(@RequestBody getPostDTO: GetPostDTO): Any? {
        log.debug("getPosts, getPostDTO = '$getPostDTO'")
        return postService.getPosts(getPostDTO)
    }

    @ApiRequestMapping("/posts/{id}", method = [RequestMethod.GET])
    fun getPosts(@PathVariable id: Long): Any? {
        log.debug("getPost, id = '$id'")
        return postService.getPost(id)
    }

    @ApiRequestMapping("/posts", method = [RequestMethod.POST])
    fun createPost(@RequestBody createPostDTO: CreatePostDTO): Post? {
        log.debug("createPost. createPostDTO = '$createPostDTO'")
        return postService.createPost(createPostDTO)
    }

    @ApiRequestMapping("/posts", method = [RequestMethod.PATCH, RequestMethod.PUT])
    fun updatePost(@RequestBody modifiedPostDTO2: ModifiedPostDTO2): Post? {
        log.debug("updatePost. modifiedPostDTO2 : $modifiedPostDTO2")
        return postService.updatePost(modifiedPostDTO2)
    }


    @ApiRequestMapping("/posts", method = [RequestMethod.DELETE])
    fun deletePost(@RequestBody modifiedPostDTO2: ModifiedPostDTO2): Boolean? {
        log.debug("deletePost = '$modifiedPostDTO2'")
        return postService.deletePost(modifiedPostDTO2)
    }
}