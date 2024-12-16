package io.github.thebesteric.project.intelligent.module.crm.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseTenantBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

/**
 * 客户类型
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-11 12:41:26
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "customer_type")
@EntityClass(comment = "客户类型")
public class CustomerType extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 765634887250519727L;

    @EntityColumn(length = 64, nullable = false, comment = "客户类型名称")
    private String name;

    @EntityColumn(length = 64, nullable = false, comment = "关键字")
    private String keyword;

    @EntityColumn(nullable = false, comment = "是否默认", defaultExpression = "false")
    private Boolean isDefault = false;
}
