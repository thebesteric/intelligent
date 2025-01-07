package io.github.thebesteric.project.intelligent.core.model.entity.crm;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.github.thebesteric.framework.agile.commons.util.IpUtils;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseTenantBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.constant.AuditStatus;
import io.github.thebesteric.project.intelligent.core.constant.crm.AccountType;
import io.github.thebesteric.project.intelligent.core.constant.crm.RegisterSource;
import io.github.thebesteric.project.intelligent.core.mapper.handler.CommaStringToListTypeHandler;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.CustomerAuditRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import java.beans.Transient;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 客户
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-10 16:35:19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = ApplicationConstants.Application.Module.CRM.TABLE_NAME_PREFIX + "customer", autoResultMap = true)
@EntityClass(comment = "客户表", schemas = ApplicationConstants.Application.Module.CRM.DATASOURCE_INTELLIGENT_MODULE_CRM)
public class Customer extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = 1712056308739028043L;

    /** 总共允许登录失败的的次数 */
    public static final int TOTAL_ALLOW_LOGIN_FAILED_COUNT = 5;

    @EntityColumn(length = 32, nullable = false, comment = "登录账号")
    private String username;

    @EntityColumn(length = 128, nullable = false, comment = "登录密码")
    private String password;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, nullable = false, comment = "账号类型")
    private AccountType accountType = AccountType.MASTER;

    @EntityColumn(comment = "所属客户 ID")
    private Long ownerId;

    @EntityColumn(length = 64, nullable = false, comment = "客户名称")
    private String name;

    @EntityColumn(length = 64, nullable = false, comment = "关键字")
    private String keyword;

    @EntityColumn(length = 32, nullable = false, comment = "客户编号")
    private String serialNo;

    @EntityColumn(length = 64, comment = "联系人")
    private String contact;

    @EntityColumn(length = 64, comment = "联系电话")
    private String phone;

    @EntityColumn(length = 255, comment = "地址")
    private String address;

    @EntityColumn(length = 10, precision = 8, comment = "经度")
    private BigDecimal longitude;

    @EntityColumn(length = 11, precision = 8, comment = "纬度")
    private BigDecimal latitude;

    @EntityColumn(comment = "客户等级 ID")
    private Long customerLevelId;

    @EntityColumn(comment = "客户类型 ID")
    private Long customerTypeId;

    @EntityColumn(comment = "客勤预警 ID")
    private Long customerRelationId;

    @EntityColumn(comment = "区域 ID")
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long regionId;

    @EntityColumn(comment = "省市区 ID")
    private Long areaId;

    @TableField(value = "is_invoice")
    @EntityColumn(comment = "是否开票", nullable = false, defaultExpression = "false")
    private boolean isInvoice = false;

    @TableField(value = "is_free_freight")
    @EntityColumn(comment = "是否免运费", nullable = false, defaultExpression = "false")
    private boolean freeFreight = false;

    @TableField(value = "is_enable_integral")
    @EntityColumn(comment = "是否启用积分", nullable = false, defaultExpression = "false")
    private boolean enableIntegral = false;

    @TableField(value = "is_lock")
    @EntityColumn(comment = "是否启用子账户", nullable = false, defaultExpression = "false")
    private boolean enableSubAccount = false;

    @TableField(value = "is_requires_sub_account_order_approval")
    @EntityColumn(comment = "子账号提交订单是否需要审核", nullable = false, defaultExpression = "false")
    private boolean requiresSubAccountOrderApproval = false;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "店面图片")
    @TableField(jdbcType = JdbcType.VARCHAR, typeHandler = CommaStringToListTypeHandler.class)
    private List<String> storeHeaderPics;

    @EntityColumn(type = EntityColumn.Type.JSON, comment = "公司信息")
    @TableField(jdbcType = JdbcType.JAVA_OBJECT, typeHandler = JacksonTypeHandler.class)
    private Company company;

    @EntityColumn(type = EntityColumn.Type.JSON, comment = "法人信息")
    @TableField(jdbcType = JdbcType.JAVA_OBJECT, typeHandler = JacksonTypeHandler.class)
    private Corporate corporate;

    @EntityColumn(type = EntityColumn.Type.TINY_INT, nullable = false, comment = "审核状态")
    private AuditStatus auditStatus = AuditStatus.WAIT_AUDIT;

    @EntityColumn(length = 1024, comment = "审核说明")
    private String auditComment;

    @EntityColumn(type = EntityColumn.Type.TEXT, comment = "关联业务员 IDs")
    @TableField(jdbcType = JdbcType.VARCHAR, typeHandler = CommaStringToListTypeHandler.class)
    private List<String> salesUserIds;

    @EntityColumn(comment = "推荐人 ID")
    private Long referrerUserId;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, comment = "注册来源")
    private RegisterSource registerSource = RegisterSource.INNER;

    @EntityColumn(length = 64, comment = "登录 IP")
    private String loginIp;

    @EntityColumn(comment = "登录次数", nullable = false, defaultExpression = "0")
    private Integer loginTimes;

    @EntityColumn(comment = "登录连续错误次数", nullable = false, defaultExpression = "0")
    private Integer loginContinueErrorTimes;

    @EntityColumn(type = EntityColumn.Type.DATETIME, comment = "最后一次登录日期")
    private Date lastLoginTime;

    @TableField(value = "is_lock")
    @EntityColumn(comment = "账户是否锁定", nullable = false, defaultExpression = "false")
    private boolean lock = false;

    /**
     * 设置客户审核信息
     *
     * @param auditRequest 请求
     *
     * @author wangweijun
     * @since 2025/1/6 13:54
     */
    @Transient
    public void setCustomerAuditInfo(CustomerAuditRequest auditRequest) {
        this.auditStatus = auditRequest.getAuditStatus();
        this.auditComment = auditRequest.getAuditComment();
        this.salesUserIds = auditRequest.getSalesUserIds();
        this.referrerUserId = auditRequest.getReferrerUserId();
    }

    /**
     * 设置成功登录信息
     *
     * @param request Http 请求
     *
     * @author wangweijun
     * @since 2025/1/6 13:55
     */
    @Transient
    public void setSuccessLoginInfo(HttpServletRequest request) {
        this.loginTimes = this.loginTimes == null ? 1 : ++this.loginTimes;
        this.setLoginInfo(IpUtils.getClientIP(request), this.loginTimes, 0);
    }

    /**
     * 设置失败登录信息
     *
     * @param request Http 请求
     *
     * @author wangweijun
     * @since 2025/1/6 18:36
     */
    @Transient
    public void setErrorLoginInfo(HttpServletRequest request) {
        this.loginTimes = this.loginTimes == null ? 1 : ++this.loginTimes;
        this.loginContinueErrorTimes = this.loginContinueErrorTimes == null ? 1 : ++this.loginContinueErrorTimes;
        this.setLoginInfo(IpUtils.getClientIP(request), this.loginTimes, this.loginContinueErrorTimes);
    }

    /**
     * 设置登录信息
     *
     * @param loginIp                 登录 IP
     * @param loginTimes              登录次数
     * @param loginContinueErrorTimes 登录连续错误次数
     *
     * @author wangweijun
     * @since 2025/1/6 18:37
     */
    @Transient
    private void setLoginInfo(String loginIp, Integer loginTimes, Integer loginContinueErrorTimes) {
        this.loginIp = loginIp;
        this.loginTimes = loginTimes;
        this.loginContinueErrorTimes = loginContinueErrorTimes;
        this.lastLoginTime = new Date();
    }

    /**
     * 获取登录失败后剩余登录次数
     *
     * @return int
     *
     * @author wangweijun
     * @since 2025/1/7 14:32
     */
    @Transient
    public int getLoginFailedRemainTimes() {
        int loginRemainTimes = TOTAL_ALLOW_LOGIN_FAILED_COUNT - this.loginContinueErrorTimes;
        return Math.max(loginRemainTimes, 0);
    }

    /**
     * 是否需要被锁定
     *
     * @return boolean
     *
     * @author wangweijun
     * @since 2025/1/7 14:36
     */
    @Transient
    public boolean shouldBeLock() {
        return this.loginContinueErrorTimes >= Customer.TOTAL_ALLOW_LOGIN_FAILED_COUNT;
    }

    /**
     * 公司信息
     *
     * @author wangweijun
     * @since 2024/12/12 12:41
     */
    @Data
    public static class Company implements Serializable {
        @Serial
        private static final long serialVersionUID = -1658979269953377706L;

        @Schema(description = "公司名称")
        private String name;

        @Schema(description = "营业执照号")
        private String licenceNumber;

        @Schema(description = "成立日期")
        private Date establishedDate;

        @Schema(description = "营业期限（年）")
        private Integer businessTerm;

        @Schema(description = "经营范围")
        private String businessScope;

        @Schema(description = "注册资金（万）")
        private String registeredCapital;

        @Schema(description = "组织机构代码")
        private String orgCode;

        @Schema(description = "税务登记号")
        private String taxCode;

        @Schema(description = "省市区 ID")
        private Long areaId;

        @Schema(description = "营业执照图片")
        private List<String> licencePics;

        @Schema(description = "其他图片")
        private List<String> otherPics;

    }

    /**
     * 法人信息
     *
     * @author wangweijun
     * @since 2024/12/12 12:41
     */
    @Data
    public static class Corporate implements Serializable {
        @Serial
        private static final long serialVersionUID = -5925402154100045074L;

        @Schema(description = "经营者姓名/法人")
        private String name;

        @Schema(description = "联系电话")
        private String phone;

        @Schema(description = "电子邮件")
        private String email;

        @Schema(description = "微信号")
        private String wechat;

        @Schema(description = "QQ号")
        private String qq;

        @Schema(description = "身份证号")
        private String idCard;

        @Schema(description = "身份证正面图片")
        private String idCardFrontPic;

        @Schema(description = "身份证反面图片")
        private String idCardBackPic;
    }

}
