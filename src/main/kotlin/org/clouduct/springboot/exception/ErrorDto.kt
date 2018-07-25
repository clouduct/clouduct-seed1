package org.clouduct.springboot.exception

import java.time.Instant

data class ErrorDTO(val internalErrorCode: Int, val message: String?, val path: String) {
    val timestamp : Instant = Instant.now()
}