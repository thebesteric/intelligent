package io.github.thebesteric.project.intelligent.core.mapper.common;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.thebesteric.project.intelligent.core.base.IBaseMapper;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.model.entity.common.Seed;

/**
 * SeedMapper
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-17 11:54:35
 */
@DS(ApplicationConstants.Application.Server.CoreApi.DATASOURCE_INTELLIGENT_CORE_API)
public interface SeedMapper extends IBaseMapper<Seed> {
}
