package com.javatest.refactor.dao.config.cache;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "com.dph.search.facade.translate.cache.spec")
@EnableCaching(proxyTargetClass = true)
public class CacheConfiguration {

    @Value("${com.dph.search.facade.translate.cache.spec}")
    private String cacheSpecification;

    @Bean
    public CacheManager cacheManager() {
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        cacheManager.setCacheSpecification(cacheSpecification);
        cacheManager.setCacheNames(Lists.newArrayList("default"));

        return cacheManager;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PersonKeyGenerator();
    }

    @Bean
    public CacheResolver cacheResolver(CacheManager cacheManager) {
        return new SimpleCacheResolver(cacheManager);
    }
}
