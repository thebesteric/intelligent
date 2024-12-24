package io.github.thebesteric.project.intelligent.core.mapper.core;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.model.entity.core.RolePrivilege;
import io.github.thebesteric.project.intelligent.core.base.IBaseMapper;

/**
 * 角色权限
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-04 16:20:25
 */
@DS(ApplicationConstants.Application.Server.CoreApi.DATASOURCE_INTELLIGENT_CORE_API)
public interface RolePrivilegeMapper extends IBaseMapper<RolePrivilege> {
}
