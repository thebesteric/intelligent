package io.github.thebesteric.project.intelligent.core.security.permission;

import java.util.Collections;
import java.util.List;

public interface PermissionExtender {

    /** 角色扩展 */
    default List<String> rolesExtension() {
        return Collections.emptyList();
    }

    /** 权限扩展 */
    default List<String> authoritiesExtension() {
        return Collections.emptyList();
    }

    /** 范围扩展 */
    default List<String> scopesExtension() {
        return Collections.emptyList();
    }

    /** 是否有角色 */
    default boolean hasRole(List<String> roles) {
        return false;
    }

    /** 是否有权限 */
    default boolean hasAuthority(List<String> authorities) {
        return false;
    }

    /** 是否有范围 */
    default boolean hasScope(List<String> scopes) {
        return false;
    }
}
