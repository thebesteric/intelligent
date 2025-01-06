package io.github.thebesteric.project.intelligent.core.model.entity.core;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseTenantBizEntity;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.constant.crm.IdentityType;
import io.github.thebesteric.project.intelligent.core.constant.core.UserType;
import io.github.thebesteric.project.intelligent.core.util.BCryptUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.Date;
import java.util.List;

/**
 * User
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-25 13:37:35
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(ApplicationConstants.Application.Module.OpenApi.TABLE_NAME_PREFIX + "user")
@EntityClass(comment = "会员表", schemas = ApplicationConstants.Application.Module.OpenApi.DATASOURCE_INTELLIGENT_MODULE_OPEN_API)
public class User extends BaseTenantBizEntity {
    @Serial
    private static final long serialVersionUID = -5909195754964133917L;

    @EntityColumn(length = 64, nullable = false, comment = "用户名")
    private String username;

    @EntityColumn(length = 128, nullable = false, comment = "密码")
    private String password;

    @EntityColumn(length = 32, comment = "姓名")
    private String name;

    @EntityColumn(length = 32, comment = "邮箱")
    private String email;

    @EntityColumn(length = 32, comment = "电话号码")
    private String phone;

    @EntityColumn(length = 255, comment = "地址")
    private String address;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, nullable = false, comment = "用户类型")
    private UserType userType;

    @EntityColumn(type = EntityColumn.Type.VARCHAR, length = 32, nullable = false, comment = "身份类型")
    private IdentityType identityType;

    @EntityColumn(type = EntityColumn.Type.DATETIME, comment = "失效日期")
    private Date expiresAt;

    public static User of(String tenantId, String username, String password, String name, String email, String phone, String address,
                          UserType userType, IdentityType identityType, Date expiresAt) {
        User user = new User();
        user.tenantId = tenantId;
        user.username = username;
        user.password = BCryptUtils.encode(password);
        user.name = name;
        user.email = email;
        user.phone = phone;
        user.address = address;
        user.userType = userType;
        user.identityType = identityType;
        user.expiresAt = expiresAt;
        return user;
    }

    public static User of(String tenantId, String username, String password, String name, String email, String phone, String address,
                          UserType userType, IdentityType identityType, Date expiresAt, List<User> existsUsers) {
        if (existsUsers.stream().anyMatch(i -> i.username.equals(username) && i.userType == userType)) {
            return null;
        }
        return User.of(tenantId, username, password, name, email, phone, address, userType, identityType, expiresAt);
    }

    public static User createSuperAdmin(List<User> existsSuperAdmins) {
        return User.of(ApplicationConstants.SYSTEM_TENANT_ID, "admin", "123456",
                UserType.SUPER_ADMIN.getDesc(), "whatisjava@hotmail.com", "13900000000", null,
                UserType.SUPER_ADMIN, IdentityType.MANAGER, null, existsSuperAdmins);
    }

}
