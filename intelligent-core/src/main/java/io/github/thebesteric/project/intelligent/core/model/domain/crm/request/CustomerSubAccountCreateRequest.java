package io.github.thebesteric.project.intelligent.core.model.domain.crm.request;

import io.github.thebesteric.framework.agile.plugins.idempotent.annotation.IdempotentKey;
import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 * CustomerSubAccountAddRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-07 16:38:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerSubAccountCreateRequest extends BaseRequest<Customer> {
    @Serial
    private static final long serialVersionUID = 6343217048352639953L;

    @IdempotentKey
    @Schema(description = "客户 ID")
    @NotNull(message = "客户 ID 不能为空")
    private Long customerId;

    @IdempotentKey
    @Schema(description = "登录账号")
    @NotBlank(message = "登录账号不能为空")
    @Length(min = 6, max = 20, message = "账号长度在 {min}-{max} 位之间")
    private String username;

    @Schema(description = "登录密码")
    @NotBlank(message = "登录密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度在 {min}-{max} 位之间")
    private String password;

    @Schema(description = "客户名称")
    @NotBlank(message = "客户名称不能为空")
    @Length(min = 6, max = 20, message = "名称长度在 {min}-{max} 位之间")
    private String name;

}
