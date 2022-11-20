package com.example.kopring_board.integrated.db.repository

import com.example.kopring_board.integrated.db.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository : JpaRepository<User, String?> {


}