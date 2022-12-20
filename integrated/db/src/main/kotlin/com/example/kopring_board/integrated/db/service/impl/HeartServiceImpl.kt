package com.example.kopring_board.integrated.db.service.impl

import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.dto.heart.ToggleHeartDTO
import com.example.kopring_board.integrated.db.entity.Heart
import com.example.kopring_board.integrated.db.entity.Post
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.repository.HeartRepository
import com.example.kopring_board.integrated.db.repository.PostRepository
import com.example.kopring_board.integrated.db.repository.UserRepository
import com.example.kopring_board.integrated.db.service.HeartService
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service

@Service
class HeartServiceImpl(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val heartRepository: HeartRepository
): HeartService {

    companion object {
        private val log = LogManager.getLogger()
    }

    override fun getHeartPosts(userId: String): List<Heart> {
        //userID 검증
        val optionalUser = userRepository.findById(userId)
        if(optionalUser.isEmpty) {
            throw ResultCodeException(
                ResultCode.ERROR_USER_NOT_EXISTS,
                loglevel = Level.INFO,
                message = "사용자가 존재하지 않습니다."
            )
        }

        //찾기
        return try {
            heartRepository.findByUser(optionalUser.get())
        } catch(e:Exception) {
            throw ResultCodeException(
                ResultCode.ERROR_DB,
                loglevel = Level.ERROR,
                message = "${e}"
            )
        }
    }

    override fun getHeartUsers(postId: Long): List<Heart> {
        //userID 검증
        val optionalPost = postRepository.findById(postId)
        if(optionalPost.isEmpty) {
            throw ResultCodeException(
                ResultCode.ERROR_POST_NOT_EXIST,
                loglevel = Level.INFO,
                message = "게시글이 존재하지 않습니다."
            )
        }

        //찾기
        return try {
            heartRepository.findByPost(optionalPost.get())
        } catch(e:Exception) {
            throw ResultCodeException(
                ResultCode.ERROR_DB,
                loglevel = Level.ERROR,
                message = "${e}"
            )
        }
    }

    override fun toggleHeart(postId: Long, toggleHeartDTO: ToggleHeartDTO) {

        val optionalPost = postRepository.findById(postId)
        if (optionalPost.isEmpty) {
            throw ResultCodeException(
                ResultCode.ERROR_POST_NOT_EXIST,
                loglevel = Level.ERROR
            )
        }

        val optionalUser = userRepository.findById(toggleHeartDTO.userId)
        if (optionalUser.isEmpty) {
            throw ResultCodeException(
                ResultCode.ERROR_USER_NOT_EXISTS,
                loglevel = Level.ERROR
            )
        }

        if (optionalPost.get().author?.id != optionalUser.get().id) {
            throw ResultCodeException(
                ResultCode.ERROR_REQUESTER_NOT_POST_AUTHOR,
                loglevel = Level.ERROR
            )
        }

        val optionalHeart = heartRepository.findByUserAndPost(optionalUser.get(), optionalPost.get())
        if (optionalHeart.isPresent) {
            heartRepository.deleteById(optionalHeart.get().id)
        } else {
            heartRepository.save(
                Heart(
                    user = optionalUser.get(),
                    post = optionalPost.get()
                )
            )
        }
    }
}