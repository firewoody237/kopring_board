package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.mapper.PostMapper
import com.example.kopring_board.integrated.db.repository.PostRepository
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service


@Service
class PostService(
    private val postMapper: PostMapper,
    private val postRepository: PostRepository
) {

    companion object {
        private val log = LogManager.getLogger()
    }

    fun getPosts(): List<Post> {
        return postRepository.findAll()
    }

    fun getPost(id: Long): Post? {
        log.debug("getPost")

        var optionalPost = postRepository.findById(id)
        if (optionalPost.isPresent) {
            return optionalPost.get()
        } else {
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.INFO)
        }
    }

    fun createPost(post: Post): Post?{
        log.debug("createPost")
        return postRepository.save(post)
    }

    fun updatePost(id: Long, post: Post): Boolean? {
        log.debug("updateBoard")

        if (postRepository.existsById(id)) {
            postRepository.save(post)
            return true
        } else {
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.INFO)
        }
    }

    fun deletePost(id: Long): Boolean? {
        log.debug("deletePost")



        //find by id

        //empty check return;

        //author id check return;

        //user check

        //save error check

        val optionalPost  = postRepository.findById(id)
        if(optionalPost.isEmpty){
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.INFO)
        }

        val post = optionalPost.get()

        if(post.author.id == ){

        }


        if (postRepository.existsById(id)) {



            postRepository.deleteById(id)
            return true
        } else {
            throw ResultCodeException(ResultCode.ERROR_POST_NOT_EXIST, loglevel = Level.INFO)
        }
    }
}