package io.github.thebesteric.project.intelligent.core.model.entity.core;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.generator.SimpleDictShortCodeGenerator;
import io.github.thebesteric.project.intelligent.core.constant.RoleType;
import io.github.thebesteric.project.intelligent.core.base.BaseTenantBizEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.List;

/**
 * Role
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-02 11:23:03
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Server.CoreApi.TABLE_NAME_PREFIX + "role")
@EntityClass(comment = "角色表", schemas = ApplicationConstants.DataSource.INTELLIGENT_CORE_API)
public class Role extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 6637738551499033827L;

    @EntityColumn(length = 64, comment = "角色编码", nullable = false)
    private String code;

    @EntityColumn(length = 64, comment = "角色名称", nullable = false)
    private String name;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, nullable = false, comment = "角色类型")
    private RoleType type;

    public static Role of(String tenantId, String name, RoleType type, SimpleDictShortCodeGenerator codeGenerator) {
        Role role = new Role();
        role.tenantId = tenantId;
        role.name = name;
        role.type = type;
        role.code = codeGenerator.generate();
        return role;
    }

    public static Role of(String tenantId, String name, RoleType type, SimpleDictShortCodeGenerator codeGenerator, List<Role> existsRoles) {
        if (existsRoles.stream().anyMatch(i -> i.name.equals(name) && i.type == type)) {
            return null;
        }
        return of(tenantId, name, type, codeGenerator);
    }

}
