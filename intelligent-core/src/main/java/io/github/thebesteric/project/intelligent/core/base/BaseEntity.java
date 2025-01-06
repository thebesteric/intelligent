package io.github.thebesteric.project.intelligent.core.base;

import com.baomidou.mybatisplus.annotation.*;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.beans.Introspector;
import java.beans.Transient;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public abstract class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -4420494594607379574L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @EntityColumn(type = EntityColumn.Type.BIG_INT, primary = true, comment = "主键")
    protected Long id;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @EntityColumn(type = EntityColumn.Type.DATETIME, comment = "创建日期")
    protected Date createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    @EntityColumn(type = EntityColumn.Type.DATETIME, comment = "更新日期")
    protected Date updatedAt;

    @TableField(value = "created_by", fill = FieldFill.INSERT)
    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, comment = "创建人")
    protected String createdBy;

    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, comment = "更新人")
    protected String updatedBy;

    @TableField(value = "`desc`")
    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 255, comment = "描述")
    protected String desc;

    @Transient
    public String getTableName() {
        String tableName = null;
        Class<? extends BaseEntity> entityClass = this.getClass();
        if (entityClass.isAnnotationPresent(EntityClass.class)) {
            tableName = entityClass.getAnnotation(EntityClass.class).value();
        }
        if (StringUtils.isBlank(tableName) && entityClass.isAnnotationPresent(TableName.class)) {
            tableName = entityClass.getAnnotation(TableName.class).value();
        }
        return StringUtils.isNotBlank(tableName) ? tableName : Introspector.decapitalize(this.getClass().getSimpleName());
    }
}