package io.github.thebesteric.project.intelligent.core.condition;

import io.github.thebesteric.project.intelligent.core.annotation.ConditionalOnProperties;
import jakarta.annotation.Nonnull;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * OnPropertiesCondition
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-06-04 17:54:13
 */
public class OnPropertiesCondition implements Condition, Ordered {

    @SuppressWarnings("unchecked")
    @Override
    public boolean matches(@Nonnull ConditionContext context, @Nonnull AnnotatedTypeMetadata metadata) {
        MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(ConditionalOnProperties.class.getName(), true);
        if (attrs == null) {
            return false;
        }

        List<Object> value = attrs.get("value").stream().toList();
        if (!value.isEmpty()) {
            Map<String, Object>[] mapArr = (LinkedHashMap<String, Object>[]) value.get(0);
            for (Map<String, Object> map : mapArr) {
                ConditionalOnPropertyDomain propertyDomain = new ConditionalOnPropertyDomain();
                try {
                    BeanUtils.populate(propertyDomain, map);
                } catch (Exception e) {
                    return false;
                }
                if (isMatch(context.getEnvironment(), propertyDomain)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isMatch(Environment environment, ConditionalOnPropertyDomain property) {
        if (property.getValue() != null && property.getValue().length > 0) {
            property.setName(property.getValue());
        }
        for (String name : property.getName()) {
            String propertyName = property.getPrefix() + "." + name;
            String propertyValue = environment.getProperty(propertyName);
            if (propertyValue == null && property.isMatchIfMissing()) {
                return true;
            }
            if (propertyValue != null && propertyValue.equals(property.getHavingValue())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Data
    public static class ConditionalOnPropertyDomain {
        private String prefix;
        private String[] name;
        private String[] value;
        private String havingValue;
        private boolean matchIfMissing;
    }
}
