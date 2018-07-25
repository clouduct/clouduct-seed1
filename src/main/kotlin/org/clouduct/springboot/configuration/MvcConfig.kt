package org.clouduct.springboot.configuration

import com.fasterxml.jackson.databind.SerializationFeature
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.*


@Configuration
@EnableWebMvc
class MvcConfig : WebMvcConfigurer {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Value("\${app.cors.origin:#{null}}")
    private val corsOrigin: String? = null

    @Autowired
    private var requestInterceptor: RequestInterceptor? = null

    override fun addInterceptors(registry: InterceptorRegistry) {
        if (requestInterceptor == null) {
            logger.warn("RequestInterceptor not set (okay in test mode)")
        } else {
            registry.addInterceptor(requestInterceptor)
        }
        super.addInterceptors(registry)
    }

    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer?) {
        super.configureContentNegotiation(configurer)
        configurer!!.favorPathExtension(false) // needed to supprt ".com" mail addresses in urls
    }

    /**
     * Add swagger-ui, automatic configuration doesn't seem to work.
     */
    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
        registry!!.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>?) {
        // patch the existing MappingJackson2HttpMessageConverter with our custom ObjectMapper
        // I didn't find any other mechanism that worked (I tried)
        val jsonConverter = converters!!.filter { c -> c is MappingJackson2HttpMessageConverter }.last() as MappingJackson2HttpMessageConverter
        jsonConverter.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        super.extendMessageConverters(converters)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedMethods(
                        HttpMethod.OPTIONS.toString(),
                        HttpMethod.GET.toString(),
                        HttpMethod.HEAD.toString(),
                        HttpMethod.POST.toString(),
                        HttpMethod.PUT.toString(),
                        HttpMethod.DELETE.toString()
                )
                .allowedHeaders("*")
                .allowedOrigins(corsOrigin)
                .allowCredentials(true)
    }


}