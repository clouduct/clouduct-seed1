package org.clouduct.springboot.configuration

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.AmazonSNSClientBuilder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3ServiceConfig {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Value("\${app.aws.defaultRegion}")
    private val region: String? = null



    @Bean
    fun s3Client(): AmazonS3? {
        val provider = ProfileCredentialsProvider()

        try {
            logger.info("{} ({})", provider.credentials.awsAccessKeyId, System.getenv("AWS_PROFILE"))
        } catch(ex: Exception) {
            logger.error(ex.message)
        }
        return AmazonS3ClientBuilder.standard().withRegion(region).build();
    }
}
