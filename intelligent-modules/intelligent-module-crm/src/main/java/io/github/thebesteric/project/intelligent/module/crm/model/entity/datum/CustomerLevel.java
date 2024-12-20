package io.github.thebesteric.project.intelligent.module.crm.model.entity.datum;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseTenantBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.module.crm.constant.DiscountType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

/**
 * 客户等级
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-10 17:05:30
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "customer_level")
@EntityClass(comment = "客户等级")
public class CustomerLevel extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = -1725852564646596245L;

    @EntityColumn(length = 32, nullable = false, comment = "名称")
    private String name;

    @EntityColumn(comment = "上一个等级")
    private Long preLevelId;

    @EntityColumn(length = 5, precision = 2, nullable = false, comment = "折扣率")
    private Double discountRate = 1.0D;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, comment = "折扣类型")
    private DiscountType discountType;

    @EntityColumn(nullable = false, comment = "是否默认", defaultExpression = "false")
    private Boolean isDefault = false;

    @EntityColumn(nullable = false, comment = "自动升级", defaultExpression = "false")
    private Boolean autoUpgrade = false;

    @EntityColumn(comment = "升级积分")
    private Integer upgradeScore;


}
