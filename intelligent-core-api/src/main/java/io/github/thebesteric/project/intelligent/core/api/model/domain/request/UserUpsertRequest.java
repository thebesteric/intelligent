package io.github.thebesteric.project.intelligent.core.api.model.domain.request;

import io.github.thebesteric.project.intelligent.core.api.model.constant.UserType;
import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.util.Date;

/**
 * UserCreateRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-04 16:55:50
 */
@Data
public class UserUpsertRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = -6860863275577558072L;

    @Schema(description = "主键")
    private String id;

    @Schema(description = "用户名")
    @NotBlank
    private String username;

    @Schema(description = "密码")
    @NotBlank
    private String password;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "电话号码")
    private String phone;

    @Schema(description = "是否是超级管理员")
    private Boolean isSuperAdmin;

    @Schema(description = "失效日期")
    private Date expiresAt;

    @Schema(description = "用户类型")
    private UserType userType = UserType.MEMBER;

}
