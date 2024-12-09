package io.github.thebesteric.project.intelligent.oauth.config;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.project.intelligent.oauth.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 权限访问拒绝
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-08-12 11:15:28
 */
public class JsonAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String message = accessDeniedException.getLocalizedMessage();
        ServletUtils.renderString(response, R.error(HttpStatus.FORBIDDEN, message));
    }
}
