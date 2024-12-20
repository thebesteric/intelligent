package io.github.thebesteric.project.intelligent.core.security.permission;


import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限认证器，需要开启 @EnableMethodSecurity 方法安全认证
 * 使用权限：@PreAuthorize("@auth.hasAuthority('user:add')")
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-16 20:19:39
 */
@Service("auth")
public class PermissionAuthenticator {

    private final List<PermissionExtender> permissionExtenders;

    public PermissionAuthenticator(@Autowired(required = false) List<PermissionExtender> permissionExtenders) {
        this.permissionExtenders = permissionExtenders;
    }

    /**
     * 角色判断
     *
     * @param role 角色
     *
     * @return boolean
     *
     * @author wangweijun
     * @since 2024/12/17 10:11
     */
    public boolean hasRole(String role) {
        List<String> userRoles = SecurityUtils.getRoles();
        boolean hasRole = false;
        if (!permissionExtenders.isEmpty()) {
            // 将 extension 加入 roles
            for (PermissionExtender permissionExtender : permissionExtenders) {
                userRoles.addAll(permissionExtender.rolesExtension());
            }
            // 判断是否需要直接放行
            hasRole = permissionExtenders.stream().anyMatch(p -> p.hasRole(userRoles));
        }
        return hasRole || !userRoles.isEmpty() && userRoles.contains(role);
    }

    /**
     * 角色判断（任意）
     *
     * @param role       角色
     * @param otherRoles 其他角色
     *
     * @return boolean
     *
     * @author wangweijun
     * @since 2024/12/17 10:10
     */
    public boolean hasAnyRole(String role, String... otherRoles) {
        List<String> userRoles = SecurityUtils.getRoles();
        List<String> needsRoles = new ArrayList<>();
        needsRoles.add(role);
        if (otherRoles != null && otherRoles.length > 0) {
            needsRoles.addAll(List.of(otherRoles));
        }
        // 扩展判断
        if (!permissionExtenders.isEmpty()) {
            // 将 extension 加入 roles
            for (PermissionExtender permissionExtender : permissionExtenders) {
                userRoles.addAll(permissionExtender.rolesExtension());
            }
            // 判断是否需要直接放行
            if (permissionExtenders.stream().anyMatch(p -> p.hasRole(userRoles))) {
                return true;
            }
        }
        // 权限判断
        for (String needRole : needsRoles) {
            if (userRoles.contains(needRole)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 权限判断
     *
     * @param authority 权限
     *
     * @return boolean
     *
     * @author wangweijun
     * @since 2024/12/17 10:12
     */
    public boolean hasAuthority(String authority) {
        List<String> userAuthorities = SecurityUtils.getAuthorities();
        boolean hasAuthority = false;
        if (CollectionUtils.isNotEmpty(permissionExtenders)) {
            // 将 extension 加入 authorities
            for (PermissionExtender permissionExtender : permissionExtenders) {
                userAuthorities.addAll(permissionExtender.authoritiesExtension());
            }
            // 判断是否需要直接放行
            hasAuthority = permissionExtenders.stream().anyMatch(p -> p.hasAuthority(userAuthorities));
        }
        return hasAuthority || !userAuthorities.isEmpty() && userAuthorities.contains(authority);
    }

    /**
     * 权限判断（任意）
     *
     * @param authority        权限
     * @param otherAuthorities 其他权限
     *
     * @return boolean
     *
     * @author wangweijun
     * @since 2024/12/17 10:12
     */
    public boolean hasAnyAuthority(String authority, String... otherAuthorities) {
        List<String> userAuthorities = SecurityUtils.getAuthorities();
        List<String> needsAuthorities = new ArrayList<>();
        needsAuthorities.add(authority);
        if (otherAuthorities != null && otherAuthorities.length > 0) {
            needsAuthorities.addAll(List.of(otherAuthorities));
        }
        // 扩展判断
        if (CollectionUtils.isNotEmpty(permissionExtenders)) {
            // 将 extension 加入 authorities
            for (PermissionExtender permissionExtender : permissionExtenders) {
                userAuthorities.addAll(permissionExtender.authoritiesExtension());
            }
            // 判断是否需要直接放行
            if (permissionExtenders.stream().anyMatch(p -> p.hasAuthority(userAuthorities))) {
                return true;
            }
        }
        // 权限判断
        for (String auth : needsAuthorities) {
            if (userAuthorities.contains(auth)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 范围判断
     *
     * @param scope 范围
     *
     * @return boolean
     *
     * @author wangweijun
     * @since 2024/12/17 10:13
     */
    public boolean hasScope(String scope) {
        List<String> userScopes = SecurityUtils.getScope();
        boolean hasScope = false;
        if (CollectionUtils.isNotEmpty(permissionExtenders)) {
            // 将 extension 加入 scopes
            for (PermissionExtender permissionExtender : permissionExtenders) {
                userScopes.addAll(permissionExtender.scopesExtension());
            }
            // 判断是否需要直接放行
            hasScope = permissionExtenders.stream().anyMatch(p -> p.hasScope(userScopes));
        }
        return hasScope || !userScopes.isEmpty() && userScopes.contains(scope);
    }

    /**
     * 范围判断（任意）
     *
     * @param scope       范围
     * @param otherScopes 其他范围
     *
     * @return boolean
     *
     * @author wangweijun
     * @since 2024/12/17 10:13
     */
    public boolean hasAnyScope(String scope, String... otherScopes) {
        List<String> userScopes = SecurityUtils.getScope();
        List<String> needsScopes = new ArrayList<>();
        needsScopes.add(scope);
        if (otherScopes != null && otherScopes.length > 0) {
            needsScopes.addAll(List.of(otherScopes));
        }
        // 扩展判断
        if (CollectionUtils.isNotEmpty(permissionExtenders)) {
            // 将 extension 加入 scopes
            for (PermissionExtender permissionExtender : permissionExtenders) {
                userScopes.addAll(permissionExtender.scopesExtension());
            }
            // 判断是否需要直接放行
            if (permissionExtenders.stream().anyMatch(p -> p.hasScope(userScopes))) {
                return true;
            }
        }
        // 权限判断
        for (String sc : needsScopes) {
            if (userScopes.contains(sc)) {
                return true;
            }
        }
        return false;
    }
}
