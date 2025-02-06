package io.github.thebesteric.project.intelligent.modules.common.model.entity.crm;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseTenantBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.modules.common.constant.PrepaidChangeProject;
import io.github.thebesteric.project.intelligent.modules.common.constant.PrepaidType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.annotation.Nullable;
import java.io.Serial;
import java.math.BigDecimal;

/**
 * 预存款
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-22 17:09:33
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "prepaid", autoResultMap = true)
@EntityClass(comment = "预存款", schemas = ApplicationConstants.Application.Module.CRM.DATASOURCE_INTELLIGENT_MODULE_CRM)
public class Prepaid extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = -6509428229890564904L;

    @EntityColumn(nullable = false, comment = "客户 ID")
    private Long customerId;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, nullable = false, comment = "变动类型")
    private PrepaidType type;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, nullable = false, comment = "变动项目")
    private PrepaidChangeProject changeProject;

    @EntityColumn(length = 10, precision = 2, nullable = false, defaultExpression = "0.00", comment = "变动金额")
    private BigDecimal changeAmount = BigDecimal.ZERO;

    @EntityColumn(length = 10, precision = 2, nullable = false, defaultExpression = "0.00", comment = "支付金额")
    private BigDecimal payAmount = BigDecimal.ZERO;

    @EntityColumn(length = 10, precision = 2, nullable = false, defaultExpression = "0.00", comment = "奖励金额")
    private BigDecimal rewardAmount = BigDecimal.ZERO;

    @EntityColumn(length = 10, precision = 2, nullable = false, defaultExpression = "0.00", comment = "可用余额")
    private BigDecimal balanceAmount = BigDecimal.ZERO;

    @EntityColumn(length = 10, precision = 2, nullable = false, defaultExpression = "0.00", comment = "手续费")
    private BigDecimal feeAmount = BigDecimal.ZERO;

    public static Prepaid convertToAdjust(String tenantId, Long customerId, PrepaidType type, BigDecimal changeAmount, String desc, @Nullable Prepaid latestPrepaid) {
        Prepaid prepaid = new Prepaid();
        prepaid.tenantId = tenantId;
        prepaid.customerId = customerId;
        prepaid.type = type;
        prepaid.changeProject = PrepaidChangeProject.MANUAL_ADJUST;
        prepaid.changeAmount = type == PrepaidType.ADD ? changeAmount.abs() : changeAmount.abs().negate();
        prepaid.desc = desc;
        prepaid.balanceAmount = latestPrepaid == null ? prepaid.changeAmount : latestPrepaid.balanceAmount.add(prepaid.changeAmount);
        return prepaid;
    }

}
