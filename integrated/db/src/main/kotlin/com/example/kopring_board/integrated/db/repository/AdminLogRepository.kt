package com.example.kopring_board.integrated.db.repository

import com.example.kopring_board.integrated.db.entity.AdminLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminLogRepository: JpaRepository<AdminLog, Long?> {
}