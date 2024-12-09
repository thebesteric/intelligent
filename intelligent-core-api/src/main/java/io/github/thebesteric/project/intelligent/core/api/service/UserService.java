package io.github.thebesteric.project.intelligent.core.api.service;

import io.github.thebesteric.project.intelligent.core.api.model.constant.UserType;
import io.github.thebesteric.project.intelligent.core.api.model.domain.request.UserUpsertRequest;
import io.github.thebesteric.project.intelligent.core.api.model.entity.User;
import io.github.thebesteric.project.intelligent.core.base.BaseRepositoryService;

import java.util.List;

public interface UserService extends BaseRepositoryService<User> {
    /**
     * 初始化
     *
     * @author wangweijun
     * @since 2024/12/5 11:12
     */
    void init();

    /**
     * 根据用户类型获取用户列表
     *
     * @param userType 用户类型
     *
     * @return List<User>
     *
     * @author wangweijun
     * @since 2024/12/9 14:50
     */
    List<User> list(UserType userType);

    /**
     * 更新或新增用呢
     *
     * @param request 请求参数
     *
     * @return User
     *
     * @author wangweijun
     * @since 2024/12/5 09:18
     */
    User upsert(UserUpsertRequest request);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author wangweijun
     * @since 2024/12/5 10:16
     */
    void delete(Long id);
}
