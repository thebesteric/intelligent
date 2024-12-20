package io.github.thebesteric.project.intelligent.core.service.common.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.mapper.common.AreaMapper;
import io.github.thebesteric.project.intelligent.core.model.entity.common.Area;
import io.github.thebesteric.project.intelligent.core.service.common.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * AreaServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-17 11:57:53
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.DataSource.INTELLIGENT_CORE_API)
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {
}
