package io.github.thebesteric.project.intelligent.core.service.common.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.constant.SeedType;
import io.github.thebesteric.project.intelligent.core.mapper.common.SeedMapper;
import io.github.thebesteric.project.intelligent.core.model.entity.common.Seed;
import io.github.thebesteric.project.intelligent.core.service.common.SeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * SeedServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-17 11:56:36
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.Application.Server.CoreApi.DATASOURCE_INTELLIGENT_CORE_API)
public class SeedServiceImpl extends ServiceImpl<SeedMapper, Seed> implements SeedService {


    /**
     * 获取类型所对应的值，并自增
     *
     * @param tenantId 租户 ID
     * @param type     种子类型
     *
     * @return String
     *
     * @author wangweijun
     * @since 2024/12/17 12:42
     */
    @Override
    public synchronized String getAndIncrement(String tenantId, SeedType type) {
        Seed seed = getOrCreateSeed(tenantId, type);
        // 获取当前值
        Long current = seed.getNext();
        AtomicLong atomicLong = new AtomicLong(current);
        // 自增并保存
        long next = atomicLong.incrementAndGet();
        seed.setNext(next);
        this.updateById(seed);
        // 返回格式化后的字符串
        return formatSeed(seed);
    }

    /**
     * 获取类型所对应的值
     *
     * @param tenantId 租户 ID
     * @param type     种子类型
     *
     * @return String
     *
     * @author wangweijun
     * @since 2024/12/30 15:43
     */
    @Override
    public String get(String tenantId, SeedType type) {
        Seed seed = getOrCreateSeed(tenantId, type);
        return formatSeed(seed);
    }

    /**
     * 获取种子
     *
     * @param tenantId 租户 ID
     * @param type     种子类型
     *
     * @return Seed
     *
     * @author wangweijun
     * @since 2024/12/30 15:45
     */
    @Override
    public synchronized Seed getOrCreateSeed(String tenantId, SeedType type) {
        Seed seed = this.lambdaQuery().eq(Seed::getTenantId, tenantId).eq(Seed::getType, type).eq(Seed::getState, 1).one();
        if (seed == null) {
            seed = Seed.of(tenantId, type, 1L);
            this.save(seed);
        }
        return seed;
    }

    /**
     * 返回种子格式化后的字符串
     *
     * @param seed 种子
     *
     * @return String
     *
     * @author wangweijun
     * @since 2024/12/30 15:50
     */
    @Override
    public String formatSeed(Seed seed) {
        return String.format(seed.getPrefix() + "%0" + seed.getDigits() + "d", seed.getNext());
    }
}
