package io.github.thebesteric.project.intelligent.oauth.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import io.github.thebesteric.project.intelligent.core.constant.security.GrantType;
import io.github.thebesteric.project.intelligent.core.constant.security.SecurityConstants;
import io.github.thebesteric.project.intelligent.core.properties.OAuth2Properties;
import io.github.thebesteric.project.intelligent.core.security.*;
import io.github.thebesteric.project.intelligent.oauth.config.convert.PasswordGrantAuthenticationConverter;
import io.github.thebesteric.project.intelligent.oauth.config.convert.PasswordGrantAuthenticationProvider;
import io.github.thebesteric.project.intelligent.oauth.detail.UserDetailsFactory;
import io.github.thebesteric.project.intelligent.oauth.model.domain.CustomUserDetails;
import io.github.thebesteric.project.intelligent.oauth.service.UserDetailsManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.util.StandardSessionIdGenerator;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.time.Instant;
import java.util.*;

/**
 * SpringSecurityConfig
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-25 12:55:13
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * Spring Security 的过滤器链，用于协议端点的
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity httpSecurity,
                                                                      UserDetailsService userDetailsService,
                                                                      PasswordEncoder passwordEncoder,
                                                                      OAuth2AuthorizationService authorizationService,
                                                                      OAuth2TokenGenerator<?> tokenGenerator) throws Exception {

        // FOR Spring Boot 3.4.x
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();
        httpSecurity.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (authorizationServer) -> {
                            authorizationServer.clientAuthentication(clientAuthentication -> {
                                // 客户端异常处理
                                clientAuthentication.errorResponseHandler(new SecurityAuthenticationFailureHandler());
                            });
                            // 配置 Token 生成策略
                            authorizationServer.tokenEndpoint(tokenEndpoint -> {
                                        tokenEndpoint.accessTokenRequestConverter(new PasswordGrantAuthenticationConverter());
                                        tokenEndpoint.authenticationProvider(new PasswordGrantAuthenticationProvider(userDetailsService, passwordEncoder, authorizationService, tokenGenerator));
                                        tokenEndpoint.errorResponseHandler(new SecurityAuthenticationFailureHandler());
                                    })
                                    // 开启 OpenID Connect 1.0
                                    .oidc(Customizer.withDefaults());
                        }
                )
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        // For Spring Boot 3.3.x
        // 将默认的 OAuth2 security configuration 应用到 HttpSecurity
        // OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
        //
        // httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
        //         .clientAuthentication(clientAuthentication ->
        //                 // 客户端异常处理
        //                 clientAuthentication.errorResponseHandler(new SecurityAuthenticationFailureHandler())
        //         )
        //         // 配置密码模式
        //         .tokenEndpoint(tokenEndpoint -> tokenEndpoint
        //                 .accessTokenRequestConverters(authenticationConverters -> authenticationConverters.add(new PasswordGrantAuthenticationConverter()))
        //                 .authenticationProviders(authenticationProviders -> authenticationProviders.add(new PasswordGrantAuthenticationProvider(userDetailsService, passwordEncoder, authorizationService, tokenGenerator)))
        //                 .errorResponseHandler(new SecurityAuthenticationFailureHandler()))
        //         // 开启 OpenID Connect 1.0，oidc 就是 OpenID Connect 的缩写
        //         .oidc(Customizer.withDefaults());

        httpSecurity
                // 在 ExceptionTranslationFilter 过滤器之前加入 SecurityExceptionTranslationFilter 过滤器
                .addFilterBefore(new SecurityExceptionTranslationFilter(), ExceptionTranslationFilter.class)
                // 当未从授权端点进行身份验证时，重定向到登录页面
                .exceptionHandling(exceptions -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                        // 认证异常处理
                        .authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
                        // 没有权限的异常处理
                        .accessDeniedHandler(new SecurityAccessDeniedHandler())
                )
                // 使用 jwt 处理接收到的 token
                .oauth2ResourceServer(resourceServer -> resourceServer
                        .jwt(Customizer.withDefaults())
                        // 认证异常处理
                        .authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
                        // 没有权限的异常处理
                        .accessDeniedHandler(new SecurityAccessDeniedHandler())
                );

        httpSecurity.sessionManagement(session -> session
                // 设置最多有多 1 个 Session，也就是后登录的账号会让之前登录的账号过期
                .maximumSessions(1)
                // 设置 Session 过期后处理
                .expiredSessionStrategy(new JsonSessionInformationExpiredStrategy())
        );

        httpSecurity
                // 解决请求跨域问题
                .cors(AbstractHttpConfigurer::disable)
                // 关闭跨站请求伪造
                .csrf(AbstractHttpConfigurer::disable)
                // 退出时 session 失效
                .logout(logout -> logout.invalidateHttpSession(true));

        return httpSecurity.build();


    }

    /**
     * Spring Security 的过滤器链，用于 Spring Security 的身份验证
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 放行 OPTIONS, 解决 Token 请求跨域问题
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.OPTIONS).permitAll())
                // 对所有请求都拦截
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                // 由 Spring Security 过滤链中的 UsernamePasswordAuthenticationFilter 过滤器拦截处理
                .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    /**
     * 跨域配置
     * 获取在 Controller 上使用 @CrossOrigin 注解
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(OAuth2Properties oAuth2Properties) {
        CorsConfiguration configuration = new CorsConfiguration();
        OAuth2Properties.Cors cors = oAuth2Properties.getCors();
        configuration.setAllowedOrigins(cors.getAllowedOrigins());
        configuration.setAllowedMethods(cors.getAllowedMethods());
        configuration.setAllowedHeaders(cors.getAllowedHeaders());
        configuration.setAllowCredentials(cors.isAllowCredentials());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * 基于数据库的用户信息
     */
    @Bean
    public UserDetailsService userDetailsService(UserDetailsFactory userDetailsFactory) {
        return new UserDetailsManager(userDetailsFactory);
    }

    /**
     * 客户端信息
     * 对应表：oauth2-registered-client-schema.sql
     * spring-security-oauth2-authorization-server-1.4.1.jar!/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    /**
     * 授权信息
     * 对应表：oauth2_authorization
     * spring-security-oauth2-authorization-server-1.4.1.jar!/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql
     */
    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    public abstract static class SynchronizedSetMixin {
    }

    /**
     * 授权确认
     * 对应表：oauth2_authorization_consent
     * spring-security-oauth2-authorization-server-1.4.1.jar!/org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql
     */
    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    /**
     * 密码解析器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置 JWK，为 JWT(id_token) 提供加密密钥，用于加密/解密或签名/验签
     * JWK 详见：https://datatracker.ietf.org/doc/html/draft-ietf-jose-json-web-key-41
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource(StringRedisTemplate redisTemplate) throws ParseException {
        String jwkSetCache = redisTemplate.opsForValue().get(SecurityConstants.AUTHORIZATION_JWK_SET_KEY);
        if (ObjectUtils.isEmpty(jwkSetCache)) {
            KeyPair keyPair = generateRsaKey();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAKey rsaKey = new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyID(UUID.randomUUID().toString())
                    .build();
            JWKSet jwkSet = new JWKSet(rsaKey);
            String jwkSetString = jwkSet.toString(Boolean.FALSE);
            redisTemplate.opsForValue().set(SecurityConstants.AUTHORIZATION_JWK_SET_KEY, jwkSetString);
            return new ImmutableJWKSet<>(jwkSet);
        }
        JWKSet jwkSet = JWKSet.parse(jwkSetCache);
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * 生成 RSA 密钥对，给上面 jwkSource() 方法的提供密钥对
     */
    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    /**
     * 用于解码签名访问令牌
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * 用于配置 Spring Authorization Server
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings(JdbcTemplate jdbcTemplate) {
        // 什么都不配置，则使用默认地址，即请求的 token 的地址就是 iss 的地址
        return AuthorizationServerSettings.builder().build();

    }

    /**
     * 配置 token 生成器
     */
    @Bean
    public OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator(JWKSource<SecurityContext> jwkSource, OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer) {
        JwtGenerator jwtGenerator = new JwtGenerator(new NimbusJwtEncoder(jwkSource));
        // 自定义 JWT 输出格式
        jwtGenerator.setJwtCustomizer(jwtCustomizer);
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(OAuth2AuthorizationService authorizationService) {
        return context -> {
            OAuth2TokenType tokenType = context.getTokenType();
            // Authentication authentication = context.getPrincipal();
            // User userDetails = (User) authentication.getPrincipal();
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getUserDetails();


            // Get userDetails from refresh_token
            if (userDetails == null) {
                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                assert attr != null;
                HttpServletRequest request = attr.getRequest();
                String refreshToken = request.getParameter(GrantType.GRANT_TYPE_REFRESH_TOKEN.getType());
                OAuth2Authorization token = authorizationService.findByToken(refreshToken, OAuth2TokenType.REFRESH_TOKEN);
                if (token == null) {
                    throw new SecurityAuthenticationException("Get refreshToken error");
                }
                try {
                    JWT jwt = JWTParser.parse(token.getAccessToken().getToken().getTokenValue());
                    userDetails = CustomUserDetails.fromJwt(jwt);
                } catch (Exception e) {
                    throw new SecurityAuthenticationException(e);
                }
            }

            if (tokenType.equals(OAuth2TokenType.ACCESS_TOKEN) || tokenType.getValue().equals(OidcParameterNames.ID_TOKEN)) {
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                List<String> roleCodes = userDetails.getRoles();

                // Customize headers/claims for access_token
                JwsHeader.Builder headers = context.getJwsHeader();
                headers.header("created", "intelligent");

                JwtClaimsSet.Builder claims = context.getClaims();
                // Customize headers/claims for id_token
                claims.claim(IdTokenClaimNames.AUTH_TIME, Date.from(Instant.now()));
                claims.claim(SecurityConstants.CLAIM_IDENTITY, userDetails.getId());
                claims.claim(SecurityConstants.CLAIM_USERNAME, userDetails.getUsername());
                claims.claim(SecurityConstants.CLAIM_TENANT_ID, userDetails.getTenantId());
                claims.claim(SecurityConstants.CLAIM_SID, new StandardSessionIdGenerator().generateSessionId());
                claims.claim(SecurityConstants.CLAIM_ROLES, clone(roleCodes));
                claims.claim(SecurityConstants.CLAIM_AUTHORITIES, clone(authorities.stream().map(GrantedAuthority::getAuthority).toList()));
                claims.claim(SecurityConstants.CLAIM_AUTH_TYPE, userDetails.getAuthType());
                claims.claim(SecurityConstants.CLAIM_AUTH_SOURCE, userDetails.getAuthSource());
            }
        };
    }

    /**
     * 克隆
     * 解决：The class with java.util.ImmutableCollections$ListN and name of java.util.ImmutableCollections$ListN is not in the allowList 问题
     */
    private List<String> clone(List<String> rawList) {
        List<String> cloneList = new ArrayList<>();
        if (rawList != null && !rawList.isEmpty()) {
            cloneList.addAll(rawList);
        }
        return cloneList;
    }

}
