package io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.request;

import io.github.thebesteric.framework.agile.core.domain.page.PagingRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * PrepaidSearchRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-23 13:34:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PrepaidSearchRequest extends PagingRequest {
    @Serial
    private static final long serialVersionUID = 6861144106746307417L;

    @Schema(description = "客户名称/编号/账号/手机号")
    private String keyword;

}
