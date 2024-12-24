package io.github.thebesteric.project.intelligent.core.service.core.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.constant.UserType;
import io.github.thebesteric.project.intelligent.core.mapper.core.UserMapper;
import io.github.thebesteric.project.intelligent.core.model.domain.core.request.UserCreateRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.core.request.UserUpdateRequest;
import io.github.thebesteric.project.intelligent.core.model.entity.core.User;
import io.github.thebesteric.project.intelligent.core.service.core.UserService;
import io.github.thebesteric.project.intelligent.core.util.BCryptUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-25 13:47:54
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.Application.Server.CoreApi.DATASOURCE_INTELLIGENT_CORE_API)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 初始化
     *
     * @author wangweijun
     * @since 2024/12/5 11:12
     */
    public void init() {
        List<User> existsSuperAdmins = this.list(UserType.SUPER_ADMIN);
        User user = User.createSuperAdmin(existsSuperAdmins);
        this.saveIfNotNull(user);
    }

    /**
     * 创建用户
     *
     * @param createRequest 请求参数
     *
     * @return User
     *
     * @author wangweijun
     * @since 2024/12/5 09:18
     */
    @Override
    public void create(UserCreateRequest createRequest) {
        User user = createRequest.transform();
        user.setPassword(BCryptUtils.encode(createRequest.getPassword()));
        this.save(user);
    }

    /**
     * 更新用户
     *
     * @param updateRequest 请求参数
     *
     * @author wangweijun
     * @since 2024/12/5 09:18
     */
    @Override
    public void update(UserUpdateRequest updateRequest) {
        String userId = updateRequest.getId();
        User user = this.getById(userId);
        user = updateRequest.merge(user, "password");
        user.setPassword(BCryptUtils.encode(updateRequest.getPassword()));
        this.updateById(user);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author wangweijun
     * @since 2024/12/5 10:16
     */
    @Override
    public void delete(Long id) {
        this.removeById(id);
    }

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
    @Override
    public List<User> list(UserType userType) {
        return this.lambdaQuery().eq(User::getUserType, userType).list();
    }

}
