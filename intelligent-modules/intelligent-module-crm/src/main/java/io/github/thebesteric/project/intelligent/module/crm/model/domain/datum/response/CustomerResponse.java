package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import io.github.thebesteric.project.intelligent.core.base.BaseResponse;
import io.github.thebesteric.project.intelligent.module.crm.constant.RegisterSource;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.Customer;
import io.github.thebesteric.project.intelligent.modules.common.constant.AuditStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.List;

/**
 * 客户
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 20:57:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerResponse extends BaseResponse<Customer> {
    @Serial
    private static final long serialVersionUID = -4575915736983191354L;

    @Schema(description = "登录账号")
    private String username;

    @Schema(description = "登录密码")
    private String password;

    @Schema(description = "客户名称")
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

    @Schema(description = "店面图片")
    private List<String> storeHeaderPics;

    @Schema(description = "公司信息")
    private Customer.Company company;

    @Schema(description = "法人信息")
    private Customer.Corporate corporate;

    @Schema(description = "审核状态")
    private AuditStatus auditStatus = AuditStatus.WAIT_AUDIT;

    @Schema(description = "审核说明")
    private String auditComment;

    @Schema(description = "关联业务员 IDs")
    private List<String> clerkUserIds;

    @Schema(description = "推荐人 ID")
    private Long referrerUserId;

    @Schema(description = "注册来源")
    private RegisterSource registerSource = RegisterSource.INNER;

    public void setPassword(String password) {
        this.password = "******";
    }
}
