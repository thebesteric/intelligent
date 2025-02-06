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
 * 商品规格
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-22 16:53:41
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = ApplicationConstants.Application.Module.Product.TABLE_NAME_PREFIX + "spec", autoResultMap = true)
@EntityClass(comment = "商品规格", schemas = ApplicationConstants.Application.Module.Product.DATASOURCE_INTELLIGENT_MODULE_PRODUCT)
public class ProductSpec extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 7197018206385372751L;

    @EntityColumn(length = 64, nullable = false, comment = "规格名称")
    private String name;

    @EntityColumn(nullable = false, comment = "排序")
    private Integer seq = 0;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 2048, comment = "备注")
    private String comment;
}
