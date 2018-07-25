package org.clouduct.springboot.configuration

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager {
        val simpleCacheManager = ConcurrentMapCacheManager("cms")
//        val bookCache = GuavaCache("book", CacheBuilder.newBuilder().build<Any, Any>())
//        val booksExpirableCache = GuavaCache("books", CacheBuilder.newBuilder()
//                .expireAfterAccess(30, TimeUnit.MINUTES)
//                .build<Any, Any>())
//        simpleCacheManager.setCaches(Arrays.asList(bookCache, booksExpirableCache))
        return simpleCacheManager
    }
}