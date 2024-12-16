package io.github.thebesteric.project.intelligent.core.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.model.entity.core.RolePrivilege;
import io.github.thebesteric.project.intelligent.core.base.BaseRepositoryMapper;

/**
 * 角色权限
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-04 16:20:25
 */
@DS(ApplicationConstants.DataSource.INTELLIGENT_CORE_API)
public interface RolePrivilegeMapper extends BaseRepositoryMapper<RolePrivilege> {
}
