package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.thebesteric.project.intelligent.core.model.entity.core.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户区域-关联用户
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-30 18:03:57
 */
@Data
public class CustomerRegionUserResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 5495866274398436993L;

    @Schema(description = "客户信息")
    private CustomerSimpleResponse customer;

    @Schema(description = "区域信息")
    private CustomerSimpleRegion region;

    @Data
    public static class CustomerSimpleResponse implements Serializable {
        @Serial
        private static final long serialVersionUID = 7267806573884101890L;

        @Schema(description = "ID")
        private Long id;

        @Schema(description = "客户名称")
        private String name;

        @Schema(description = "客户编号")
        private String serialNo;

        @Schema(description = "客户地址")
        private String address;

        @Schema(description = "客户等级")
        private CustomerLevelInfo customerLevel;

        @Schema(description = "客户类型")
        private CustomerTypeInfo customerType;

        @JsonIgnore
        @Schema(description = "关联业务员 IDs")
        private List<String> salesUserIds = new ArrayList<>();

        @Schema(description = "关联业务员")
        private List<SalesUserInfo> salesUsers = new ArrayList<>();
    }

    @Data
    public static class CustomerSimpleRegion implements Serializable {
        @Serial
        private static final long serialVersionUID = 1470861840325715971L;

        @Schema(description = "ID")
        private Long id;

        @Schema(description = "区域名称")
        private String name;

        @Schema(description = "区域编码")
        private String code;
    }

    @Data
    public static class CustomerLevelInfo implements Serializable {
        @Serial
        private static final long serialVersionUID = 3945832829243488221L;

        @Schema(description = "ID")
        private Long id;

        @Schema(description = "等级名称")
        private String name;
    }

    @Data
    public static class CustomerTypeInfo implements Serializable {
        @Serial
        private static final long serialVersionUID = 5527559908866155768L;

        @Schema(description = "ID")
        private Long id;

        @Schema(description = "类型名称")
        private String name;
    }

    @Data
    public static class SalesUserInfo implements Serializable {
        @Serial
        private static final long serialVersionUID = -2981777155286531457L;

        @Schema(description = "ID")
        private Long id;

        @Schema(description = "业务员名称")
        private String name;

        public static SalesUserInfo of(User salesUser) {
            SalesUserInfo info = new SalesUserInfo();
            info.id = salesUser.getId();
            info.name = salesUser.getName();
            return info;
        }
    }
}
