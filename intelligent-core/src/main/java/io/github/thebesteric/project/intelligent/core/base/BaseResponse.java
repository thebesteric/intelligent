package io.github.thebesteric.project.intelligent.core.base;

import io.github.thebesteric.framework.agile.commons.util.JsonUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * BaseResponse
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 16:38:59
 */
@Getter
@Setter
public abstract class BaseResponse<T extends BaseEntity> implements Serializable {
    @Serial
    private static final long serialVersionUID = 9053126985766603314L;

    @Schema(description = "ID")
    protected Long id;

    public BaseResponse<T> transform(T entity, String... ignoreProperties) {
        BeanUtils.copyProperties(entity, this, ignoreProperties);
        return this;
    }

    public String toJson(T t) {
        return JsonUtils.toJson(t);
    }

    public <K, V> Map<K, V> toMap(Object obj, Class<K> kClass, Class<V> vClass) {
        return JsonUtils.toMap(obj, kClass, vClass);
    }

    public <K, V> Map<K, V> toMap(String jsonStr, Class<K> kClass, Class<V> vClass) {
        return JsonUtils.toMap(jsonStr, kClass, vClass);
    }
}
