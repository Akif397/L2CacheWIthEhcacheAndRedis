package com.akif.demoforehcache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class EhCacheConfiguration {
    @Primary
    @Bean(name = "ehCacheCacheManager")
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean factoryBean) {
        return new EhCacheCacheManager(factoryBean.getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;

    }

//    @Bean
//    public CacheManager cacheManager() {
//        return new EhCacheCacheManager(cacheMangerFactory().getObject());
//    }
//
//    @Bean
//    public EhCacheManagerFactoryBean cacheMangerFactory() {
//        EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
//        bean.setConfigLocation(new ClassPathResource("ehcache.xml"));
//        bean.setShared(true);
//        return bean;
//    }
}
