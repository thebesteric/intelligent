package io.github.thebesteric.project.intelligent.oauth.service.core;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.core.model.entity.core.User;

/**
 * CoreUserService
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-03 15:08:24
 */
public interface CoreUserService extends IBaseService<User> {

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     *
     * @return User
     *
     * @author wangweijun
     * @since 2025/1/3 14:30
     */
    User getByUsername(String username);

}
