package io.github.thebesteric.project.intelligent.oauth.config;

import io.github.thebesteric.framework.agile.core.domain.R;
import io.github.thebesteric.project.intelligent.oauth.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 请求未认证处理
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-08-09 17:42:08
 */
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String message = authException.getLocalizedMessage();
        ServletUtils.renderString(response, R.error(HttpStatus.UNAUTHORIZED, message));
    }
}
