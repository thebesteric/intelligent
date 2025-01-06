package io.github.thebesteric.project.intelligent.core.service.core.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.constant.core.RoleType;
import io.github.thebesteric.project.intelligent.core.constant.core.UserType;
import io.github.thebesteric.project.intelligent.core.mapper.core.UserRoleMapper;
import io.github.thebesteric.project.intelligent.core.model.entity.core.Role;
import io.github.thebesteric.project.intelligent.core.model.entity.core.User;
import io.github.thebesteric.project.intelligent.core.model.entity.core.UserRole;
import io.github.thebesteric.project.intelligent.core.service.core.RoleService;
import io.github.thebesteric.project.intelligent.core.service.core.UserRoleService;
import io.github.thebesteric.project.intelligent.core.service.core.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * UserRoleServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-04 16:27:38
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.Application.Module.OpenApi.DATASOURCE_INTELLIGENT_MODULE_OPEN_API)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    private final UserService userService;
    private final RoleService roleService;

    /**
     * 初始化
     *
     * @author wangweijun
     * @since 2024/12/5 11:12
     */
    @Transactional
    @Override
    public void init() {
        List<User> users = userService.list(UserType.SUPER_ADMIN);
        List<Role> roles = roleService.list(RoleType.SYSTEM);
        for (User user : users) {
            List<UserRole> existsUserRoles = this.listByUserId(user.getId());
            for (Role role : roles) {
                UserRole userRole = UserRole.of(ApplicationConstants.SYSTEM_TENANT_ID, user, role, existsUserRoles);
                this.saveIfNotNull(userRole);
            }
        }
    }

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
    @Override
    public List<UserRole> listByUserId(Long userId) {
        return this.lambdaQuery().eq(UserRole::getUserId, userId).eq(UserRole::getState, 1).list();
    }
}
