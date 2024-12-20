package io.github.thebesteric.project.intelligent.core.service.core;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.core.model.entity.core.RolePrivilege;

import java.util.List;

public interface RolePrivilegeService extends IBaseService<RolePrivilege> {
    /**
     * 初始化
     *
     * @author wangweijun
     * @since 2024/12/5 11:12
     */
    void init();

    /**
     * 根据角色获取所有角色权限
     *
     * @param roleIds 角色 IDs
     *
     * @return List<RolePrivilege>
     *
     * @author wangweijun
     * @since 2024/12/16 14:33
     */
    List<RolePrivilege> listByRoleIds(Long... roleIds);
}
