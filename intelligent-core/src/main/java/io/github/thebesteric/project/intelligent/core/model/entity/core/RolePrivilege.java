package io.github.thebesteric.project.intelligent.core.model.entity.core;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseTenantBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * @Author guidy
 * @Date 2024-09-30 11:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Server.CoreApi.TABLE_NAME_PREFIX + "r_role_privilege")
@EntityClass(comment = "角色权限关联表", schemas = ApplicationConstants.Application.Server.CoreApi.DATASOURCE_INTELLIGENT_CORE_API)
public class RolePrivilege extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 2232543994904695027L;

    @EntityColumn(comment = "角色 ID", nullable = false)
    private Long roleId;

    @EntityColumn(comment = "权限 ID", nullable = false)
    private Long privilegeId;

    public static RolePrivilege of(String tenantId, Role role, Privilege privilege) {
        RolePrivilege rolePrivilege = new RolePrivilege();
        rolePrivilege.tenantId = tenantId;
        rolePrivilege.roleId = role.getId();
        rolePrivilege.privilegeId = privilege.getId();
        return rolePrivilege;
    }

    public static RolePrivilege of(String tenantId, Role role, Privilege privilege, List<RolePrivilege> existsRolePrivileges) {
        if (existsRolePrivileges.stream().anyMatch(i -> i.getRoleId().equals(role.getId()) && i.getPrivilegeId().equals(privilege.getId()))) {
            return null;
        }
        return of(tenantId, role, privilege);
    }

}
