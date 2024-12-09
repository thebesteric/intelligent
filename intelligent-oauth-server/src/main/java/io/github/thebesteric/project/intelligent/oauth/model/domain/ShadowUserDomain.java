package io.github.thebesteric.project.intelligent.oauth.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import io.github.thebesteric.project.intelligent.oauth.model.entity.ShadowUser;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class ShadowUserDomain extends ShadowUser implements UserDetails {
    @Serial
    private static final long serialVersionUID = 6793896182203928882L;

    @TableField(exist = false)
    @EntityColumn(exist = false, comment = "权限集合")
    private List<GrantedAuthority> authorities;


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

    public static ShadowUserDomain of(ShadowUser user) {
        ShadowUserDomain shadowUserDomain = new ShadowUserDomain();
        BeanUtils.copyProperties(user, shadowUserDomain);
        return shadowUserDomain;
    }

}
