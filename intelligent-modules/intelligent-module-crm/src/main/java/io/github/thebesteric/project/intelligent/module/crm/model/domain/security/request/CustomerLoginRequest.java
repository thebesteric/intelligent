package io.github.thebesteric.project.intelligent.module.crm.model.domain.security.request;

import io.github.thebesteric.project.intelligent.core.model.domain.security.request.UsernamePasswordLoginRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 客户登录请求
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-02 19:36:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerLoginRequest extends UsernamePasswordLoginRequest {
    @Serial
    private static final long serialVersionUID = 558601480637838853L;
}
