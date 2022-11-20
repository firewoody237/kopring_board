package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.db.entity.Board
import com.example.kopring_board.integrated.db.repository.BoardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val boardRepository: BoardRepository
) {

    fun getBoards(): List<Board> {
        return boardRepository.findAll()
    }
}