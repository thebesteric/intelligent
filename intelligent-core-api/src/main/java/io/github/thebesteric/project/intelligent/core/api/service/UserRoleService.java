package io.github.thebesteric.project.intelligent.core.api.service;

import io.github.thebesteric.project.intelligent.core.api.model.entity.User;
import io.github.thebesteric.project.intelligent.core.api.model.entity.UserRole;
import io.github.thebesteric.project.intelligent.core.base.BaseRepositoryService;

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
     * @param user 用户
     *
     * @return List<UserRole>
     *
     * @author wangweijun
     * @since 2024/12/9 15:03
     */
    List<UserRole> list(User user);
}
