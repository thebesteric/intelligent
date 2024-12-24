package io.github.thebesteric.project.intelligent.modules.common.model.domain.stock.request;

import io.github.thebesteric.framework.agile.core.domain.page.PagingRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * WarehouseSearchRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 17:50:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WarehouseSearchRequest extends PagingRequest {
    @Serial
    private static final long serialVersionUID = 26805343334247693L;

    @Schema(description = "仓库编号/名称")
    private String keyword;
}
