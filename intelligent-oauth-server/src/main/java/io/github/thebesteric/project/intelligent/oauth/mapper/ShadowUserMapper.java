package io.github.thebesteric.project.intelligent.oauth.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.oauth.model.entity.ShadowUser;

@DS(ApplicationConstants.DataSource.INTELLIGENT_CORE_API)
public interface ShadowUserMapper extends BaseMapper<ShadowUser> {

}
