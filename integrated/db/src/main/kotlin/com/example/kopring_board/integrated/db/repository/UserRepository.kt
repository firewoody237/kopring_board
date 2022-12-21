package com.example.kopring_board.integrated.db.repository

import com.example.kopring_board.integrated.db.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository : JpaRepository<User, String> {

    fun findByIdLikeAndNameContains(id: String, name: String, pageable: Pageable): Page<User>
    fun findByIdContains(id: String, pageable: Pageable): Page<User>
    fun findByNameContains(name: String, pageable: Pageable): Page<User>
}