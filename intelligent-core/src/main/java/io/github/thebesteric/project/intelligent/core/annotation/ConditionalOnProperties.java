package io.github.thebesteric.project.intelligent.core.annotation;

import io.github.thebesteric.project.intelligent.core.condition.OnPropertiesCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * ConditionalOnProperties
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-06-04 17:49:08
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnPropertiesCondition.class)
public @interface ConditionalOnProperties {
    ConditionalOnProperty[] value();
}
