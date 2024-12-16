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
 * PurchaseAuthorization
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-11 13:23:23
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "customer_purchase_auth")
@EntityClass(comment = "客户采购授权")
public class CustomerPurchaseAuth extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 8521200036928773460L;

    @EntityColumn(nullable = false, comment = "客户类型 ID")
    private Long customerTypeId;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "品牌 IDs")
    private String brandIds;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "目录 IDs")
    private String categoryIds;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "仓库 IDs")
    private String warehouseIds;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "标签 IDs")
    private String tagIds;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "可购商品 IDs")
    private String enableProductIds;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "禁用商品 IDs")
    private String disableProductIds;

}
