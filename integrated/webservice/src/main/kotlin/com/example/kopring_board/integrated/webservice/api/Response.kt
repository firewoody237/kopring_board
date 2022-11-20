package com.example.kopring_board.integrated.webservice.api

import com.fasterxml.jackson.annotation.JsonIgnore
import com.naver.billing.polaris.common.ResultCode
import org.apache.commons.lang.StringUtils
import org.apache.logging.log4j.ThreadContext

@Suppress("MemberVisibilityCanBePrivate", "ConvertSecondaryConstructorToPrimary")
class Response {
    @JsonIgnore
    val resultCode: ResultCode

    @JsonIgnore
    val msg: String?

    val requestUuid: String
    val rtncd: Int
    val rtnmsg: String
    val response: Any?

    constructor(resultCode: ResultCode, msg: Any? = null, response: Any? = null) {
        this.resultCode = resultCode
        this.msg = msg?.toString()

        this.requestUuid = ThreadContext.get("requestUuid")
        this.response = response
        this.rtncd = resultCode.code
        this.rtnmsg = arrayOf(resultCode.msg, msg?.toString()).filter(StringUtils::isNotBlank).joinToString(" - ")
    }

}