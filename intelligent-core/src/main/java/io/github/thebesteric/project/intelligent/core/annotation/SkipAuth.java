package io.github.thebesteric.project.intelligent.core.annotation;

import java.lang.annotation.*;

/**
 * 忽略认证
 *
 * @author wangweijun
 * @since 2024/8/13 16:37
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SkipAuth {
}
