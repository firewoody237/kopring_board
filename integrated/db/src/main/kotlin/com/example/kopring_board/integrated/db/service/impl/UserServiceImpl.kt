package com.example.kopring_board.integrated.db.service.impl

import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.dto.user.CreateUserDTO
import com.example.kopring_board.integrated.db.dto.user.DeleteUserDTO
import com.example.kopring_board.integrated.db.dto.user.GetUserDTO
import com.example.kopring_board.integrated.db.dto.user.UpdateUserDTO
import com.example.kopring_board.integrated.db.entity.User
import com.example.kopring_board.integrated.db.mapper.UserMapper
import com.example.kopring_board.integrated.db.repository.UserRepository
import com.example.kopring_board.integrated.db.service.UserService
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userMapper: UserMapper,
    private val userRepository: UserRepository
): UserService {
    //TODO: 하기 메소드들을 좀 더 코틀린스럽게 할 수 없을까
    companion object {
        private val log = LogManager.getLogger()
    }

    override fun getUsers(getUserDTO: GetUserDTO): List<User> {
        log.debug("getUsers By Parameter, getUserDTO='$getUserDTO'")

        val id = getUserDTO.id
        val name = getUserDTO.name

        try {
            return when {
                //id 검색
                //TODO: 이거 더 깔끔하게 할 방법 없나..? - 리펙토링
                !id.isNullOrEmpty() && !name.isNullOrEmpty() -> {
                    userRepository.findByIdLikeAndNameLike(id, name)
                }

                !id.isNullOrEmpty() -> {
                    userRepository.findByIdLike(id)
                }

                !name.isNullOrEmpty() -> {
                    userRepository.findByNameLike(name)
                }

                else -> {
                    userRepository.findAll()
                }
            }
        } catch (e: Exception) {
            log.error("get user failed. ${getUserDTO.id}", e)
            throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
        }
    }


    override fun getUser(id: String): User {
        log.debug("getUser, id='$id'")

        val optionalUser = try {
            userRepository.findById(id)
        } catch (e: Exception) {
            log.error("get user failed. $id", e)
            throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
        }

        if (optionalUser.isPresent) {
            return optionalUser.get()
        } else {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.INFO)
        }
    }

    override fun createUser(createUserDTO: CreateUserDTO): User {
        log.debug("createUser. createUserDTO=$createUserDTO")

        //TODO : try-catch의 남발..
        val isPresentById = try {
            userRepository.existsById(createUserDTO.id)
        } catch (e: Exception) {
            log.error("get user failed. ${createUserDTO.id}", e)
            throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
        }

        if (isPresentById) {
            throw ResultCodeException(ResultCode.ERROR_USER_ALREADY_EXISTS, loglevel = Level.INFO)
        } else {
            try {
                log.debug("createUserDTO to User : ${createUserDTO.toEntity()}")
                return userRepository.save(createUserDTO.toEntity())
            } catch (e:Exception) {
                log.error("create user failed. ${createUserDTO.id}", e)
                throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
            }
        }
    }

    override fun updateUser(updateUserDTO: UpdateUserDTO): Boolean {
        log.debug("updateUser, updateUserDTO='$updateUserDTO'")
        //TODO : try-catch의 남발..
        val optionalUser = try {
            userRepository.findById(updateUserDTO.id)
        } catch (e: Exception) {
            log.error("get user failed. ${updateUserDTO.id}", e)
            throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
        }

        if (optionalUser.isEmpty) {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.INFO)
        }

        var isChange = false
        val user = optionalUser.get()

        if (!updateUserDTO.name.isNullOrEmpty()) {
            user.name = updateUserDTO.name
            isChange = true
        }

        if (!updateUserDTO.email.isNullOrEmpty()) {
            user.email = updateUserDTO.email
            isChange = true
        }

        return try {
            when {
                isChange -> {
                    userRepository.save(user)
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

    override fun deleteUser(deleteUserDTO: DeleteUserDTO): Boolean {
        log.debug("deleteUser, deleteUserDTO='$deleteUserDTO'")

        //TODO : try-catch의 남발..
        val optionalUser = try {
            userRepository.findById(deleteUserDTO.id)
        } catch (e: Exception) {
            log.error("get user failed. ${deleteUserDTO.id}", e)
            throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
        }

        if (optionalUser.isEmpty) {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.INFO)
        }

        try {
            userRepository.deleteById(optionalUser.get().id)
            return true
        } catch (e: Exception) {
            throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.ERROR)
        }
    }
}