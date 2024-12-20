package io.github.thebesteric.project.intelligent.core.model.domain.core.request;

import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.core.constant.UserType;
import io.github.thebesteric.project.intelligent.core.model.entity.core.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * 用户创建请求
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-04 16:55:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserCreateRequest extends BaseRequest<User> {
    @Serial
    private static final long serialVersionUID = -6860863275577558072L;

    @Schema(description = "主键")
    private String id;

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度在 6-20 位之间")
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
