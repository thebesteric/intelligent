package io.github.thebesteric.project.intelligent.core.resolver;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

/**
 * RedisCacheResolver
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-07-19 11:59:29
 */
public class RedisCacheResolver extends RedisCache {

    private static final String NO_CACHEABLE = "noCacheable:";

    private final String name;
    private final RedisCacheWriter cacheWriter;
    private final ConversionService conversionService;

    protected RedisCacheResolver(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfiguration) {
        super(name, cacheWriter, cacheConfiguration);
        this.name = name;
        this.cacheWriter = cacheWriter;
        this.conversionService = cacheConfiguration.getConversionService();
    }

    /**
     * 缓存删除
     *
     * @param key key
     *
     * @author wangweijun
     * @since 2024/7/19 12:00
     */
    @Override
    public void evict(Object key) {
        // 如果 key 中包含 "noCacheable:" 关键字的，就不进行缓存处理
        if (key.toString().contains(NO_CACHEABLE)) {
            return;
        }

        // 判断 key 是否是以 "*" 结尾的
        if (key instanceof String keyStr && keyStr.endsWith("*")) {
            byte[] pattern = this.conversionService.convert(this.createCacheKey(key), byte[].class);
            if (pattern != null) {
                this.cacheWriter.clean(this.name, pattern);
                return;
            }
        }

        // 删除指定的 key
        super.evict(key);
    }
}
