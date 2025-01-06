package io.github.thebesteric.project.intelligent.oauth.detail.core;

import io.github.thebesteric.framework.agile.commons.util.DataValidator;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthSource;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthType;
import io.github.thebesteric.project.intelligent.core.exception.BizException;
import io.github.thebesteric.project.intelligent.core.model.entity.core.*;
import io.github.thebesteric.project.intelligent.core.service.core.*;
import io.github.thebesteric.project.intelligent.oauth.detail.CustomUserDetailsService;
import io.github.thebesteric.project.intelligent.oauth.model.domain.CustomUserDetails;
import io.github.thebesteric.project.intelligent.oauth.service.core.CoreUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * CoreUserDetailService
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-03 15:31:49
 */
@Service
@RequiredArgsConstructor
public class CoreUserDetailService implements CustomUserDetailsService {

    private final CoreUserService coreUserService;

    private final UserService userService;
    private final PrivilegeService privilegeService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final RolePrivilegeService rolePrivilegeService;

    @Override
    public CustomUserDetails loadUserByIdentity(String identity) {
        User user = coreUserService.getByUsername(identity);
        // 校验用户
        checkUser(user);
        // 查找权限
        List<UserRole> userRoles = userRoleService.listByUserId(user.getId());
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).toList();
        List<Role> roles = roleService.listByIds(roleIds);
        List<RolePrivilege> rolePrivileges = rolePrivilegeService.listByRoleIds(roles.stream().map(Role::getId).toList().toArray(new Long[0]));
        Set<Long> privilegeIds = rolePrivileges.stream().map(RolePrivilege::getPrivilegeId).collect(Collectors.toSet());
        List<Privilege> privileges = privilegeService.listByIds(privilegeIds);
        // 数据封装
        return CustomUserDetails.builder()
                .id(String.valueOf(user.getId()))
                .tenantId(user.getTenantId())
                .username(user.getUsername())
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .enabled(true)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .authSource(getAuthSource().getSource())
                .authType(getAuthType().getType())
                .roles(roles.stream().map(Role::getCode).toList())
                .privileges(privileges.stream().map(Privilege::getCode).toList())
                .extra(null)
                .build();
    }

    @Override
    public AuthSource getAuthSource() {
        return AuthSource.MASTER;
    }

    @Override
    public AuthType getAuthType() {
        return AuthType.PASSWORD;
    }

    /**
     * 校验用户
     *
     * @param user user
     *
     * @author wangweijun
     * @since 2024/12/16 14:12
     */
    private void checkUser(User user) {
        DataValidator.create()
                .validate(user == null, new UsernameNotFoundException("用户或密码错误"))
                .validate(Objects.requireNonNull(user).getState() == 0, new BizException(BizException.BizCode.DATA_VALID_ERROR, "用户已禁用"))
                .validate(() -> {
                    Date currentDate = new Date();
                    Date expiresAt = user.getExpiresAt();
                    if (expiresAt != null && expiresAt.before(currentDate)) {
                        return new BizException(BizException.BizCode.DATA_VALID_ERROR, "用户已过期");
                    }
                    return null;
                });
    }
}
