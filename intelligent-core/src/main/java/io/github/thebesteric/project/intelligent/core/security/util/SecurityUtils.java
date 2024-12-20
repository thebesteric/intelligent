package io.github.thebesteric.project.intelligent.core.security.util;

import io.github.thebesteric.framework.agile.commons.util.AbstractUtils;
import io.github.thebesteric.project.intelligent.core.constant.security.SecurityConstants;
import io.github.thebesteric.project.intelligent.core.exception.BizException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Date;
import java.util.List;

/**
 * SecurityUtils
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-16 20:25:38
 */
public class SecurityUtils extends AbstractUtils {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static void clearContext() {
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(emptyContext);
    }

    public static Long getIdentity() {
        Jwt jwt = getJwt();
        return jwt.getClaim(SecurityConstants.CLAIM_IDENTITY);
    }

    public static String getUsername() {
        Jwt jwt = getJwt();
        return jwt.getClaim(SecurityConstants.CLAIM_USERNAME);
    }

    public static String getTenantId() {
        Jwt jwt = getJwt();
        return jwt.getClaim(SecurityConstants.CLAIM_TENANT_ID);
    }

    public static String getTenantIdWithException() {
        String tenantId = getTenantId();
        if (tenantId == null) {
            throw new BizException(BizException.BizCode.DATA_NOT_FOUND, "tenantId can not be null");
        }
        return tenantId;
    }

    public static List<String> getAuthorities() {
        Jwt jwt = getJwt();
        return jwt.getClaim(SecurityConstants.CLAIM_AUTHORITIES);
    }

    public static List<String> getRoles() {
        Jwt jwt = getJwt();
        return jwt.getClaim(SecurityConstants.CLAIM_ROLES);
    }

    public static String getSubject() {
        Jwt jwt = getJwt();
        return jwt.getClaim(SecurityConstants.CLAIM_SUBJECT);
    }

    public static String getAudience() {
        Jwt jwt = getJwt();
        return jwt.getClaim(SecurityConstants.CLAIM_AUDIENCE);
    }

    public static String getNotBefore() {
        Jwt jwt = getJwt();
        return jwt.getClaim(SecurityConstants.CLAIM_NOT_BEFORE);
    }

    public static List<String> getScope() {
        Jwt jwt = getJwt();
        return jwt.getClaim(SecurityConstants.CLAIM_SCOPE);
    }

    public static String getIssuer() {
        Jwt jwt = getJwt();
        return jwt.getClaim(SecurityConstants.CLAIM_ISSUER);
    }

    public static Date getIssuedAt() {
        Jwt jwt = getJwt();
        return Date.from(jwt.getClaim(SecurityConstants.CLAIM_ISSUED_AT));
    }

    public static Date getExpire() {
        Jwt jwt = getJwt();
        return Date.from(jwt.getClaim(SecurityConstants.CLAIM_EXPIRE));
    }

    private static Jwt getJwt() {
        Authentication authentication = getAuthentication();
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        return (Jwt) jwtAuthenticationToken.getPrincipal();
    }

}
