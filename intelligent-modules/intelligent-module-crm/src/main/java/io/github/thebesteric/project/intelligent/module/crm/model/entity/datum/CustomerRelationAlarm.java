package io.github.thebesteric.project.intelligent.module.crm.model.entity.datum;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseTenantBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.mapper.handler.CommaStringToListTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;

import java.io.Serial;
import java.util.List;

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
@TableName(value = ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "customer_relation_alarm", autoResultMap = true)
@EntityClass(comment = "客勤预警")
public class CustomerRelationAlarm extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 6249610234226280671L;

    @EntityColumn(length = 64, nullable = false, comment = "名称")
    private String name;

    @EntityColumn(nullable = false, comment = "拜访频率（多少天拜访 1 次）")
    private Integer visitIntervalDays;

    @EntityColumn(nullable = false, length = 64, defaultExpression = "'拜访已超期'", comment = "拜访频率超期提示语")
    private String visitIntervalTips;

    @EntityColumn(nullable = false, comment = "下单频率（多少天下单 1 次）")
    private Integer orderIntervalDays;

    @EntityColumn(nullable = false, length = 64, defaultExpression = "'下单已超期'", comment = "拜访频率超期提示语")
    private String orderIntervalTips;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "客户 IDs")
    @TableField(jdbcType = JdbcType.VARCHAR, typeHandler = CommaStringToListTypeHandler.class)
    private List<String> customerIds;

    @EntityColumn(nullable = false, defaultExpression = "0", comment = "排序")
    private Integer seq = 0;

    public void setVisitIntervalTips(String visitIntervalTips) {
        this.visitIntervalTips = StringUtils.isBlank(visitIntervalTips) ? null : visitIntervalTips;
    }

    public void setOrderIntervalTips(String orderIntervalTips) {
        this.orderIntervalTips = StringUtils.isBlank(orderIntervalTips) ? null : orderIntervalTips;
    }

    public void setSeq(Integer seq) {
        this.seq = seq == null ? 0 : seq;
    }
}
