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
public abstract class BaseRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -5167786334495406870L;

    public <T> T transform(Class<T> clazz) {
        return Try.of(() -> {
            T t = clazz.getConstructor().newInstance();
            BeanUtils.copyProperties(this, t);
            return t;
        }).onFailure(e -> {
            throw new BizException(e);
        }).get();

    }
}
