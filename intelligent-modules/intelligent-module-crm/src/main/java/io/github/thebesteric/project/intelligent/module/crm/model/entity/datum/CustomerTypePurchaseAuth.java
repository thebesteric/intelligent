package io.github.thebesteric.project.intelligent.module.crm.model.entity.datum;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseTenantBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.mapper.handler.CommaStringToListTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import java.io.Serial;
import java.util.List;

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
@TableName(value = ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "customer_type_purchase_auth", autoResultMap = true)
@EntityClass(comment = "客户类型采购授权")
public class CustomerTypePurchaseAuth extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 8521200036928773460L;

    @EntityColumn(nullable = false, comment = "客户类型 ID")
    private Long customerTypeId;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "品牌 IDs")
    @TableField(jdbcType = JdbcType.VARCHAR, typeHandler = CommaStringToListTypeHandler.class)
    private List<String> brandIds;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "目录 IDs")
    @TableField(jdbcType = JdbcType.VARCHAR, typeHandler = CommaStringToListTypeHandler.class)
    private List<String> categoryIds;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "仓库 IDs")
    @TableField(jdbcType = JdbcType.VARCHAR, typeHandler = CommaStringToListTypeHandler.class)
    private List<String> warehouseIds;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "标签 IDs")
    @TableField(jdbcType = JdbcType.VARCHAR, typeHandler = CommaStringToListTypeHandler.class)
    private List<String> tagIds;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "可购商品 IDs")
    @TableField(jdbcType = JdbcType.VARCHAR, typeHandler = CommaStringToListTypeHandler.class)
    private List<String> enableProductIds;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "禁购商品 IDs")
    @TableField(jdbcType = JdbcType.VARCHAR, typeHandler = CommaStringToListTypeHandler.class)
    private List<String> disableProductIds;

}
