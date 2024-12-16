package io.github.thebesteric.project.intelligent.module.crm.model.domain.request;

import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * CustomerCreateRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 16:27:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerCreateRequest extends BaseRequest<Customer> {
    @Serial
    private static final long serialVersionUID = 7505881461702849105L;

    @Schema(description = "登录账号")
    @NotNull(message = "登录账号不能为空")
    private String username;

    @Schema(description = "登录密码")
    @NotNull(message = "登录密码不能为空")
    private String password;

    @Schema(description = "客户名称")
    @NotNull(message = "客户名称不能为空")
    private String name;

    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "客户编号")
    private String serialNo;

    @Schema(description = "联系人")
    private String contact;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "客户等级 ID")
    private Long customerLevelId;

    @Schema(description = "客户类型 ID")
    private Long customerTypeId;

    @Schema(description = "客勤预警 ID")
    private Long customerRelationId;

    @Schema(description = "区域 ID")
    private Long regionId;

    @Schema(description = "省市区 ID")
    private Long areaId;

    @Schema(description = "是否开票")
    private Boolean isInvoice = false;

    @Schema(description = "是否免运费")
    private Boolean isFreeFreight = false;

    @Schema(description = "是否启用积分")
    private Boolean isEnableIntegral = false;

    @Schema(description = "是否启用子账户")
    private Boolean isEnableSubAccount = false;
}
