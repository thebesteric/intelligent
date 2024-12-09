package io.github.thebesteric.project.intelligent.oauth.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.core.base.BaseBizEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
@TableName("core_t_user")
@EntityClass(comment = "用户表", ignore = true)
public class ShadowUser extends BaseBizEntity {

    @EntityColumn(comment = "用户名")
    private String username;

    @EntityColumn(comment = "密码")
    private String password;

}
