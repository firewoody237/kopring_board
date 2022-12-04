package com.example.kopring_board.integrated.db.service

import com.example.kopring_board.integrated.common.ResultCode
import com.example.kopring_board.integrated.common.ResultCodeException
import com.example.kopring_board.integrated.db.entity.Board
import com.example.kopring_board.integrated.db.mapper.BoardMapper
import com.example.kopring_board.integrated.db.repository.BoardRepository
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Service


@Service
class BoardService(
    private val boardMapper: BoardMapper,
    private val boardRepository: BoardRepository
) {

    companion object {
        private val log = LogManager.getLogger()
    }

    fun getBoards(): List<Board> {
        return boardRepository.findAll()
    }

    fun getBoard(id: Long): Board? {
        log.debug("getBoard")

        var optionalBoard = boardRepository.findById(id)
        if (optionalBoard.isPresent) {
            return optionalBoard.get()
        } else {
            throw ResultCodeException(ResultCode.ERROR_BOARD_NOT_EXIST, loglevel = Level.INFO)
        }
    }

    fun createBoard(board: Board): Board?{
        log.debug("createBoard")
        return boardRepository.save(board)
    }

    fun updateBoard(id: Long, board: Board): Boolean? {
        log.debug("updateBoard")

        if (boardRepository.existsById(id)) {
            boardRepository.save(board)
            return true
        } else {
            throw ResultCodeException(ResultCode.ERROR_BOARD_NOT_EXIST, loglevel = Level.INFO)
        }
    }

    fun deleteBoard(id: Long): Boolean? {
        log.debug("deleteBoard")

        if (boardRepository.existsById(id)) {
            boardRepository.deleteById(id)
            return true
        } else {
            throw ResultCodeException(ResultCode.ERROR_BOARD_NOT_EXIST, loglevel = Level.INFO)
        }
    }
}