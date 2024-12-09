package io.github.thebesteric.project.intelligent.core.base;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * BaseRepositoryService
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-06 15:25:10
 */
public interface BaseRepositoryService<T extends BaseEntity> extends IService<T> {

    /**
     * 非空时保存
     *
     * @param entity 实体类
     *
     * @return boolean
     *
     * @author wangweijun
     * @since 2024/12/6 15:28
     */
    default boolean saveIfNotNull(T entity) {
        return entity != null && this.save(entity);
    }

    /**
     * 非空时保存
     *
     * @param entityList 实体类列表
     *
     * @author wangweijun
     * @since 2024/12/6 15:43
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    default void saveBatchIfNotNull(Collection<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        for (T entity : entityList) {
            saveIfNotNull(entity);
        }
    }
}
