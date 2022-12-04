package com.example.kopring_board.api.controller.boardController

import com.example.kopring_board.integrated.db.entity.Board
import com.example.kopring_board.integrated.db.service.BoardService
import com.example.kopring_board.integrated.webservice.api.ApiRequestMapping
import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/board/v1")
class BoardController(
    private val boardService: BoardService
) {
    companion object {
        private val log = LogManager.getLogger()
    }

    @ApiRequestMapping("/boards", method = [RequestMethod.GET])
    fun getBoards(): Any? {
        log.debug("getBoards")
        return boardService.getBoards()
    }

    @ApiRequestMapping("/boards/{id}", method = [RequestMethod.GET])
    fun getBoard(@PathVariable id: Long): Any? {
        log.debug("getBoard")
        return boardService.getBoard(id)
    }

    @ApiRequestMapping("/boards", method = [RequestMethod.POST])
    fun createBoard(@RequestBody board: Board): Board? {
        log.debug("createBoard")
        return boardService.createBoard(board)
    }

    @ApiRequestMapping("/boards/{id}", method = [RequestMethod.POST])
    fun updateBoard(@PathVariable id: Long, @RequestBody board: Board): Boolean? {
        log.debug("updateBoard")
        return boardService.updateBoard(id, board)
    }

    @ApiRequestMapping("/boards/{id}", method = [RequestMethod.DELETE])
    fun deleteBoard(@PathVariable id: Long): Boolean? {
        log.debug("deleteBoard")
        return boardService.deleteBoard(id)
    }
}