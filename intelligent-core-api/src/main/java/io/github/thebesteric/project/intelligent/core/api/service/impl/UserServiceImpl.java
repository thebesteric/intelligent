package io.github.thebesteric.project.intelligent.core.api.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.api.mapper.UserMapper;
import io.github.thebesteric.project.intelligent.core.api.model.constant.UserType;
import io.github.thebesteric.project.intelligent.core.api.model.domain.request.UserUpsertRequest;
import io.github.thebesteric.project.intelligent.core.api.model.entity.User;
import io.github.thebesteric.project.intelligent.core.api.service.UserService;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 初始化
     *
     * @author wangweijun
     * @since 2024/12/5 11:12
     */
    public void init() {
        List<User> exitsUsers = list(UserType.SUPER_ADMIN);
        User user = User.of(ApplicationConstants.SYSTEM_TENANT_ID, "admin", "123456",
                UserType.SUPER_ADMIN.getDesc(), "13900000000", UserType.SUPER_ADMIN, null, exitsUsers);
        this.saveIfNotNull(user);
    }

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
    @Override
    public User upsert(UserUpsertRequest request) {
        User user = request.transform(User.class);
        if (user.getId() == null) {
            user.setPassword(BCryptUtils.encode(request.getPassword()));
        }
        this.saveOrUpdate(user);
        return user;
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
