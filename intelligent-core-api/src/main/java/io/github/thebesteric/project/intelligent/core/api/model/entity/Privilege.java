package io.github.thebesteric.project.intelligent.core.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.api.generator.SimpleDictShortCodeGenerator;
import io.github.thebesteric.project.intelligent.core.api.model.constant.PrivilegeType;
import io.github.thebesteric.project.intelligent.core.base.BaseBizEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.List;

/**
 * Privilege
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-02 11:26:05
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("core_t_privilege")
@EntityClass(comment = "权限表")
public class Privilege extends BaseBizEntity {
    @Serial
    private static final long serialVersionUID = 7775807590136562571L;

    @EntityColumn(length = 64, comment = "父级权限 ID")
    private Long parentId;

    @EntityColumn(length = 64, unique = true, nullable = false, comment = "权限编码")
    private String code;

    @EntityColumn(length = 64, nullable = false, comment = "权限名称")
    private String name;

    @EntityColumn(type = EntityColumn.Type.TINY_INT, nullable = false, comment = "权限级别")
    private Integer level;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, nullable = false, comment = "权限类型")
    private PrivilegeType type;

    public static Privilege of(String name, PrivilegeType type, SimpleDictShortCodeGenerator codeGenerator) {
        Privilege privilege = new Privilege();
        privilege.name = name;
        privilege.type = type;
        privilege.code = codeGenerator.generate();
        privilege.level = 1;
        return privilege;
    }

    public static Privilege of(String name, PrivilegeType type, SimpleDictShortCodeGenerator codeGenerator, List<Privilege> existsPrivileges) {
        if (existsPrivileges.stream().anyMatch(i -> i.name.equals(name) && i.type == type)) {
            return null;
        }
        return of(name, type, codeGenerator);
    }

    public static Privilege of(Privilege parent, String name, PrivilegeType type, SimpleDictShortCodeGenerator codeGenerator, List<Privilege> existsPrivileges) {
        Privilege privilege = of(name, type, codeGenerator, existsPrivileges);
        if (privilege != null && parent != null) {
            privilege.parentId = parent.getId();
            privilege.level = parent.level + 1;
        }
        return privilege;
    }

}
