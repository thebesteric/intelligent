package io.github.thebesteric.project.intelligent.core.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.thebesteric.framework.agile.commons.util.MessageUtils;
import io.github.thebesteric.project.intelligent.core.properties.ApplicationProperties;
import io.github.thebesteric.project.intelligent.core.resolver.RedisCacheManagerCustomized;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * SpringCachingConfig
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-07-17 15:58:08
 */
@Configuration
@EnableCaching
@ConditionalOnProperty(prefix = "application.components.caching", name = "enable", havingValue = "true")
public class SpringCachingConfig {

    @Bean
    @ConditionalOnMissingBean
    public CacheManager cacheManager(ApplicationProperties applicationProperties, @Nullable RedisConnectionFactory redisConnectionFactory) {
        ApplicationProperties.Components.Caching caching = applicationProperties.getComponents().getCaching();
        ApplicationProperties.Components.Caching.CacheType cacheType = caching.getCacheType();
        String cacheNamePrefix = caching.getCacheNamePrefix();
        String cacheNameSuffix = caching.getCacheNameSuffix();
        String cacheNameSpectator = caching.getCacheNameSpectator();
        Duration entryTtl = caching.getEntryTtl();
        Set<String> cacheNames = caching.getCacheNames();

        CacheManager cacheManager = null;
        // CAFFEINE 缓存实现
        if (ApplicationProperties.Components.Caching.CacheType.CAFFEINE == cacheType) {
            CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
            Caffeine<Object, Object> caffeine = Caffeine.newBuilder().maximumSize(caching.getMaximumSize()).expireAfterAccess(entryTtl);
            // Caffeine 实现类
            caffeineCacheManager.setCaffeine(caffeine);
            // 缓存名称
            caffeineCacheManager.setCacheNames(cacheNames);
            cacheManager = caffeineCacheManager;
        }
        // REDIS 缓存实现
        else if (ApplicationProperties.Components.Caching.CacheType.REDIS == cacheType) {
            if (redisConnectionFactory == null) {
                throw new IllegalArgumentException("RedisConnectionFactory can not be null in Cache Redis implementation");
            }
            // 配置序列化
            String cacheNameFormatter = "cacheable" + cacheNameSpectator + cacheNamePrefix + cacheNameSpectator + cacheNameSuffix + cacheNameSpectator + "{}" + cacheNameSpectator;
            RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                    // 过期时间
                    .entryTtl(entryTtl)
                    // 缓存 key
                    .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisConfig.stringSerializer))
                    // 缓存组件 value
                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisConfig.jackson2JsonRedisSerializer))
                    // value 不为空
                    .disableCachingNullValues()
                    // 缓存 key 的前缀
                    .computePrefixWith(cacheName -> MessageUtils.format(cacheNameFormatter, cacheName));

            // cacheManager = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(redisCacheConfiguration).build();
            cacheManager = new RedisCacheManagerCustomized(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory), redisCacheConfiguration);
        }
        // ConcurrentHashMap 缓存实现
        else if (ApplicationProperties.Components.Caching.CacheType.HASHMAP == cacheType) {
            SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
            Set<Cache> caches = new HashSet<>();
            for (String cacheName : cacheNames) {
                caches.add(new ConcurrentMapCache(cacheName));
            }
            simpleCacheManager.setCaches(caches);
            cacheManager = simpleCacheManager;
        }

        if (cacheManager == null) {
            throw new IllegalArgumentException("Please check the cache configuration is correct, using HASHMAP, CAFFEINE or REDIS instead");
        }

        return cacheManager;
    }

}
