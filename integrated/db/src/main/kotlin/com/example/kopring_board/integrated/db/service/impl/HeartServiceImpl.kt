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
import com.example.kopring_board.integrated.db.service.PostService
import com.example.kopring_board.integrated.db.service.UserService
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service

@Service
class HeartServiceImpl(
    private val heartRepository: HeartRepository,
    private val postService: PostService,
    private val userService: UserService
): HeartService {

    companion object {
        private val log = LogManager.getLogger()
    }

    //사용자가 본인이 하트 누른 글을 찾아올 때
    override fun getHeartPosts(userId: String): List<Heart> {
        //userID 검증
        val foundUser = userService.getUser(userId)

        //찾기
        return try {
            heartRepository.findByUser(foundUser)
        } catch(e:Exception) {
            throw ResultCodeException(
                ResultCode.ERROR_DB,
                loglevel = Level.ERROR,
                message = "${e}"
            )
        }
    }

    //해당 글을 하트 누른 유저를 창아올 때
    override fun getHeartUsers(postId: Long): List<Heart> {

        val foundPost = postService.getPost(postId)

        //찾기
        return try {
            heartRepository.findByPost(foundPost)
        } catch(e:Exception) {
            throw ResultCodeException(
                ResultCode.ERROR_DB,
                loglevel = Level.ERROR,
                message = "${e}"
            )
        }
    }

    override fun heart(postId: Long, toggleHeartDTO: ToggleHeartDTO): Boolean {

        val foundPost = postService.getPost(postId)
        val foundUser = userService.getUser(toggleHeartDTO.userId)

        //하트했는지 확인하고, 없으면 heart
        val optionalHeart = heartRepository.findByUserAndPost(foundUser, foundPost)
        //TODO : 이거 좀 스마트하게 쓸 수 있는 방법 없을까?
        if (optionalHeart.isEmpty) {
            try {
                heartRepository.save(
                    Heart(
                        user = foundUser,
                        post = foundPost
                    )
                )
                return true
            } catch(e: Exception) {
                throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.ERROR)
            }
        } else {
            throw ResultCodeException(ResultCode.ERROR_HEART_ALREADY_EXIST, loglevel = Level.WARN)
        }
    }

    override fun unheart(postId: Long, toggleHeartDTO: ToggleHeartDTO): Boolean {

        val foundPost = postService.getPost(postId)
        val foundUser = userService.getUser(toggleHeartDTO.userId)

        //하트했는지 확인하고, 없으면 heart
        //TODO : 이거 좀 스마트하게 쓸 수 있는 방법 없을까?
        val optionalHeart = heartRepository.findByUserAndPost(foundUser, foundPost)
        if (optionalHeart.isPresent) {
            try {
                heartRepository.deleteById(optionalHeart.get().id)
                return true
            } catch (e: Exception) {
                throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.ERROR)
            }
        } else {
            throw ResultCodeException(ResultCode.ERROR_HEART_NOT_EXIST, loglevel = Level.WARN)
        }
    }
}