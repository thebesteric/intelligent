package io.github.thebesteric.project.intelligent.core.model.domain.security.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * UsernamePasswordLoginRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-02 19:38:46
 */
@Data
public class UsernamePasswordLoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -3239394190344458997L;

    @Schema(description = "登录账号")
    @NotBlank(message = "登录账号不能为空")
    @Length(min = 1, max = 20, message = "账号长度在 {min}-{max} 位之间")
    protected String username;

    @Schema(description = "登录密码")
    @NotBlank(message = "登录密码不能为空")
    @Length(min = 1, max = 20, message = "密码长度在 {min}-{max} 位之间")
    protected String password;

}
