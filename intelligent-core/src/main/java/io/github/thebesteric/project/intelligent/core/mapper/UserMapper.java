package io.github.thebesteric.project.intelligent.core.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.thebesteric.project.intelligent.core.base.BaseRepositoryMapper;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.model.entity.core.User;

/**
 * 用户
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-04 16:20:25
 */
@DS(ApplicationConstants.DataSource.INTELLIGENT_CORE_API)
public interface UserMapper extends BaseRepositoryMapper<User> {
}