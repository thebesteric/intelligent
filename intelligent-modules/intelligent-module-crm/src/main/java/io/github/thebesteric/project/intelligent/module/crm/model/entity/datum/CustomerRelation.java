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
 * 客勤预警
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 11:02:01
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "customer_relation")
@EntityClass(comment = "客勤预警")
public class CustomerRelation extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 6249610234226280671L;

    @EntityColumn(length = 64, nullable = false, comment = "名称")
    private String name;

    @EntityColumn(nullable = false, comment = "拜访频率（多少天拜访 1 次）")
    private Integer visitFrequency;

    @EntityColumn(nullable = false, comment = "下单频率（多少天下单 1 次）")
    private Integer orderFrequency;
}
