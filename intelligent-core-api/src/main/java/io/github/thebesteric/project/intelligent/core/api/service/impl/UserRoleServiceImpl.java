package io.github.thebesteric.project.intelligent.core.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.api.mapper.UserRoleMapper;
import io.github.thebesteric.project.intelligent.core.api.model.constant.RoleType;
import io.github.thebesteric.project.intelligent.core.api.model.constant.UserType;
import io.github.thebesteric.project.intelligent.core.api.model.entity.Role;
import io.github.thebesteric.project.intelligent.core.api.model.entity.User;
import io.github.thebesteric.project.intelligent.core.api.model.entity.UserRole;
import io.github.thebesteric.project.intelligent.core.api.service.RoleService;
import io.github.thebesteric.project.intelligent.core.api.service.UserRoleService;
import io.github.thebesteric.project.intelligent.core.api.service.UserService;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
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
            List<UserRole> existsUserRoles = this.list(user);
            for (Role role : roles) {
                UserRole userRole = UserRole.of(ApplicationConstants.SYSTEM_TENANT_ID, user, role, existsUserRoles);
                this.saveIfNotNull(userRole);
            }
        }
    }

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
    @Override
    public List<UserRole> list(User user) {
        return List.of();
    }
}
