package io.github.thebesteric.project.intelligent.core.properties;

import io.github.thebesteric.project.intelligent.core.constant.security.AuthSource;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthType;
import io.github.thebesteric.project.intelligent.core.constant.security.GrantType;
import io.github.thebesteric.project.intelligent.core.constant.security.Scope;
import io.github.thebesteric.project.intelligent.core.model.domain.security.request.OAuth2TokenRequest;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.time.Duration;
import java.util.*;

/**
 * ApplicationProperties
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-29 13:48:05
 */
@Data
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    /** 应用组件 */
    @NestedConfigurationProperty
    private Components components = new Components();

    /** 认证相关 */
    @NestedConfigurationProperty
    private OAuth2 oauth2 = new OAuth2();

    /** API 接口地址相关 */
    @NestedConfigurationProperty
    private OpenApis openApis = new OpenApis();

    /** 应用组件 */
    @Data
    public static class Components {
        /** XXL-Job 配置 */
        @NestedConfigurationProperty
        private XxlJobComponent xxlJob = new XxlJobComponent();
        /** Swagger 配置 */
        @NestedConfigurationProperty
        private SwaggerComponent swagger = new SwaggerComponent();
        /** Spring Caching 配置 */
        @NestedConfigurationProperty
        private Caching caching = new Caching();

        /** swagger 配置类 */
        @Data
        public static class XxlJobComponent {
            /** 是否启用 */
            private boolean enable = false;
            /** 令牌 */
            private String assessToken = "default_token";
            /** 管理端 */
            private Admin admin = new Admin();
            /** 执行器 */
            private Executor executor = new Executor();

            @Data
            public static class Admin {
                /** 管理地址 */
                private String address = "http://localhost:8215/xxl-job-admin";
            }

            @Data
            public static class Executor {
                /** 执行器名称，为空表示关闭自动注册 */
                private String appName;
                /** 执行器地址，优先使用该配置作为注册地址，为空时使用内嵌服务 IP:PORT 作为注册地址 */
                private String address;
                /** 执行器 IP，为空表示自动获取 IP，多网卡需要手动指定 IP */
                private String ip;
                /** 通讯端口，当存在多个执行器，需要配置不同的端口，小于等于 0 则自动获取；默认端口为 9999 */
                private Integer port = -1;
                /** 日志路径，需要有读写权限 */
                private String logPath = "./logs/xxl-job";
                /** 日志保留天数，大于等于 3 的时候生效，当等于 -1 表示关闭自动清理日志 */
                private Integer logRetentionDays = 30;

            }
        }

        /** xxl-job 配置类 */
        @Data
        public static class SwaggerComponent {
            /** 是否启用 */
            private boolean enable = false;
            /** 生产环境保护，不设置则以 spring.profiles.active 为准 */
            private Boolean production;
            /** 联系方式 */
            @NestedConfigurationProperty
            private Contact contact = new Contact();
            /** 展示信息 */
            @NestedConfigurationProperty
            private Info info = new Info();

            @Data
            public static class Contact {
                private String name;
                private String email = "whatisjava@hotmail.com";
                private String url = "https://doc.xiaominfo.com";
            }

            @Data
            public static class Info {
                private String title = "服务 API 接口文档";
                private String version = "1.0";
                private String description;
                private String termsOfService = "https://doc.xiaominfo.com";
                @NestedConfigurationProperty
                private License license;
            }

            @Data
            public static class License {
                private String name = "Apache 2.0";
                private String url = "https://www.apache.org/licenses/LICENSE-2.0.html";
            }
        }

        /** Spring Caching 配置 */
        @Data
        public static class Caching {
            public static final String DEFAULT_CACHE_NAME = "default";
            /** 是否启用 */
            private boolean enable = false;
            /** 缓存前缀 */
            private String cacheNamePrefix = "intelligent";
            /** 缓存后缀 */
            private String cacheNameSuffix = "cache";
            /** 缓存分隔符 */
            private String cacheNameSpectator = "::";
            /** 缓存类型 */
            private CacheType cacheType = CacheType.CAFFEINE;
            /** 默认 3600 秒后过期 */
            private Duration entryTtl;
            /** 缓存名称: REDIS 不需要设置此项 */
            private Set<String> cacheNames;
            /** 缓存最大容量: 仅对 CAFFEINE 生效 */
            private Integer maximumSize;

            public Duration getEntryTtl() {
                return entryTtl == null ? Duration.ofSeconds(3600L) : entryTtl;
            }

            public Integer getMaximumSize() {
                return maximumSize == null || maximumSize <= 0 ? 1024 : maximumSize;
            }

            public Set<String> getCacheNames() {
                if (cacheNames == null) {
                    cacheNames = new HashSet<>();
                }
                cacheNames.add(DEFAULT_CACHE_NAME);
                return cacheNames;
            }

            public enum CacheType {
                HASHMAP, CAFFEINE, REDIS
            }
        }
    }

    /** 认证相关 */
    @Data
    public static class OAuth2 {
        /** 认证地址 */
        private String issuerUri;
        /** 认证类型 */
        private GrantType grantType = GrantType.GRANT_TYPE_PASSWORD;
        /** 权限范围 */
        private List<Scope> scopes = List.of(Scope.PROFILE, Scope.OPENID);
        /** 认证客户端 */
        private ClientInfo clientInfo;
        /** 认证源 */
        private AuthSource authSource = AuthSource.MASTER;
        /** 认证类型 */
        private AuthType authType = AuthType.PASSWORD;

        public String getAccessTokenUri(String username, String password) {
            OAuth2TokenRequest oAuth2TokenRequest = OAuth2TokenRequest.of(username, password, grantType, authType, authSource, scopes);
            return getAccessTokenUri(oAuth2TokenRequest);
        }

        public String getAccessTokenUri(OAuth2TokenRequest tokenRequest) {
            Map<String, Object> params = new HashMap<>();
            params.put("username", tokenRequest.getUsername());
            params.put("password", tokenRequest.getPassword());
            params.put("grant_type", tokenRequest.getGrantType());
            params.put("scope", tokenRequest.getScope());
            params.put("auth_source", tokenRequest.getAuthSource());
            params.put("auth_type", tokenRequest.getAuthType());
            String queryString = toQueryString(params);
            return getTokenUri() + "?" + queryString;
        }

        public String getRefreshTokenUri() {
            return getTokenUri();
        }

        private String getTokenUri() {
            return issuerUri + "/oauth2/token";
        }

        private String toQueryString(Map<String, Object> params) {
            StringBuilder sb = new StringBuilder();
            params.forEach((key, value) -> {
                if (value != null) {
                    sb.append(key).append("=").append(value).append("&");
                }
            });
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }

        @Data
        public static class ClientInfo {
            private String clientId;
            private String clientSecret;
        }
    }

    /** API 接口地址相关 */
    @Data
    public static class OpenApis {
        /** Open API 访问地址 */
        private String openApi = "http://localhost:8000/module/open/api";
    }

}
