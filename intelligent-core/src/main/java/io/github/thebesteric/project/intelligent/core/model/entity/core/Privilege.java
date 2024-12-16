package io.github.thebesteric.project.intelligent.core.model.entity.core;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.constant.PrivilegeCode;
import io.github.thebesteric.project.intelligent.core.constant.PrivilegeType;
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
@TableName(ApplicationConstants.Application.Server.CoreApi.TABLE_NAME_PREFIX + "privilege")
@EntityClass(comment = "权限表", schemas = ApplicationConstants.DataSource.INTELLIGENT_CORE_API)
public class Privilege extends BaseBizEntity {
    @Serial
    private static final long serialVersionUID = 7775807590136562571L;

    @EntityColumn(length = 64, comment = "父级 ID")
    private Long parentId;

    @EntityColumn(length = 64, unique = true, nullable = false, comment = "权限编码")
    private String code;

    @EntityColumn(length = 64, nullable = false, comment = "权限名称")
    private String name;

    @EntityColumn(length = 32, nullable = false, comment = "权限顺序")
    private String seq;

    @EntityColumn(type = EntityColumn.Type.TINY_INT, nullable = false, comment = "权限级别")
    private Integer level;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, nullable = false, comment = "权限类型")
    private PrivilegeType type;

    public static Privilege of(String name, PrivilegeType type, String code, String seq) {
        Privilege privilege = new Privilege();
        privilege.name = name;
        privilege.seq = seq;
        privilege.type = type;
        privilege.code = code;
        privilege.level = 1;
        return privilege;
    }

    public static Privilege of(PrivilegeCode privilegeCode, List<Privilege> existsPrivileges) {
        String name = privilegeCode.getName();
        PrivilegeType type = privilegeCode.getType();
        if (existsPrivileges.stream().anyMatch(i -> i.name.equals(name) && i.type == type)) {
            return null;
        }
        return of(name, type, privilegeCode.getCode(), privilegeCode.getSeq());
    }

    public static Privilege of(Privilege parent, PrivilegeCode privilegeCode, List<Privilege> existsPrivileges) {
        Privilege privilege = of(privilegeCode, existsPrivileges);
        if (privilege != null && parent != null) {
            privilege.parentId = parent.getId();
            privilege.level = parent.level + 1;
        }
        return privilege;
    }

    public static Privilege of(String name, PrivilegeType type, String code, String seq, List<Privilege> existsPrivileges) {
        if (existsPrivileges.stream().anyMatch(i -> i.name.equals(name) && i.type == type)) {
            return null;
        }
        return of(name, type, code, seq);
    }

    public static Privilege of(Privilege parent, String name, PrivilegeType type, String code, String seq, List<Privilege> existsPrivileges) {
        Privilege privilege = of(name, type, code, seq, existsPrivileges);
        if (privilege != null && parent != null) {
            privilege.parentId = parent.getId();
            privilege.level = parent.level + 1;
        }
        return privilege;
    }

}
