package com.akif.demoforehcache.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;


@Configuration
@EnableCaching
public class EhCacheConfiguration {
//    @Primary
//    @Bean(name = "ehCacheCacheManager")
//    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean factoryBean) {
//        return new EhCacheCacheManager(factoryBean.getObject());
//    }
//
//    @Bean
//    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
//        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
//        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
//        cacheManagerFactoryBean.setShared(true);
//        return cacheManagerFactoryBean;
//
//    }
}
