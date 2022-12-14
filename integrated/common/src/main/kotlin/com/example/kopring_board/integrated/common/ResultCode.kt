package com.example.kopring_board.integrated.common

enum class ResultCode(val code: Int, val msg: String) {

    //1000 : 성공
    SUCCESS(1000, "성공"),

    //1100 : 파라미터체크 및 api 존재 여부
    ERROR_NOT_EXISTS_URI(1100, "존재하지 않는 API입니다"),
    ERROR_NOT_SUPPORTED_HTTP_METHOD(1101, "제공되지 않는 Http Method 입니다"),
    ERROR_PARAMETER_NOT_EXISTS(1102, "유효하지 않은 파라미터 입니다."),
    ERROR_PARAMETER_TYPE(1103, "유효하지 않는 파라미터 타입 입니다."),
    ERROR_PARAMETER_JSON_PARSING(1104, "요청 json 파싱 오류 입니다."),
    ERROR_HTTP_BODY(1105, "읽을 수 없는 http body 형식입니다."),
    ERROR_NOT_MEDIA_TYPE(1106, "적용 불가능한 Content-Type 입니다."),
    ERROR_ACCESS_DENIED(1107, "해당 API에 대한 권한이 없습니다."),

    //2000 : 유저관련
    ERROR_USER_NOT_EXISTS(2000, "존재하지 않는 유저입니다."),
    ERROR_USER_ALREADY_EXISTS(2001, "이미 존재하는 ID 입니다."),

    //3000 : 게시글관련
    ERROR_BOARD_NOT_EXIST(3000, "존재하지 않는 게시글입니다."),

    //9000 : 확인이 힘든 오류
    ERROR_DB(9002, "DB 변경 중 오류"),
    ERROR_CIPHER(9003, "암호화 모듈 오류"),
    ERROR_ETC(9999, "기타 에러");
}