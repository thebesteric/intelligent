package io.github.thebesteric.project.intelligent.core.security.util;

import io.github.thebesteric.framework.agile.commons.util.AbstractUtils;
import io.github.thebesteric.project.intelligent.core.constant.core.PrivilegeCode;
import io.github.thebesteric.project.intelligent.core.constant.core.RoleCode;

import java.util.List;

/**
 * UserInfoUtils
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-17 09:31:49
 */
public class UserInfoUtils extends AbstractUtils {

    public static Long getId() {
        return SecurityUtils.getIdentity();
    }

    public static String getUsername() {
        return SecurityUtils.getUsername();
    }

    public static String getTenantId() {
        return SecurityUtils.getTenantId();
    }

    public static boolean hasRole(RoleCode roleCode) {
        return SecurityUtils.getRoles().contains(roleCode.getCode());
    }

    public static List<RoleCode> getRoles() {
        return SecurityUtils.getRoles().stream().map(RoleCode::of).toList();
    }

    public static List<PrivilegeCode> getPrivileges() {
        return SecurityUtils.getAuthorities().stream().map(PrivilegeCode::of).toList();
    }

    public static boolean hasPrivilege(PrivilegeCode privilegeCode) {
        return SecurityUtils.getAuthorities().contains(privilegeCode.getCode());
    }


}
