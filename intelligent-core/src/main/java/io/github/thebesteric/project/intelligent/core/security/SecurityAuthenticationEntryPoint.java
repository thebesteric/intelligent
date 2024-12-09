package io.github.thebesteric.project.intelligent.core.security;

import io.github.thebesteric.framework.agile.commons.util.JsonUtils;
import io.github.thebesteric.framework.agile.core.domain.R;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.io.IOException;

/**
 * 自定义 AuthenticationEntryPoint
 */
@Slf4j
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String message = authException.getMessage();
        // 访问了受保护的资源，但是没有携带令牌
        if (authException instanceof InsufficientAuthenticationException) {
            String accept = request.getHeader("accept");
            if (accept != null && accept.contains(MediaType.TEXT_HTML_VALUE)) {
                // 如果是 html 请求类型，则返回登录页
                LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint("/login");
                loginUrlAuthenticationEntryPoint.commence(request, response, authException);
            } else {
                // 如果是 api 请求类型，则返回 json
                message = JsonUtils.toJson(R.error(HttpStatus.UNAUTHORIZED, "需要携带令牌进行访问"));
            }
        }
        // 令牌无效或已过期
        else if (authException instanceof InvalidBearerTokenException) {
            message = JsonUtils.toJson(R.error(HttpStatus.UNAUTHORIZED, "令牌无效或已过期"));
        }
        // 其他情况
        else {
            // 其他情况下，直接返回异常
            message = JsonUtils.toJson(R.error(HttpStatus.UNAUTHORIZED, message));
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(message);

        // 控制台打印异常信息
        log.error("认证失败,message:{},path:{}", message, request.getRequestURI(), authException);
    }
}
