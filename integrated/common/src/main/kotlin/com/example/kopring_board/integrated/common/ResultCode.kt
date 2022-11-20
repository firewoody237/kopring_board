package com.example.kopring_board.integrated.common

enum class ResultCode(val code: Int, val msg: String) {

    //1000 : 성공
    SUCCESS(1000, "성공"),




    //9000 : 확인이 힘든 오류
    ERROR_DB(9002, "DB 변경 중 오류"),
    ERROR_CIPHER(9003, "암호화 모듈 오류"),
    ERROR_ETC(9999, "기타 에러");
}