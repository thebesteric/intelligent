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
 * 客户所属区域-关联用户
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-11 14:52:27
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "customer_region_user")
@EntityClass(comment = "客户所属区域-关联用户")
public class CustomerRegionUser extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = -8839172611095052564L;

    @EntityColumn(nullable = false, comment = "区域 ID")
    private Long regionId;

    @EntityColumn(nullable = false, comment = "客户 ID")
    private Long customerId;

}
