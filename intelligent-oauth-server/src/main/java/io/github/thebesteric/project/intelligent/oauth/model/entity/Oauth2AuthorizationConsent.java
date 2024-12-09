package io.github.thebesteric.project.intelligent.oauth.model.entity;

/**
 * 授权确认
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-30 00:35:35
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import lombok.Data;

@Data
@TableName("oauth2_authorization_consent")
@EntityClass(comment = "授权确认")
public class Oauth2AuthorizationConsent {

    @TableId(type = IdType.ASSIGN_UUID)
    @EntityColumn(name = "registered_client_id", primary = true, length = 100)
    private String registeredClientId;

    @EntityColumn(name = "principal_name", nullable = false, length = 200)
    private String  principalName;

    @EntityColumn(name = "authorities", nullable = false, length = 1000)
    private String authorities;
}
