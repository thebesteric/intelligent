package io.github.thebesteric.project.intelligent.core.model.entity.crm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseTenantBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 积分设置
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-16 17:05:00
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "score_settings", autoResultMap = true)
@EntityClass(comment = "积分设置", schemas = ApplicationConstants.Application.Module.CRM.DATASOURCE_INTELLIGENT_MODULE_CRM)
public class ScoreSettings extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = -472739815422966523L;

    @EntityColumn(type = EntityColumn.Type.JSON, comment = "进货积分规则")
    @TableField(jdbcType = JdbcType.JAVA_OBJECT, typeHandler = JacksonTypeHandler.class)
    private PurchaseScoreRules purchaseScoreRules = new PurchaseScoreRules();

    @EntityColumn(type = EntityColumn.Type.JSON, comment = "其他积分规则")
    @TableField(jdbcType = JdbcType.JAVA_OBJECT, typeHandler = JacksonTypeHandler.class)
    private OtherScoreRules otherScoreRules = new OtherScoreRules();

    @EntityColumn(type = EntityColumn.Type.JSON, comment = "积分抵扣现金设置")
    @TableField(jdbcType = JdbcType.JAVA_OBJECT, typeHandler = JacksonTypeHandler.class)
    private ScoreDeductionCashSettings scoreDeductionCashSettings = new ScoreDeductionCashSettings();

    @EntityColumn(type = EntityColumn.Type.JSON, comment = "积分抵扣运费设置")
    @TableField(jdbcType = JdbcType.JAVA_OBJECT, typeHandler = JacksonTypeHandler.class)
    private ScoreDeductionFreightSettings scoreDeductionFreightSettings = new ScoreDeductionFreightSettings();

    /** 进货积分规则 */
    @Data
    public static class PurchaseScoreRules implements Serializable {
        @Serial
        private static final long serialVersionUID = -7686622810859725521L;

        @Schema(description = "是否开启")
        private boolean enable = false;

        @Schema(description = "统一进货积分比率：金额每满 1 元奖励积分")
        private BigDecimal purchaseRate = BigDecimal.ZERO;

        @Schema(description = "商家进货积分范围")
        private List<PurchaseScope> purchaseScopes = List.of(PurchaseScope.NORMAL);

        @Schema(description = "商家进货积分周期（周一至周日）")
        private Map<Integer, Boolean> purchasePeriods = new LinkedHashMap<>();

        @Schema(description = "商家进货特殊奖励")
        private SpecialScore specialScore = new SpecialScore();

        @Schema(description = "活动商品是否参与积分")
        private boolean activityProductEnable = false;

        public PurchaseScoreRules() {
            // 设置商家进货积分周期周一到周日默认值为开启
            for (int i = 1; i <= 7; i++) {
                this.purchasePeriods.put(i, true);
            }
        }

        /** 商家进货特殊奖励 */
        @Data
        public static class SpecialScore implements Serializable {
            @Serial
            private static final long serialVersionUID = -7236012705712574285L;

            @Schema(description = "是否开启")
            private boolean enable = false;

            @Schema(description = "周期")
            private Period period = SpecialScore.Period.WEEK;

            @Schema(description = "星期，范围 1-7，分别代表周一到周日")
            private Integer weekday = 1;

            @Schema(description = "积分倍数")
            private BigDecimal multiple = BigDecimal.ONE;

            /** 周期 */
            public enum Period {
                @Schema(description = "每周")
                WEEK,
                @Schema(description = "每月")
                MONTH,
            }
        }

        /** 商家进货积分范围 */
        public enum PurchaseScope {
            @Schema(description = "普通商城订单")
            NORMAL,
            @Schema(description = "业务员代下单")
            VICARIOUS,
            @Schema(description = "手工开单")
            MANUAL
        }
    }

    /** 其他积分规则 */
    @Data
    public static class OtherScoreRules implements Serializable {
        @Serial
        private static final long serialVersionUID = 7362482938269311456L;

        @Schema(description = "签到奖励")
        private CheckInRule checkInRule = new CheckInRule();

        @Schema(description = "商品评价奖励")
        private CommentRule commentRule = new CommentRule();

        /** 连续签到奖励 */
        @Data
        public static class CheckInRule implements Serializable {
            @Serial
            private static final long serialVersionUID = 2872675605137296672L;

            @Schema(description = "是否开启")
            private boolean enable = false;

            @Schema(description = "第一天到第七天到签到奖励积分")
            private Map<Integer, BigDecimal> bonusPoints = new LinkedHashMap<>();

            public CheckInRule() {
                // 设置第一天到第七天到签到奖励积分
                for (int i = 1; i <= 7; i++) {
                    this.bonusPoints.put(i, BigDecimal.ZERO);
                }
            }
        }

        /** 商品评价奖励 */
        @Data
        public static class CommentRule implements Serializable {
            @Serial
            private static final long serialVersionUID = 6363569787797348798L;

            @Schema(description = "是否开启")
            private boolean enable = false;

            @Schema(description = "每个好评奖励积分")
            private BigDecimal score = BigDecimal.ZERO;
        }
    }

    /** 积分抵扣现金设置 */
    @Data
    public static class ScoreDeductionCashSettings implements Serializable {
        @Serial
        private static final long serialVersionUID = -2040036636967522391L;

        @Schema(description = "是否开启")
        private boolean enable = false;

        @Schema(description = "积分抵扣现金比例: 每 1 积分可抵扣的现金金额")
        private BigDecimal deductionRate = BigDecimal.ZERO;

        @Schema(description = "积分抵扣方式")
        private DeductionBy deductionBy = DeductionBy.ORDER;

        @Schema(description = "按商品抵现")
        private DeductionByProduct deductionByProduct = new DeductionByProduct();

        @Schema(description = "按订单抵现")
        private DeductionByOrder deductionByOrder = new DeductionByOrder();

        /** 按商品抵现 */
        @Data
        public static class DeductionByProduct implements Serializable {
            @Serial
            private static final long serialVersionUID = -5205067185648977879L;

            @Schema(description = "开始日期")
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
            private Date startDate;

            @Schema(description = "结束日期")
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
            private Date endDate;
        }

        /** 按订单抵现（同时设置整单金额百分比、最多抵现金额，取二者的最大金额抵扣） */
        @Data
        public static class DeductionByOrder implements Serializable {
            @Serial
            private static final long serialVersionUID = -2489088673023932442L;

            @Schema(description = "按比例抵扣：设置范围 0 到 100 的整数，0 表示不限制，小于 0 表示不按比例进行抵扣")
            private BigDecimal deductionByRate = BigDecimal.valueOf(-1);

            @Schema(description = "最多抵现金额：0 表示不限制，小于 0 表示不按金额进行抵扣")
            private BigDecimal deductionByAmount = BigDecimal.valueOf(-1);
        }

        /** 抵扣方式 */
        public enum DeductionBy {
            @Schema(description = "按商品抵扣")
            PRODUCT,
            @Schema(description = "按订单抵扣")
            ORDER
        }
    }

    /** 积分抵扣运费设置 */
    @Data
    public static class ScoreDeductionFreightSettings implements Serializable {
        @Serial
        private static final long serialVersionUID = 6309165306883731038L;

        @Schema(description = "是否开启")
        private boolean enable = false;

        @Schema(description = "积分抵扣运费比例: 每 1 积分可抵扣的现金金额")
        private BigDecimal deductionRate = BigDecimal.ZERO;

        @Schema(description = "最多抵现金额：0 表示不限制，小于 0 表示不按金额进行抵扣")
        private BigDecimal deductionByAmount = BigDecimal.valueOf(-1);
    }

}
