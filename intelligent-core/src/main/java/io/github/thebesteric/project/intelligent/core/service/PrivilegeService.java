package io.github.thebesteric.project.intelligent.core.service;

import io.github.thebesteric.project.intelligent.core.constant.PrivilegeType;
import io.github.thebesteric.project.intelligent.core.model.entity.core.Privilege;
import io.github.thebesteric.project.intelligent.core.base.BaseRepositoryService;

public interface PrivilegeService extends BaseRepositoryService<Privilege> {

    /**
     * 初始化
     *
     * @author wangweijun
     * @since 2024/12/5 11:12
     */
    void init();

    /**
     * 根据权限编码获取权限
     *
     * @param code 权限编码
     *
     * @return Privilege
     *
     * @author wangweijun
     * @since 2024/12/5 11:11
     */
    Privilege getByCode(String code);

    /**
     * 根据权限名称和类型获取权限
     *
     * @param name 权限名称
     * @param type 权限类型
     *
     * @return Privilege
     *
     * @author wangweijun
     * @since 2024/12/6 16:04
     */
    Privilege getByNameAndType(String name, PrivilegeType type);
}
