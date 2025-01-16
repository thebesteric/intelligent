package io.github.thebesteric.project.intelligent.core.model.entity.crm;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseTenantBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.constant.crm.ConsumeType;
import io.github.thebesteric.project.intelligent.core.constant.crm.ScoreType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * Score
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-09 18:53:58
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "score", autoResultMap = true)
@EntityClass(comment = "积分表", schemas = ApplicationConstants.Application.Module.CRM.DATASOURCE_INTELLIGENT_MODULE_CRM)
@NoArgsConstructor
public class Score extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = -922713638916069250L;

    @EntityColumn(nullable = false, comment = "客户 ID")
    private Long customerId;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, nullable = false, comment = "积分类型")
    private ScoreType scoreType;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, nullable = false, comment = "消费类型")
    private ConsumeType consumeType;

    @EntityColumn(length = 10, precision = 2, nullable = false, defaultExpression = "0.00", comment = "变动积分")
    private BigDecimal changeScore;

    private Score(ScoreType scoreType, ConsumeType consumeType, BigDecimal changeScore) {
        this.scoreType = scoreType;
        this.consumeType = consumeType;
        if (ScoreType.ADD == scoreType) {
            this.changeScore = changeScore.abs();
        } else {
            this.changeScore = changeScore.signum() == -1 ? changeScore : changeScore.negate();
        }
    }

    public static Score of(ScoreType scoreType, ConsumeType consumeType, BigDecimal changeScore) {
        return new Score(scoreType, consumeType, changeScore);
    }
}
