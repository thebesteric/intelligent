package io.github.thebesteric.project.intelligent.core.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.github.thebesteric.project.intelligent.core.constant.security.SecurityConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * FeignConfig
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-21 11:42:11
 */
@Configuration
public class FeignConfig {

    /**
     * 自定义的拦截器
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignWithAuthRequestInterceptor();
    }

    /**
     * 携带认证请求拦截器
     *
     * @author wangweijun
     * @since 2024/11/26 10:05
     */
    @Slf4j
    public static class FeignWithAuthRequestInterceptor implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate requestTemplate) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null != attributes) {
                HttpServletRequest request = attributes.getRequest();
                // 是否需要携带认证信息
                withAuthorizationIfNecessary(request, requestTemplate);
            }
        }

        /**
         * 携带认证信息
         *
         * @param request         请求
         * @param requestTemplate Http 请求客户端
         *
         * @author wangweijun
         * @since 2024/7/16 16:37
         */
        private void withAuthorizationIfNecessary(HttpServletRequest request, RequestTemplate requestTemplate) {
            String skipAuthorization = request.getHeader(SecurityConstants.REQUEST_HEADER_SKIP_AUTHORIZATION);
            if (skipAuthorization == null) {
                skipAuthorization = request.getParameter(SecurityConstants.REQUEST_HEADER_SKIP_AUTHORIZATION);
            }
            if (skipAuthorization == null || skipAuthorization.equalsIgnoreCase("false")) {
                String accessToken = request.getHeader(SecurityConstants.REQUEST_HEADER_AUTHORIZATION);
                requestTemplate.header(SecurityConstants.REQUEST_HEADER_AUTHORIZATION, accessToken);
            }
        }
    }

}
