package io.github.thebesteric.project.intelligent.core.security;

import io.github.thebesteric.framework.agile.commons.util.JsonUtils;
import io.github.thebesteric.framework.agile.core.domain.R;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * SecurityAccessDeniedHandler
 */
@Slf4j
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String message = accessDeniedException.getMessage();
        if (request.getUserPrincipal() instanceof AbstractOAuth2TokenAuthenticationToken) {
            message = JsonUtils.toJson(R.error(HttpStatus.FORBIDDEN, "权限不足"));
        } else {
            message = JsonUtils.toJson(R.error(HttpStatus.FORBIDDEN, message));
        }

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(message);

        // 控制台打印异常信息
        log.error("权限异常: {}", message, accessDeniedException);
    }
}
