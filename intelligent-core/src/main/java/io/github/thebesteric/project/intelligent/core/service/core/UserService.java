package io.github.thebesteric.project.intelligent.core.service.core;

import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.core.constant.UserType;
import io.github.thebesteric.project.intelligent.core.model.domain.core.request.UserCreateRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.core.request.UserUpdateRequest;
import io.github.thebesteric.project.intelligent.core.model.entity.core.User;

import java.util.List;

public interface UserService extends IBaseService<User> {
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
     * 创建用户
     *
     * @param createRequest 请求参数
     *
     * @author wangweijun
     * @since 2024/12/5 09:18
     */
    void create(UserCreateRequest createRequest);

    /**
     * 更新用户
     *
     * @param updateRequest 请求参数
     *
     * @author wangweijun
     * @since 2024/12/5 09:18
     */
    void update(UserUpdateRequest updateRequest);

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
