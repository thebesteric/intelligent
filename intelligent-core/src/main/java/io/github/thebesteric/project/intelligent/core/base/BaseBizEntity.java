package io.github.thebesteric.project.intelligent.core.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public abstract class BaseBizEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 6034991749333824043L;

    @TableField(value = "extra")
    @EntityColumn(type = EntityColumn.Type.JSON, comment = "扩展字段")
    protected String extra;

    @TableField(value = "state")
    @EntityColumn(type = EntityColumn.Type.SMALL_INT, defaultExpression = "1", comment = "状态：0-禁用，1-启用（可根据业务定义或扩充）")
    protected Integer state = 1;

    @Version
    @EntityColumn(type = EntityColumn.Type.INT, defaultExpression = "0", comment = "乐观锁")
    protected Integer version = 0;

    @TableLogic(value = "0", delval = "1")
    @EntityColumn(type = EntityColumn.Type.TINY_INT, defaultExpression = "false", comment = "逻辑删除：无需手动维护。0-正常，1-已删除")
    protected Boolean deleted = false;
}