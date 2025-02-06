package io.github.thebesteric.project.intelligent.modules.common.model.domain.crm.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.thebesteric.project.intelligent.core.annotation.BigDecimalFormat;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Customer;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.crm.Prepaid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * PrepaidSearchResponse
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-23 13:35:59
 */
@Data
public class PrepaidSearchResponse {

    @Schema(description = "登录账号")
    private String username;

    @Schema(description = "客户名称")
    private String name;

    @Schema(description = "客户编号")
    private String serialNo;

    @Schema(description = "客户手机号")
    private String phone;

    @Schema(description = "可用余额")
    @BigDecimalFormat
    private BigDecimal balanceAmount = BigDecimal.ZERO;

    @Schema(description = "最近增加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date latestCreatedAt;

    public static PrepaidSearchResponse of(Customer customer, @Nullable Prepaid latestPrepaid) {
        PrepaidSearchResponse response = new PrepaidSearchResponse();
        response.username = customer.getUsername();
        response.name = customer.getName();
        response.serialNo = customer.getSerialNo();
        response.phone = customer.getPhone();
        response.balanceAmount = latestPrepaid == null ? BigDecimal.ZERO : latestPrepaid.getBalanceAmount();
        response.latestCreatedAt = latestPrepaid == null ? null : latestPrepaid.getCreatedAt();
        return response;
    }

}
