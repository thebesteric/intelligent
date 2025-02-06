package io.github.thebesteric.project.intelligent.modules.common.model.entity.product;

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
 * 商品规格项
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-22 16:58:41
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = ApplicationConstants.Application.Module.Product.TABLE_NAME_PREFIX + "spec_item", autoResultMap = true)
@EntityClass(comment = "商品规格项", schemas = ApplicationConstants.Application.Module.Product.DATASOURCE_INTELLIGENT_MODULE_PRODUCT)
public class ProductSpecItem extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 7262073854747662455L;

    @EntityColumn(nullable = false, comment = "商品规格 ID")
    private Long productSpecId;

    @EntityColumn(length = 64, nullable = false, comment = "规格项-名称")
    private String name;

    @EntityColumn(length = 64, comment = "规格项-别名")
    private String alias;
}
