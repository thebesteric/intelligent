package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.github.thebesteric.framework.agile.core.domain.page.PagingRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * CustomerTypeSearchRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-23 19:35:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerTypeSearchRequest extends PagingRequest {
    @Serial
    private static final long serialVersionUID = -7604009596003330305L;

    @Schema(description = "客户类型名称")
    private String name;
}
