package io.github.thebesteric.project.intelligent.core.service.common;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.core.constant.SeedType;
import io.github.thebesteric.project.intelligent.core.model.entity.common.Seed;

public interface SeedService extends IBaseService<Seed> {

    /**
     * 获取种子类型所对应的值，并自增
     *
     * @param tenantId 租户 ID
     * @param type     种子类型
     *
     * @return String
     *
     * @author wangweijun
     * @since 2024/12/17 12:42
     */
    String getAndIncrement(String tenantId, SeedType type);


    /**
     * 获取种子类型所对应的值
     *
     * @param tenantId 租户 ID
     * @param type     种子类型
     *
     * @return String
     *
     * @author wangweijun
     * @since 2024/12/30 15:43
     */
    String get(String tenantId, SeedType type);

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
    Seed getOrCreateSeed(String tenantId, SeedType type);

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
    String formatSeed(Seed seed);
}
