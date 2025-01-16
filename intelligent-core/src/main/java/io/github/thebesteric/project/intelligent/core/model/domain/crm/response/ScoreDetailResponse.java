package io.github.thebesteric.project.intelligent.core.model.domain.crm.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.core.constant.crm.ConsumeType;
import io.github.thebesteric.project.intelligent.core.constant.crm.ScoreType;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Score;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * ScoreResponse
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-14 10:43:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScoreDetailResponse extends BaseResponse<Score> {
    @Serial
    private static final long serialVersionUID = 1118893212667328175L;

    @Schema(description = "积分类型")
    private ScoreType scoreType = ScoreType.ADD;

    @Schema(description = "消费类型")
    private ConsumeType consumeType;

    @Schema(description = "变动积分")
    private BigDecimal changeScore;

    @Schema(description = "变动时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    @Schema(description = "描述")
    private String desc;

    public Map<String, String> getScoreType() {
        return scoreType.toMap();
    }

    public Map<String, String> getConsumeType() {
        return consumeType.toMap();
    }
}
