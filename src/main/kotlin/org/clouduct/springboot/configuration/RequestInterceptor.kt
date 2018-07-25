package org.clouduct.springboot.configuration

import org.clouduct.springboot.util.RandomString
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RequestInterceptor : HandlerInterceptorAdapter() {
    private val logger = LoggerFactory.getLogger(javaClass)


    override fun preHandle(
            request: HttpServletRequest,
            response: HttpServletResponse,
            handler: Any): Boolean {
        loggingContext(request)

        logger.info("request: ${request.method} ${request.requestURI}")

        return true
    }

    override fun afterCompletion(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, ex: Exception?) {
        if (request != null) {
            logger.info("complete: ${request.method} ${response?.status} ${request.requestURI}")
        }
        if (ex != null) {
            logger.error("complete", ex)
        }
        super.afterCompletion(request, response, handler, ex)
    }


    companion object {
        val SESSION_ID_HEADER = "cldct-Session-ID"
        val REQUEST_ID_HEADER = "cldct-Request-ID"

        fun loggingContext(request: HttpServletRequest) {
            val sessionID = request.getHeader(SESSION_ID_HEADER) ?: "?"
            val requestID = request.getHeader(REQUEST_ID_HEADER) ?: RandomString(6).nextString()

            var user = SecurityContextHolder.getContext().authentication.principal
            MDC.put("mdcIDs", " $sessionID:$requestID")
            if ( user is User ) {
                user = user.username
            }
            MDC.put("mdcUser", user.toString())
        }

        fun loggingContextUser(userName: String) {
            MDC.put("mdcUser", userName)
        }

    }
}