package io.github.thebesteric.project.intelligent.modules.common.model.entity.product;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseTenantBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.modules.common.model.domain.Range;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.List;

/**
 * 商品目录
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-19 14:17:48
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Module.Product.TABLE_NAME_PREFIX + "catalog")
@EntityClass(comment = "商品目录", schemas = ApplicationConstants.DataSource.INTELLIGENT_MODULE_PRODUCT)
public class ProductCatalog extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 2926798117431225942L;

    @EntityColumn(length = 64, nullable = false, comment = "目录名称")
    private String name;

    @EntityColumn(nullable = false, comment = "排序")
    private Integer seq = 0;

    @EntityColumn(comment = "父级目录")
    private Long parentId;

    @EntityColumn(comment = "图标 ID")
    private Long iconImageId;

    @EntityColumn(comment = "分类图 ID")
    private Long catalogImageId;

    @EntityColumn(type = EntityColumn.Type.JSON, comment = "价格区间，单位：分")
    private List<Range> priceRange;
}
