package com.example.kopring_board.integrated.db.service.impl

import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.dto.common.PageDTO
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
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
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

    override fun getUsers(getUserDTO: GetUserDTO, pageDTO: PageDTO): Page<User> {
        log.debug("call getUsers : getUserDTO='$getUserDTO', pageDTO = '$pageDTO'")

        val id = getUserDTO.id
        val name = getUserDTO.name
        val pageRequest = PageRequest.of(pageDTO.page, pageDTO.size)

        try {
            return when {
                id?.isNotEmpty() == true && name?.isNotEmpty() == true -> {
                    userRepository.findByIdLikeAndNameContains(id, name, pageRequest)
                }

                id?.isNotEmpty() == true -> {
                    userRepository.findByIdContains(id, pageRequest)
                }

                name?.isNotEmpty() == true -> {
                    userRepository.findByNameContains(name, pageRequest)
                }

                else -> {
                    userRepository.findAll(pageRequest)
                }
            }
        } catch (e: Exception) {
            log.error("getUsers DB search failed. $getUserDTO", e)
            throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
        }
    }


    override fun getUser(id: String): User {
        log.debug("call getUser : id = '$id'")

        val optionalUser = try {
            userRepository.findById(id)
        } catch (e: Exception) {
            log.error("getUser DB search failed. $id", e)
            throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
        }

        when (optionalUser.isPresent) {
            true -> return optionalUser.get()
            else -> throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.INFO)
        }
    }

    override fun createUser(createUserDTO: CreateUserDTO): User {
        log.debug("call createUser : createUserDTO = '$createUserDTO'")

        if (createUserDTO.id.isNullOrEmpty()) {
            throw ResultCodeException(
                ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.INFO,
                message = "파라미터에 ID가 존재하지 않습니다."
            )
        }

        val isPresentUserById = try {
            userRepository.existsById(createUserDTO.id)
        } catch (e: Exception) {
            log.error("creatUser failed. $createUserDTO", e)
            throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
        }

        return when (isPresentUserById) {
            true -> {
                throw ResultCodeException(ResultCode.ERROR_USER_ALREADY_EXISTS, loglevel = Level.INFO)
            }
            else -> {
                try {
                    userRepository.save(createUserDTO.toEntity())
                } catch (e:Exception) {
                    log.error("createUser failed. $createUserDTO", e)
                    throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
                }
            }
        }
    }

    override fun updateUser(updateUserDTO: UpdateUserDTO): Boolean {
        log.debug("updateUser : updateUserDTO = '$updateUserDTO'")

        if (updateUserDTO.id.isNullOrEmpty()) {
            throw ResultCodeException(
                ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.INFO,
                message = "파라미터에 ID가 존재하지 않습니다."
            )
        }

        val optionalUser = try {
            userRepository.findById(updateUserDTO.id)
        } catch (e: Exception) {
            log.error("updateUser failed. $updateUserDTO", e)
            throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
        }

        if (optionalUser.isEmpty) {
            throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.INFO)
        }

        var isChange = false
        val user = optionalUser.get()

        if (updateUserDTO.name?.isNotEmpty() == true) {
            user.name = updateUserDTO.name
            isChange = true
        }

        if (updateUserDTO.email?.isNotEmpty() == true) {
            user.email = updateUserDTO.email
            isChange = true
        }

        return try {
            when (isChange) {
                true -> {
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
        log.debug("deleteUser : deleteUserDTO = '$deleteUserDTO'")

        if (deleteUserDTO.id.isNullOrEmpty()) {
            throw ResultCodeException(
                ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.INFO,
                message = "파라미터에 ID가 존재하지 않습니다."
            )
        }

        val isPresentUserById = try {
            userRepository.existsById(deleteUserDTO.id)
        } catch (e: Exception) {
            log.error("deleteUser failed. $deleteUserDTO", e)
            throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
        }

        return when (isPresentUserById) {
            true -> {
                try {
                    userRepository.deleteById(deleteUserDTO.id)
                    true
                } catch (e: Exception) {
                    throw ResultCodeException(ResultCode.ERROR_DB, loglevel = Level.ERROR)
                }
            }
            else -> {
                throw ResultCodeException(ResultCode.ERROR_USER_NOT_EXISTS, loglevel = Level.INFO)
            }
        }
    }
}