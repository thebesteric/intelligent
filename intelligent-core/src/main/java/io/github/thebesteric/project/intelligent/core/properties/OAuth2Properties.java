package io.github.thebesteric.project.intelligent.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * OAuth2Properties
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-04-30 11:32:59
 */
@Data
@ConfigurationProperties(prefix = "oauth2")
public class OAuth2Properties {

    /** 认证验证地址 */
    private String issuerUri;
    /** 跨域配置 */
    private Cors cors = new Cors();

    @Data
    public static class Cors {
        private List<String> allowedOrigins = new ArrayList<>();
        private List<String> allowedMethods = List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS");
        private List<String> allowedHeaders = List.of("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization", "If-Modified-Since");
        private boolean allowCredentials = true;

        public boolean isAllowCredentials() {
            return !allowedOrigins.contains("*");
        }

    }

}
