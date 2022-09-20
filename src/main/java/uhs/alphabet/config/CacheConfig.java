package uhs.alphabet.config;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

@EnableCaching
@Configuration
public class CacheConfig {
    @Bean
    public CacheManager ehcacheManager() {
        CacheConfiguration<String, String> cacheConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class,
                        String.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .heap(10)
                                .build())
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofDays(1)))
                .build();

        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();

        javax.cache.configuration.Configuration<String, String > configuration = Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfig);
        if (cacheManager.getCache("codeforcesCache") == null) {
            cacheManager.createCache("codeforcesCache", configuration);
        }
        return cacheManager;
    }

}
