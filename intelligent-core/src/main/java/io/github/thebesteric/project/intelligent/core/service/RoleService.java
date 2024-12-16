package io.github.thebesteric.project.intelligent.core.service;

import io.github.thebesteric.project.intelligent.core.base.BaseRepositoryService;
import io.github.thebesteric.project.intelligent.core.constant.RoleType;
import io.github.thebesteric.project.intelligent.core.model.entity.core.Role;

import java.util.List;

/**
 * RoleService
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-04 16:18:49
 */
public interface RoleService extends BaseRepositoryService<Role> {

    /**
     * 初始化
     *
     * @author wangweijun
     * @since 2024/12/5 11:12
     */
    void init();

    /**
     * 角色列表
     *
     * @param type 角色类型
     *
     * @return List<Role>
     *
     * @author wangweijun
     * @since 2024/12/9 14:00
     */
    List<Role> list(RoleType type);
}
