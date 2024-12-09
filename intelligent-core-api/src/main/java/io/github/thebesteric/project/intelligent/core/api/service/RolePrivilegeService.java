package io.github.thebesteric.project.intelligent.core.api.service;

import io.github.thebesteric.project.intelligent.core.api.model.entity.Role;
import io.github.thebesteric.project.intelligent.core.api.model.entity.RolePrivilege;
import io.github.thebesteric.project.intelligent.core.base.BaseRepositoryService;

import java.util.List;

public interface RolePrivilegeService extends BaseRepositoryService<RolePrivilege> {
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
     * @param role 角色
     *
     * @return List<RolePrivilege>
     *
     * @author wangweijun
     * @since 2024/12/9 14:18
     */
    List<RolePrivilege> list(Role role);
}
