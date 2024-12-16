package io.github.thebesteric.project.intelligent.core.base;

import io.github.thebesteric.project.intelligent.core.exception.BizException;
import io.vavr.control.Try;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;

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

    public T transform(Class<T> clazz, String... ignoreProperties) {
        return Try.of(() -> {
            T target = clazz.getConstructor().newInstance();
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


}
