package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.configCache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@EnableAsync
@EnableScheduling
class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("cotization");
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void clearCache() {
        cacheManager().getCache("cotization").clear();
    }

}