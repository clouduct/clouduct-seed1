package org.clouduct.springboot.configuration

import org.clouduct.springboot.exception.ErrorDTO
import org.clouduct.springboot.exception.ErrorCode
import org.clouduct.springboot.exception.ClouductApplicationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.persistence.EntityExistsException
import javax.servlet.http.HttpServletRequest


/**
 * Generic REST controller advice that collects bean validation errors and converts them to a Json object.
 */
@RestControllerAdvice
class ControllerValidationAdvice : ResponseEntityExceptionHandler() {

    /**
     * This will be invoked on Spring Bean Validation errors.
     */
    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException?, headers: HttpHeaders?, status: HttpStatus?, request: WebRequest?): ResponseEntity<Any> {

        for (fieldError in ex!!.bindingResult.fieldErrors) {
            fieldError.field
        }

        // validation errors should have been caught in the frontend - this is for debugging
        val sb = StringBuilder()

        if (ex.bindingResult.fieldErrors.isNotEmpty()) {
            sb.append("field errors: ")
            sb.append(ex.bindingResult.fieldErrors.map { it.field }.joinToString(separator = ", "))
        }

        if (ex.bindingResult.globalErrors.isNotEmpty()) {
            sb.append("global errors: ")
            sb.append(ex.bindingResult.globalErrors.map { it.objectName }.joinToString(separator = ", "))
        }

        val error = ErrorDTO(status!!.value(), sb.toString(), request?.contextPath
                ?: "--")

        logger.warn(sb.toString(), ex)


        return ResponseEntity(error, headers, status)
    }

    @ExceptionHandler(EntityExistsException::class)
    fun handleEntityExistsException(ex: EntityExistsException, request: HttpServletRequest): ResponseEntity<ErrorDTO> {
        logger.warn("${request.method} ${request.requestURI}", ex)
        return ResponseEntity(ErrorDTO(HttpStatus.CONFLICT.value(), "entity already exists", request.requestURI), HttpHeaders(), HttpStatus.CONFLICT)
    }

    @ExceptionHandler(ClouductApplicationException::class)
    fun handleMibelleException(ex: ClouductApplicationException, request: HttpServletRequest): ResponseEntity<ErrorDTO> {

        logger.warn("${request.method} ${request.requestURI}", ex)
        return ResponseEntity(ErrorDTO(ex.errorCode.code,
                ex.message,
                request.requestURI), HttpHeaders(),
                ex.errorCode.httpStatus)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(ex: BadCredentialsException, request: HttpServletRequest): ResponseEntity<ErrorDTO> {

        logger.warn("${request.method} ${request.requestURI}", ex)
        return ResponseEntity(ErrorDTO(ErrorCode.UNAUTHORIZED.code,
                ex.message,
                request.requestURI), HttpHeaders(),
                ErrorCode.UNAUTHORIZED.httpStatus)
    }

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException,
                                               headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        logger.warn("bad request: " + ex.message)
        val uri = if (request is HttpServletRequest) request.requestURI else "--"
        return ResponseEntity(ErrorDTO(status.value(), ex.message
                ?: "bad request", uri), HttpHeaders(), status)
    }
    override fun handleMissingServletRequestPart(ex: MissingServletRequestPartException,
                                                 headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        logger.warn("bad request: " + ex.message)
        val uri = if (request is HttpServletRequest) request.requestURI else "--"
        return ResponseEntity(ErrorDTO(status.value(), ex.message
                ?: "bad request", uri), HttpHeaders(), status)
    }

    override fun handleExceptionInternal(ex: Exception, body: Any,
                                                   headers: HttpHeaders, status: HttpStatus,
                                                   request: WebRequest): ResponseEntity<Any> {
        logger.error("error handling request", ex)
        val uri = if (request is HttpServletRequest) request.requestURI else "--"
        return ResponseEntity(ErrorDTO(status.value(), ex.message
                ?: "unknown error", uri), HttpHeaders(), status)
    }

}
