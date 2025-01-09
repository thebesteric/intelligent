package io.github.thebesteric.project.intelligent.core.base;

import io.github.thebesteric.framework.agile.plugins.idempotent.annotation.IdempotentKey;
import io.github.thebesteric.project.intelligent.core.exception.BizException;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import io.vavr.control.Try;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.beans.Transient;
import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * BaseRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-04 17:10:25
 */
@Getter
@Setter
public abstract class BaseRequest<T extends BaseEntity> implements Serializable {
    @Serial
    private static final long serialVersionUID = -5167786334495406870L;

    @IdempotentKey
    @Schema(description = "租户 ID", hidden = true)
    private String tenantId;

    public T transform(String... ignoreProperties) {
        return Try.of(() -> {
            T target = getEntityClass().getConstructor().newInstance();
            BeanUtils.copyProperties(this, target, ignoreProperties);
            return target;
        }).onFailure(e -> {
            throw new BizException(e);
        }).get();
    }

    public T merge(T target, String... ignoreProperties) {
        BeanUtils.copyProperties(this, target, ignoreProperties);
        return target;
    }

    @SuppressWarnings("unchecked")
    public Class<T> getEntityClass() {
        Class<?> clazz = getClass();
        Type genericSuperclass;
        do {
            genericSuperclass = clazz.getGenericSuperclass();
            clazz = clazz.getSuperclass();
        } while (clazz != BaseRequest.class && clazz != Object.class);

        if (genericSuperclass instanceof ParameterizedType parameterizedType) {
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                return (Class<T>) actualTypeArguments[0];
            }
        }
        return null;
    }

    @Transient
    @Schema(hidden = true)
    public String getTenantIdWithException() {
        return SecurityUtils.getTenantIdWithException();
    }
}
