package io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request;

import io.github.thebesteric.framework.agile.core.domain.page.PagingRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * ProductTagSearchRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 18:05:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductTagSearchRequest extends PagingRequest {
    @Serial
    private static final long serialVersionUID = 4662376617150524459L;

    @Schema(description = "标签名称")
    private String name;
}
