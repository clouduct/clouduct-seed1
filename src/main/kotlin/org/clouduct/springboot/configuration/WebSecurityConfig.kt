package org.clouduct.springboot.configuration

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class WebSecurityConfig() : WebSecurityConfigurerAdapter() {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Value("\${app.swagger:false}")
    private val swagger: Boolean = false

//    @Bean(name = arrayOf(AUTHENTICATION_MANAGER))
//    @Throws(Exception::class)
//    override fun authenticationManagerBean(): AuthenticationManager {
//        return super.authenticationManagerBean()
//    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .cors() //

                .and()

                .csrf().disable()
                // .exceptionHandling().authenticationEntryPoint(JwtAuthenticationEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(*swaggerPaths()).permitAll()
                .antMatchers("/api/hello").permitAll()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/actuator/info").permitAll()
                .anyRequest().authenticated()

                .and()

//                .rememberMe().key(applicationSecret)
//
//        http
//          .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter::class.java)

        // we do not need the usual redirect to a login page, a simple UNAUTHORIZED is sufficient
        http.exceptionHandling().authenticationEntryPoint(AuthDeniedHandler())

    }

    internal fun swaggerPaths(): Array<String> {
        if (swagger) {
            return swaggerPaths
        } else {
            return noSwaggerPaths
        }

    }

    class AuthDeniedHandler : AuthenticationEntryPoint {
        private val logger = LoggerFactory.getLogger(javaClass)

        override fun commence(request: HttpServletRequest, response: HttpServletResponse, p2: AuthenticationException?) {
            logger.info("Unauthorized ${request.servletPath}")
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.print("Unauthorizated....")
        }

    }


    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        // auth.userDetailsService<AccountService>(accountService).passwordEncoder(BCryptPasswordEncoder())
    }

    companion object {

        val swaggerPaths = arrayOf("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**")
        val noSwaggerPaths = arrayOf("DOESNOTEXIST")
    }

}

