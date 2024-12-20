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
 * 折扣信息
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-10 21:00:26
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "customer_discount_info")
@EntityClass(comment = "客户折扣信息")
public class CustomerDiscountInfo extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = -1879253468771880711L;

    @EntityColumn(nullable = false, comment = "会员等级 ID")
    private Long customerLevelId;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, comment = "折扣类型")
    private DiscountType discountType;

    @EntityColumn(nullable = false, comment = "折扣对象 ID")
    private Long discountObjectId;

    @EntityColumn(length = 5, precision = 2, nullable = false, comment = "折扣率")
    private Double discountRate = 1.0D;

    public static CustomerDiscountInfo of(String tenantId, Long customerLevelId, DiscountType discountType, Long discountObjectId, Double discountRate) {
        CustomerDiscountInfo customerDiscountInfo = new CustomerDiscountInfo();
        customerDiscountInfo.tenantId = tenantId;
        customerDiscountInfo.customerLevelId = customerLevelId;
        customerDiscountInfo.discountType = discountType;
        customerDiscountInfo.discountObjectId = discountObjectId;
        customerDiscountInfo.discountRate = discountRate;
        return customerDiscountInfo;
    }
}
