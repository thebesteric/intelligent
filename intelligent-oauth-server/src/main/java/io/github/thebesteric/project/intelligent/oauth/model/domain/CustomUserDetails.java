package io.github.thebesteric.project.intelligent.oauth.model.domain;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import io.github.thebesteric.project.intelligent.core.constant.security.SecurityConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * CustomUserDetails
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-27 20:00:41
 */
@Data
@Builder
public class CustomUserDetails implements UserDetails {
    @Serial
    private static final long serialVersionUID = -3082302649370552978L;

    @Schema(description = "唯一键")
    private String id;

    @Schema(description = "租户 ID")
    private String tenantId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "账号是否可用")
    @Builder.Default
    private boolean enabled = true;

    @Schema(description = "账号是否过期")
    private boolean accountExpired;

    @Schema(description = "账号是否被锁定")
    private boolean accountLocked;

    @Schema(description = "认证是否过期")
    private boolean credentialsExpired;

    @Schema(description = "认证源")
    private String authSource;

    @Schema(description = "认证类型")
    private String authType;

    @Schema(description = "扩展信息")
    private transient Object extra;

    @Schema(description = "权限集合: GrantedAuthority")
    @Builder.Default
    private List<GrantedAuthority> authorities = new ArrayList<>();

    @Schema(description = "权限集合")
    @Builder.Default
    private List<String> privileges = new ArrayList<>();

    @Schema(description = "角色集合")
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return privileges == null ? List.of() : privileges.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Schema(description = "是否可用")
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Schema(description = "账号是否未过期")
    @Override
    public boolean isAccountNonExpired() {
        return !this.accountExpired;
    }

    @Schema(description = "账号是否未锁定")
    @Override
    public boolean isAccountNonLocked() {
        return !this.accountLocked;
    }

    @Schema(description = "认证是否未过期")
    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialsExpired;
    }

    public static CustomUserDetails fromJwt(JWT jwt) throws ParseException {
        JWTClaimsSet claims = jwt.getJWTClaimsSet();
        return CustomUserDetails.builder()
                .id(claims.getStringClaim(SecurityConstants.CLAIM_IDENTITY))
                .username(claims.getStringClaim(SecurityConstants.CLAIM_USERNAME))
                .tenantId(claims.getStringClaim(SecurityConstants.CLAIM_TENANT_ID))
                .roles(claims.getStringListClaim(SecurityConstants.CLAIM_ROLES))
                .privileges(claims.getStringListClaim(SecurityConstants.CLAIM_AUTHORITIES))
                .authSource(claims.getStringClaim(SecurityConstants.CLAIM_AUTH_SOURCE))
                .authType(claims.getStringClaim(SecurityConstants.CLAIM_AUTH_TYPE))
                .extra(claims.getStringClaim(SecurityConstants.CLAIM_EXTRA))
                .build();
    }
}
