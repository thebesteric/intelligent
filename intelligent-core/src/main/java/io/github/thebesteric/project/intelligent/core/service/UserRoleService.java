package io.github.thebesteric.project.intelligent.core.service;

import io.github.thebesteric.project.intelligent.core.base.BaseRepositoryService;
import io.github.thebesteric.project.intelligent.core.model.entity.core.UserRole;

import java.util.List;

public interface UserRoleService extends BaseRepositoryService<UserRole> {
    /**
     * 初始化
     *
     * @author wangweijun
     * @since 2024/12/5 11:12
     */
    void init();

    /**
     * 根据用户获取所有用户角色关联列表
     *
     * @param userId 用户 ID
     *
     * @return List<UserRole>
     *
     * @author wangweijun
     * @since 2024/12/9 15:03
     */
    List<UserRole> listByUserId(Long userId);
}
