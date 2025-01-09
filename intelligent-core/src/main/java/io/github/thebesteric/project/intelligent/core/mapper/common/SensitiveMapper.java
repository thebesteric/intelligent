package io.github.thebesteric.project.intelligent.core.mapper.common;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.thebesteric.project.intelligent.core.base.IBaseMapper;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.model.entity.common.Sensitive;

@DS(ApplicationConstants.Application.Module.OpenApi.DATASOURCE_INTELLIGENT_MODULE_OPEN_API)
public interface SensitiveMapper extends IBaseMapper<Sensitive> {
}
