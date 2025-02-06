package io.github.thebesteric.project.intelligent.core.service.crm.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.Processor;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.exception.DataNotFoundException;
import io.github.thebesteric.project.intelligent.core.mapper.crm.ScoreSettingsMapper;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.ScoreSettingsUpdateRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreSettingsResponse;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.ScoreSettings;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.core.service.crm.ScoreSettingsService;
import io.github.thebesteric.project.intelligent.core.util.RedissonUtils;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * ScoreSettingsServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-17 17:54:10
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.Application.Module.CRM.DATASOURCE_INTELLIGENT_MODULE_CRM)
public class ScoreSettingsServiceImpl extends ServiceImpl<ScoreSettingsMapper, ScoreSettings> implements ScoreSettingsService {

    public static final String CACHE_NAME = "scoreSettingsService";

    private final RedissonClient redissonClient;

    /**
     * 积分设置-详情
     *
     * @return ScoreSettingsResponse
     *
     * @author wangweijun
     * @since 2025/1/18 09:47
     */
    @Override
    @Cacheable(cacheNames = CACHE_NAME, key = "'details'", unless = "#result == null")
    public ScoreSettingsResponse details() {
        String tenantId = SecurityUtils.getTenantIdWithException();
        ScoreSettings scoreSettings = getOrCreate(tenantId);
        return scoreSettings == null ? null : new ScoreSettingsResponse().transform(scoreSettings);
    }

    /**
     * 积分设置-更新
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2025/1/21 15:25
     */
    @Override
    @CacheEvict(value = CACHE_NAME, key = "'details'")
    public void update(ScoreSettingsUpdateRequest updateRequest) {
        Processor.prepare()
                .start(() -> getByTenantAndId(updateRequest.getTenantId(), updateRequest.getId()))
                .validate(scoreSettings -> {
                    if (scoreSettings == null) {
                        throw new DataNotFoundException("积分设置不存在");
                    }
                })
                .next(updateRequest::merge)
                .complete(this::updateById);
    }

    /**
     * 获取货创建积分设置
     *
     * @param tenantId 租户 ID
     *
     * @return ScoreSettings
     *
     * @author wangweijun
     * @since 2025/1/21 10:32
     */
    private ScoreSettings getOrCreate(String tenantId) {
        return Processor.prepare()
                .start(() -> this.lambdaQuery().eq(ScoreSettings::getTenantId, tenantId).one())
                .complete(scoreSettings -> {
                    if (scoreSettings == null) {
                        String key = RedissonUtils.getCurrentMethodNameKey(ApplicationConstants.Application.Module.CRM.NAME);
                        RLock lock = redissonClient.getLock(key);
                        boolean isLocked;
                        try {
                            isLocked = lock.tryLock(5, 10, TimeUnit.SECONDS);
                            if (isLocked) {
                                scoreSettings = new ScoreSettings();
                                scoreSettings.setTenantId(tenantId);
                                this.save(scoreSettings);
                            }
                        } catch (Exception e) {
                            Thread.currentThread().interrupt();
                        } finally {
                            if (lock.isHeldByCurrentThread() && lock.isLocked()) {
                                lock.unlock();
                            }
                        }
                    }
                    return scoreSettings;
                });
    }
}
