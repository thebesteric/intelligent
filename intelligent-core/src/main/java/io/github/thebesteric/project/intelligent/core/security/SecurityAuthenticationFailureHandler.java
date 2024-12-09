package io.github.thebesteric.project.intelligent.core.security;

import io.github.thebesteric.framework.agile.commons.util.JsonUtils;
import io.github.thebesteric.framework.agile.core.domain.R;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * SecurityAuthenticationFailureHandler
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-25 21:55:18
 */
@Slf4j
public class SecurityAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(JsonUtils.toJson(R.error(HttpStatus.UNAUTHORIZED, exception.getMessage())));

        // 控制台打印异常信息
        log.error("认证失败: {}", exception.getMessage(), exception);
    }
}
