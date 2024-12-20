package io.github.thebesteric.project.intelligent.core.mapper.core;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.model.entity.core.UserRole;
import io.github.thebesteric.project.intelligent.core.base.IBaseMapper;

/**
 * 用户角色
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-04 16:20:25
 */
@DS(ApplicationConstants.DataSource.INTELLIGENT_CORE_API)
public interface UserRoleMapper extends IBaseMapper<UserRole> {
}
