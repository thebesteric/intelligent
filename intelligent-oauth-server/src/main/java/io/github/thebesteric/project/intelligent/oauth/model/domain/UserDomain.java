package io.github.thebesteric.project.intelligent.oauth.model.domain;

import io.github.thebesteric.project.intelligent.core.model.entity.core.Privilege;
import io.github.thebesteric.project.intelligent.core.model.entity.core.Role;
import io.github.thebesteric.project.intelligent.core.model.entity.core.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.List;

/**
 * UserDomain
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-26 09:12:29
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDomain extends User implements UserDetails {
    @Serial
    private static final long serialVersionUID = 6793896182203928882L;

    @Schema(description = "权限集合")
    private List<GrantedAuthority> authorities;

    @Schema(description = "权限集合")
    private List<Privilege> privileges;

    @Schema(description = "角色集合")
    private List<Role> roles;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public static UserDomain of(User user) {
        UserDomain userDomain = new UserDomain();
        BeanUtils.copyProperties(user, userDomain);
        return userDomain;
    }
}
