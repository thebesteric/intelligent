package io.github.thebesteric.project.intelligent.modules.common.config;

import io.github.thebesteric.framework.agile.commons.util.CollectionUtils;
import io.github.thebesteric.framework.agile.plugins.annotation.scanner.AnnotationParasiticContext;
import io.github.thebesteric.framework.agile.plugins.annotation.scanner.domain.Parasitic;
import io.github.thebesteric.project.intelligent.core.annotation.SkipAuth;
import io.github.thebesteric.project.intelligent.core.security.SecurityAccessDeniedHandler;
import io.github.thebesteric.project.intelligent.core.security.SecurityAuthenticationEntryPoint;
import io.github.thebesteric.project.intelligent.core.security.ignored.SecurityRequestMatcherProcessor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 资源服务器配置
 *
 * @author fox
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
@EnableGlobalAuthentication
public class ResourceServerConfig {

    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   List<SecurityRequestMatcherProcessor> securityRequestMatcherProcessors,
                                                   AnnotationParasiticContext annotationParasiticContext) throws Exception {
        http
                // 资源请求处理
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    // 对 authorizeHttpRequests 的扩展点
                    if (securityRequestMatcherProcessors != null) {
                        for (SecurityRequestMatcherProcessor processor : securityRequestMatcherProcessors) {
                            if (processor.getClass().getPackageName().startsWith("io.github.thebesteric.project.intelligent")) {
                                processor.process(authorizeHttpRequests);
                            }
                        }
                    }

                    // 获取 SkipAuth 注解的所有方法
                    Set<String> skipAuthMethodUrls = getSkipAuthMethodUrls(annotationParasiticContext);
                    for (String skipAuthMethodUrl : skipAuthMethodUrls) {
                        authorizeHttpRequests.requestMatchers(skipAuthMethodUrl).permitAll();
                    }

                    // 所有的访问都需要通过身份认证
                    authorizeHttpRequests.anyRequest().authenticated();
                })
                // 资源服务器处理
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                        .jwt(Customizer.withDefaults())
                        // 认证异常处理
                        .authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
                        // 没有权限的异常处理
                        .accessDeniedHandler(new SecurityAccessDeniedHandler())
                )
                // 解决跨域问题
                .cors(Customizer.withDefaults())
                // 关闭跨站请求伪造
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }


    private Set<String> getSkipAuthMethodUrls(AnnotationParasiticContext annotationParasiticContext) {
        Set<String> requestUrls = new HashSet<>();

        List<Parasitic> parasites = annotationParasiticContext.get(SkipAuth.class);
        if (parasites != null && !parasites.isEmpty()) {
            List<Method> methods = new ArrayList<>();
            // 获取所有符合条件的方法
            for (Parasitic parasitic : parasites) {
                Class<?> clazz = parasitic.getClazz();
                if (parasitic.annotationOnClass()) {
                    Method[] declaredMethods = clazz.getDeclaredMethods();
                    methods.addAll(List.of(declaredMethods));
                } else {
                    Method method = parasitic.getMethod();
                    methods.add(method);
                }
            }

            // 拼接 URL
            for (Method method : methods) {
                Class<?> declaringClass = method.getDeclaringClass();
                RequestMapping requestMapping = declaringClass.getAnnotation(RequestMapping.class);
                String[] classRequestMappingUrls = null;
                if (requestMapping != null) {
                    classRequestMappingUrls = requestMapping.value();
                    if (classRequestMappingUrls.length == 0) {
                        classRequestMappingUrls = requestMapping.path();
                    }
                }

                // 适配 RequestMapping 类型
                if (method.isAnnotationPresent(GetMapping.class)) {
                    processRequestMapping(method, GetMapping.class, classRequestMappingUrls, requestUrls);
                } else if (method.isAnnotationPresent(PostMapping.class)) {
                    processRequestMapping(method, PostMapping.class, classRequestMappingUrls, requestUrls);
                } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                    processRequestMapping(method, DeleteMapping.class, classRequestMappingUrls, requestUrls);
                } else if (method.isAnnotationPresent(PutMapping.class)) {
                    processRequestMapping(method, PutMapping.class, classRequestMappingUrls, requestUrls);
                } else if (method.isAnnotationPresent(PatchMapping.class)) {
                    processRequestMapping(method, PatchMapping.class, classRequestMappingUrls, requestUrls);
                } else if (method.isAnnotationPresent(RequestMapping.class)) {
                    processRequestMapping(method, RequestMapping.class, classRequestMappingUrls, requestUrls);
                }
            }
        }

        return requestUrls;
    }

    @SneakyThrows
    private void processRequestMapping(Method method, Class<? extends Annotation> annotationType, String[] classRequestMappingUrls, Set<String> requestUrls) {
        if (method.isAnnotationPresent(annotationType)) {
            Annotation annotation = method.getAnnotation(annotationType);
            Method methodValue = annotation.annotationType().getMethod("value");
            String[] methodUrls = (String[]) methodValue.invoke(annotation);
            if (CollectionUtils.isEmpty(methodUrls)) {
                Method methodPath = annotation.annotationType().getMethod("path");
                methodUrls = (String[]) methodPath.invoke(annotation);
            }
            // 处理并拼接 URL
            processMapping(classRequestMappingUrls, methodUrls, requestUrls);
        }
    }

    public void processMapping(String[] classRequestMappingUrls, String[] methodUrls, Set<String> requestUrls) {
        if (classRequestMappingUrls != null) {
            for (String classRequestMappingUrl : classRequestMappingUrls) {
                classRequestMappingUrl = addUrlSlashPrefix(classRequestMappingUrl);
                if (methodUrls.length == 0) {
                    requestUrls.add(classRequestMappingUrl);
                } else {
                    for (String methodRequestMappingUrl : methodUrls) {
                        String url = classRequestMappingUrl + addUrlSlashPrefix(methodRequestMappingUrl);
                        requestUrls.add(url);
                    }
                }
            }
        }
    }


    private String addUrlSlashPrefix(String url) {
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        return url;
    }


}
