package io.github.thebesteric.project.intelligent.core.mapper.common;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.thebesteric.project.intelligent.core.base.IBaseMapper;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.model.entity.common.Area;

@DS(ApplicationConstants.DataSource.INTELLIGENT_CORE_API)
public interface AreaMapper extends IBaseMapper<Area> {
}
