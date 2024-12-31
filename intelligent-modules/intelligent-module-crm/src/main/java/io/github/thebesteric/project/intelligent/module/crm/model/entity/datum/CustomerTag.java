package io.github.thebesteric.project.intelligent.module.crm.model.entity.datum;

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
 * 客户标签
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-27 11:49:37
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "customer_tag", autoResultMap = true)
@EntityClass(comment = "客户标签")
public class CustomerTag extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = -2586229806559650283L;

    @EntityColumn(length = 64, nullable = false, comment = "标签名称")
    private String name;

    @EntityColumn(nullable = false, comment = "标签组 ID")
    private Long groupId;

}
