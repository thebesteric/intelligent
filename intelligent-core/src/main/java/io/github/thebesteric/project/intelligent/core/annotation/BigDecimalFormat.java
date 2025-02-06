package io.github.thebesteric.project.intelligent.core.annotation;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.thebesteric.project.intelligent.core.serializer.BigDecimalFormatSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.RoundingMode;

/**
 * BigDecimalFormat
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-02-05 17:54:30
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JsonSerialize(using = BigDecimalFormatSerializer.class)
public @interface BigDecimalFormat {
    int scale() default 2;
    RoundingMode roundingMode() default RoundingMode.HALF_UP;
}

