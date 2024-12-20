package io.github.thebesteric.project.intelligent.core.service.common;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.core.constant.SeedType;
import io.github.thebesteric.project.intelligent.core.model.entity.common.Seed;

public interface SeedService extends IBaseService<Seed> {

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
    String getAndIncrement(String tenantId, SeedType type);
}
