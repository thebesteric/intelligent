package io.github.thebesteric.project.intelligent.oauth.util;

import io.github.thebesteric.framework.agile.commons.util.AbstractUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * SecurityUtils
 *
 * @author wangweijun
 * @version v1.0
 * @since 2023-12-06 23:09:09
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

    public static UserDetails getCurrentUser() {
        Object principal = getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails;
        }
        return null;
    }

}
