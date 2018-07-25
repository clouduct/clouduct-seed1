package org.clouduct.springboot.exception

class ClouductApplicationException(msg: String, cause: Throwable?) : RuntimeException(msg, cause) {
    lateinit var errorCode: ErrorCode

    constructor(errorCode: ErrorCode, message: String) : this(message, null) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode, message: String, cause: Throwable) : this(message, cause) {
        this.errorCode = errorCode
    }


}