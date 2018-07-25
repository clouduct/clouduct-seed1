package org.clouduct.springboot.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val code: Int, val httpStatus: HttpStatus) {
    ENTITY_NOT_FOUND(1001, HttpStatus.NOT_FOUND),

    RESPONSES_ILLEGAL_STATE(1201, HttpStatus.CONFLICT),
    ANALYSIS_ILLEGAL_STATE(1202, HttpStatus.CONFLICT),
    ORDER_ILLEGAL_STATE(1203, HttpStatus.CONFLICT),

    UNAUTHORIZED(1401, HttpStatus.UNAUTHORIZED),
    NOT_FOUND(1404, HttpStatus.NOT_FOUND),

    TOKEN_ALREADY_USED(1410, HttpStatus.CONFLICT),
    TOKEN_EXPIRED(1411, HttpStatus.GONE),

    INTERNAL_ERROR(1501, HttpStatus.INTERNAL_SERVER_ERROR),

    SMS_ERROR(1502, HttpStatus.BAD_GATEWAY)

}