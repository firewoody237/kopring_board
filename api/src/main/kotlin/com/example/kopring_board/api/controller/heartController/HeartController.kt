package com.example.kopring_board.api.controller.heartController

import com.example.kopring_board.api.controller.userController.UserController
import com.example.kopring_board.integrated.db.entity.Heart
import com.example.kopring_board.integrated.db.service.HeartService
import com.example.kopring_board.integrated.webservice.api.ApiRequestMapping
import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/heart/v1")
class HeartController(
    val heartService: HeartService,
) {

    companion object {
        private val log = LogManager.getLogger()
    }

    @ApiRequestMapping("/users/{id}/heart", method = [RequestMethod.GET])
    fun getHeartPosts(@PathVariable id: String): List<Heart> {
        UserController.log.debug("getUserHeart, userId='$id'")
        return heartService.getHeartPosts(id)
    }
}