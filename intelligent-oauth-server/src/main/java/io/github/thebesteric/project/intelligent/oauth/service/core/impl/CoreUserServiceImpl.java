package io.github.thebesteric.project.intelligent.oauth.service.core.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.mapper.core.UserMapper;
import io.github.thebesteric.project.intelligent.core.model.entity.core.User;
import io.github.thebesteric.project.intelligent.oauth.service.core.CoreUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * CoreUserServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-03 15:09:37
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.Application.Module.OpenApi.DATASOURCE_INTELLIGENT_MODULE_OPEN_API)
public class CoreUserServiceImpl extends ServiceImpl<UserMapper, User> implements CoreUserService {

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
    @Override
    public User getByUsername(String username) {
        return this.lambdaQuery().eq(User::getState, 1).eq(User::getUsername, username).one();
    }
}
