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
 * 商品标签
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 13:39:46
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "product_tag")
@EntityClass(comment = "商品标签")
public class ProductTag extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 8584592594287753227L;

    @EntityColumn(length = 64, nullable = false, comment = "名称")
    private String name;

    @EntityColumn(comment = "排序")
    private Integer seq = 0;
}
