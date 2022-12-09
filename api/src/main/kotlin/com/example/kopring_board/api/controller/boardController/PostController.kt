package com.example.kopring_board.api.controller.boardController

import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.service.PostService
import com.example.kopring_board.integrated.webservice.api.ApiRequestMapping
import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/post/v1")
class PostController(
    private val postService: PostService
) {
    companion object {
        private val log = LogManager.getLogger()
    }

    @ApiRequestMapping("/posts", method = [RequestMethod.GET])
    fun getPosts(): Any? {
        log.debug("getPosts}")
        return postService.getPosts()
    }

    @ApiRequestMapping("/posts/{id}", method = [RequestMethod.GET])
    fun getPosts(@PathVariable id: Long): Any? {
        log.debug("getPost")
        return postService.getPost(id)
    }

    @ApiRequestMapping("/posts", method = [RequestMethod.POST])
    fun createBoard(@RequestBody createPostDTO: CreatePostDTO): Post? {
        log.debug("createPosts. $createPostDTO")

        //author id parameter check

        return postService.createPost(
            Post(
                title = createPostDTO.title,
                content = createPostDTO.content,
                author = User(
                    id = createPostDTO.authorId!!
                )
            )
        )
    }

    @ApiRequestMapping("/posts", method = [RequestMethod.PATCH, RequestMethod.PUT])
    fun updatePost(@RequestBody post: Post): Boolean? {
        log.debug("updateBoard. board : $post")
        return postService.updatePost(id, post)
    }


    @ApiRequestMapping("/posts/{id}", method = [RequestMethod.DELETE])
    fun deletePost(@PathVariable id: Long): Boolean? {
        log.debug("deleteBoard")
        return postService.deletePost(id)
    }
}