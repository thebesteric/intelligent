package io.github.thebesteric.project.intelligent.module.crm.service.datum;

import cn.hutool.core.lang.tree.Tree;
import io.github.thebesteric.project.intelligent.core.base.IBaseService;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRegionCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRegionUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerRegionResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerRegion;

import java.util.List;

public interface CustomerRegionService extends IBaseService<CustomerRegion> {
    /**
     * 客户区域列表
     *
     * @return List<Tree>
     *
     * @author wangweijun
     * @since 2024/12/30 10:48
     */
    List<Tree<Long>> tree();

    /**
     * 子级区域
     *
     * @param regionId 区域 ID
     *
     * @return List<CustomerRegionResponse>
     *
     * @author wangweijun
     * @since 2024/12/30 11:48
     */
    List<CustomerRegionResponse> childRegions(Long regionId);

    /**
     * 创建客户区域
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/30 11:38
     */
    void create(CustomerRegionCreateRequest createRequest);

    /**
     * 更新客户区域
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/30 14:50
     */
    void update(CustomerRegionUpdateRequest updateRequest);

    /**
     * 删除客户区域
     *
     * @param regionId 区域 ID
     *
     * @author wangweijun
     * @since 2024/12/30 17:02
     */
    void delete(Long regionId);
}
