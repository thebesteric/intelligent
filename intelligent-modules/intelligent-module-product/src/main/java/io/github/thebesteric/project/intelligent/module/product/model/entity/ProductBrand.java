package io.github.thebesteric.project.intelligent.module.product.model.entity;

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
 * 商品品牌
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-11 09:09:54
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "product_brand")
@EntityClass(comment = "商品品牌")
public class ProductBrand extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 5041113100310756433L;

    @EntityColumn(length = 64, nullable = false, comment = "名称")
    private String name;

    @EntityColumn(length = 64, nullable = false, comment = "关键字")
    private String keyword;

    @EntityColumn(length = 32, comment = "别名")
    private String alias;

    @EntityColumn(length = 256, comment = "官网")
    private String website;

    @EntityColumn(length = 256, comment = "LOGO")
    private String logo;

    @EntityColumn(comment = "排序")
    private Integer seq = 0;
}
