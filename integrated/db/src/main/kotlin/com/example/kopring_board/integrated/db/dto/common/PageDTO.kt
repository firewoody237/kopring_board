package com.example.kopring_board.integrated.db.dto.common

import com.example.kopring_board.integrated.common.PAGE
import com.example.kopring_board.integrated.common.SIZE

data class PageDTO(
    val size: Int = SIZE,
    val page: Int = PAGE,
)
