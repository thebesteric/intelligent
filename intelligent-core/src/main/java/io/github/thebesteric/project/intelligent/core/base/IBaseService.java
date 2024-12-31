package io.github.thebesteric.project.intelligent.core.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.thebesteric.framework.agile.plugins.database.core.domain.query.OrderByOperator;
import io.github.thebesteric.framework.agile.plugins.database.core.domain.query.OrderByParam;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * BaseRepositoryService
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-06 15:25:10
 */
public interface IBaseService<T extends BaseEntity> extends IService<T> {

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
        return entity != null && entity.getId() == null && this.save(entity);
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

    /**
     * 根据参数获取对象
     *
     * @param queryParams 参数
     *
     * @return T
     *
     * @author wangweijun
     * @since 2024/12/17 21:00
     */
    default T getByParams(Map<String, Object> queryParams) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryParams.forEach(queryWrapper::eq);
        return getOne(queryWrapper);
    }

    /**
     * 根据参数获取对象集合
     *
     * @param queryParams   查询参数
     * @param orderByParams 排序参数
     *
     * @return List<T>
     *
     * @author wangweijun
     * @since 2024/12/17 21:00
     */
    default List<T> listByParams(Map<String, Object> queryParams, OrderByParam... orderByParams) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryParams.forEach(queryWrapper::eq);
        if (orderByParams != null) {
            for (OrderByParam orderByParam : orderByParams) {
                boolean isAsc = orderByParam.getOrderByOperator() == OrderByOperator.ASC;
                String column = orderByParam.getKey();
                queryWrapper.orderBy(true, isAsc, column);
            }
        }
        return list(queryWrapper);
    }

    /**
     * 根据租户 ID 和 ID 获取对象
     *
     * @param tenantId 租户 ID
     * @param id       ID
     *
     * @return T
     *
     * @author wangweijun
     * @since 2024/12/19 11:14
     */
    default T getByTenantAndId(String tenantId, Long id) {
        return getOne(new QueryWrapper<T>().eq("tenant_id", tenantId).eq("id", id));
    }

    /**
     * 根据租户 ID 获取对象
     *
     * @param tenantId      租户 ID
     * @param orderByParams 排序参数
     *
     * @return List<T>
     *
     * @author wangweijun
     * @since 2024/12/20 10:16
     */
    default List<T> listByTenantId(String tenantId, OrderByParam... orderByParams) {
        return listByParams(Map.of("tenant_id", tenantId), orderByParams);
    }

    /**
     * 根据租户 ID 和 ID 删除对象
     *
     * @param tenantId 租户 ID
     * @param id       ID
     *
     * @return boolean
     *
     * @author wangweijun
     * @since 2024/12/20 14:43
     */
    default boolean deleteByTenantAndId(String tenantId, Long id) {
        return this.remove(new QueryWrapper<T>().eq("tenant_id", tenantId).eq("id", id));
    }

}
