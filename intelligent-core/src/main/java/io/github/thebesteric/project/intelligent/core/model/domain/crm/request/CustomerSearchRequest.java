package io.github.thebesteric.project.intelligent.core.model.domain.crm.request;

import io.github.thebesteric.framework.agile.core.domain.page.PagingRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * CustomerSearchRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 20:54:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerSearchRequest extends PagingRequest {
    @Serial
    private static final long serialVersionUID = 6127094171417005942L;

    @Schema(description = "客户名称/编号/关键字/账号/手机号")
    private String keyword;

    @Schema(description = "是否只显示子账号")
    private boolean showSubAccountOnly = false;
}
