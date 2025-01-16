package io.github.thebesteric.project.intelligent.core.model.domain;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.thebesteric.framework.agile.plugins.database.core.domain.query.OrderByOperator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * OrderBy
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-14 10:30:24
 */
@Data
public class OrderBy implements Serializable {
    @Serial
    private static final long serialVersionUID = 3229963672079699391L;

    @Schema(description = "排序字段")
    private String name;

    @Schema(description = "排序方式")
    private OrderByOperator operator;

    public static OrderBy of(String name, OrderByOperator operator) {
        OrderBy orderBy = new OrderBy();
        orderBy.name = StringUtils.camelToUnderline(name);
        orderBy.operator = operator;
        return orderBy;
    }

    public void setName(String name) {
        this.name = StringUtils.camelToUnderline(name);;
    }
}
