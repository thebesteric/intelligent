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
 * UserRole
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-02 13:43:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Server.CoreApi.TABLE_NAME_PREFIX + "r_user_role")
@EntityClass(comment = "用户角色关联表", schemas = ApplicationConstants.DataSource.INTELLIGENT_CORE_API)
public class UserRole extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = -6891742786257676698L;

    @EntityColumn(comment = "用户 ID", nullable = false)
    private Long userId;

    @EntityColumn(comment = "角色 ID", nullable = false)
    private Long roleId;

    public static UserRole of(String tenantId, User user, Role role) {
        UserRole userRole = new UserRole();
        userRole.tenantId = tenantId;
        userRole.userId = user.getId();
        userRole.roleId = role.getId();
        return userRole;
    }

    public static UserRole of(String tenantId, User user, Role role, List<UserRole> existsUserRoles) {
        if (existsUserRoles.stream().anyMatch(i ->  i.getUserId().equals(user.getId()) && i.getRoleId().equals(role.getId()))) {
            return null;
        }
        return of(tenantId, user, role);
    }

}
