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
 * Region
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-11 14:52:27
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "customer_region")
@EntityClass(comment = "客户所属区域")
public class CustomerRegion extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 7515290645173126386L;

    @EntityColumn(length = 64, nullable = false, comment = "区域名称")
    private String name;

    @EntityColumn(length = 64, nullable = false, comment = "区域编码")
    private String code;

    @EntityColumn(comment = "排序")
    private Integer seq = 0;

    @EntityColumn(length = 64, comment = "父级 ID")
    private Long parentId;
}
