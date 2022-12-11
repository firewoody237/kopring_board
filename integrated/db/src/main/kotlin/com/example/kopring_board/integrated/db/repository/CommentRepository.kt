package com.example.kopring_board.integrated.db.repository

import com.example.kopring_board.integrated.db.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long?> {

}