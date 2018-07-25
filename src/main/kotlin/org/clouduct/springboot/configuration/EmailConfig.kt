package org.clouduct.springboot.configuration

import com.amazonaws.regions.Regions
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class EmailConfig {

    /**
     * Email addresses must be verified before usage: https://eu-west-1.console.aws.amazon.com/ses/home?region=eu-west-1#verified-senders-email:
     */
    @Bean
    fun emailClient(): AmazonSimpleEmailService? {
        return AmazonSimpleEmailServiceClientBuilder
                .standard()
                // only EU_WEST_1 provides a SES endpoint according to http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html
                .withRegion(Regions.EU_WEST_1)
                .build()
    }

}