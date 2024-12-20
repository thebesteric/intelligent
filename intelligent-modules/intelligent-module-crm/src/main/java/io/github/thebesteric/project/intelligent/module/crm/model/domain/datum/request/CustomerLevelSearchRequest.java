package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import io.github.thebesteric.framework.agile.core.domain.page.PagingRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 客户等级-查询
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-18 15:49:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerLevelSearchRequest extends PagingRequest {
    @Serial
    private static final long serialVersionUID = 8804515197366239049L;

    @Schema(description = "名称")
    private String name;
}
