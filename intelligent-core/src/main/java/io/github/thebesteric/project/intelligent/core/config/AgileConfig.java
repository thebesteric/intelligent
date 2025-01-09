package io.github.thebesteric.project.intelligent.core.config;

import io.github.thebesteric.framework.agile.commons.util.ReflectUtils;
import io.github.thebesteric.framework.agile.plugins.annotation.scanner.AnnotationRegister;
import io.github.thebesteric.framework.agile.plugins.logger.domain.RequestLog;
import io.github.thebesteric.framework.agile.plugins.logger.processor.ignore.RequestIgnoreProcessor;
import io.github.thebesteric.framework.agile.plugins.logger.processor.ignore.impl.HeaderIgnoreProcessor;
import io.github.thebesteric.framework.agile.starter.annotaion.EnableAgile;
import io.github.thebesteric.project.intelligent.core.annotation.SkipAuth;
import io.github.thebesteric.project.intelligent.core.initializer.DatabaseAgileSensitiveLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * AgileConfig
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-29 13:31:10
 */
@Configuration
@EnableAgile
public class AgileConfig {

    @Bean
    public DatabaseAgileSensitiveLoader databaseAgileSensitiveLoader() {
        return new DatabaseAgileSensitiveLoader();
    }

    @Bean
    public RequestIgnoreProcessor headerIgnoreProcessor() {
        return new HeaderIgnoreProcessor() {
            @Override
            public String[] doIgnore(RequestLog requestLog) {
                return new String[0];
            }

            @Override
            public Map<String, String> doRewrite(RequestLog requestLog) {
                return Map.of("authorization", "*");
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public AnnotationRegister annotationRegister() {
        AnnotationRegister annotationRegister = new AnnotationRegister();
        annotationRegister.register(SkipAuth.class, parasitic -> {
            Class<?> clazz = parasitic.getClazz();
            return ReflectUtils.anyAnnotationPresent(clazz, Controller.class, RestController.class);
        });
        return annotationRegister;
    }

}
