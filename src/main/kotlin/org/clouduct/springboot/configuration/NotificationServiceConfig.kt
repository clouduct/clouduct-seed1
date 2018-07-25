package org.clouduct.springboot.configuration

import com.amazonaws.regions.Regions
import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.AmazonSNSClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NotificationServiceConfig {

    @Bean
    fun snsClient(): AmazonSNS? {
        return AmazonSNSClientBuilder
                .standard()
                // see http://docs.aws.amazon.com/sns/latest/dg/sms_supported-countries.html
                .withRegion(Regions.EU_WEST_1)
                .build();
    }
}
