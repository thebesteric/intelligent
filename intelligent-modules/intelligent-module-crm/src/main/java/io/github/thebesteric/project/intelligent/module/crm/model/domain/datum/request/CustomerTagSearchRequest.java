package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.github.thebesteric.framework.agile.core.domain.page.PagingRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * CustomerTagSearchRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-27 13:22:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerTagSearchRequest extends PagingRequest {
    @Serial
    private static final long serialVersionUID = -1394271785866607252L;

    @Schema(description = "标签名称/标签组名称")
    private String keyword;
}
