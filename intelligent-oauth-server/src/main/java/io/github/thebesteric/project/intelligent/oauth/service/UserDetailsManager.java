package io.github.thebesteric.project.intelligent.oauth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.thebesteric.project.intelligent.core.exception.BizException;
import io.github.thebesteric.project.intelligent.core.model.entity.core.*;
import io.github.thebesteric.project.intelligent.core.service.*;
import io.github.thebesteric.project.intelligent.oauth.model.domain.UserDomain;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * InDBUserDetailsManager
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-25 13:33:29
 */
@RequiredArgsConstructor
public class UserDetailsManager implements UserDetailsService {

    @Resource
    private UserService userService;
    @Resource
    private PrivilegeService privilegeService;
    @Resource
    private RoleService roleService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RolePrivilegeService rolePrivilegeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getState, 1)
                .eq(User::getUsername, username);
        User user = userService.getOne(queryWrapper);
        UserDomain userDomain = checkUser(user);

        // 查找权限
        List<UserRole> userRoles = userRoleService.listByUserId(user.getId());
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).toList();
        List<Role> roles = roleService.listByIds(roleIds);
        List<RolePrivilege> rolePrivileges = rolePrivilegeService.listByRoleIds(roles.stream().map(Role::getId).toList().toArray(new Long[0]));
        Set<Long> privilegeIds = rolePrivileges.stream().map(RolePrivilege::getPrivilegeId).collect(Collectors.toSet());
        List<Privilege> privileges = privilegeService.listByIds(privilegeIds);

        // 数据封装
        userDomain.setRoles(roles);
        userDomain.setPrivileges(privileges);
        userDomain.setAuthorities((AuthorityUtils.createAuthorityList(privileges.stream().map(Privilege::getCode).toList())));

        return userDomain;
    }

    /**
     * 校验用户
     *
     * @param user user
     *
     * @return UserDomain
     *
     * @author wangweijun
     * @since 2024/12/16 14:12
     */
    private UserDomain checkUser(User user) {
        if (user == null) {
            throw new UsernameNotFoundException("用户或密码错误");
        }
        if (user.getState() == 0) {
            throw new BizException(BizException.BizCode.DATA_VALID_ERROR, "用户已禁用");
        }
        Date currentDate = new Date();
        Date expiresAt = user.getExpiresAt();
        if (expiresAt != null && expiresAt.before(currentDate)) {
            throw new BizException(BizException.BizCode.DATA_VALID_ERROR, "用户已过期");
        }
        return UserDomain.of(user);
    }
}
