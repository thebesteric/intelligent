package io.github.thebesteric.project.intelligent.core.base;

import io.github.thebesteric.project.intelligent.core.exception.BizException;
import io.vavr.control.Try;
import org.springframework.beans.BeanUtils;

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
public abstract class BaseRequest<T extends BaseEntity> implements Serializable {
    @Serial
    private static final long serialVersionUID = -5167786334495406870L;

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

}
