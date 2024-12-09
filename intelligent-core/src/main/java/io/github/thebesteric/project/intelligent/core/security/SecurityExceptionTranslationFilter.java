package io.github.thebesteric.project.intelligent.core.security;

import io.github.thebesteric.framework.agile.commons.util.JsonUtils;
import io.github.thebesteric.framework.agile.core.domain.R;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * SecurityExceptionTranslationFilter
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-25 22:27:28
 */
@Slf4j
public class SecurityExceptionTranslationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            if (ex instanceof AuthenticationException || ex instanceof AccessDeniedException) {
                throw ex;
            }
            // 非 AuthenticationException、AccessDeniedException 异常，则直接响应
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(JsonUtils.toJson(R.error(HttpStatus.UNAUTHORIZED, ex.getMessage())));

            // 控制台打印异常信息
            log.error("认证失败: {}", ex.getMessage(), ex);
        }
    }
}
