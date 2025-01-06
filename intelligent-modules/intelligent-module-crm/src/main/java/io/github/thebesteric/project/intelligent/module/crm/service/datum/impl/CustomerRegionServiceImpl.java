package io.github.thebesteric.project.intelligent.module.crm.service.datum.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.Processor;
import io.github.thebesteric.project.intelligent.core.constant.SeedType;
import io.github.thebesteric.project.intelligent.core.exception.DataAlreadyExistsException;
import io.github.thebesteric.project.intelligent.core.exception.DataNotFoundException;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.core.service.common.SeedService;
import io.github.thebesteric.project.intelligent.module.crm.mapper.datum.CustomerRegionMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRegionCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRegionUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerRegionResponse;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Customer;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerRegion;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerRegionService;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerRegionUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CustomerRegionServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:50:16
 */
@Service
@RequiredArgsConstructor
public class CustomerRegionServiceImpl extends ServiceImpl<CustomerRegionMapper, CustomerRegion> implements CustomerRegionService {

    private final SeedService seedService;
    private final CustomerRegionUserService regionUserService;

    /**
     * 客户区域列表
     *
     * @return List<Tree>
     *
     * @author wangweijun
     * @since 2024/12/30 10:48
     */
    @Override
    public List<Tree<Long>> tree() {
        String tenantId = SecurityUtils.getTenantIdWithException();
        List<CustomerRegion> regions = this.listByTenantId(tenantId);

        // 封装
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setNameKey("name");
        treeNodeConfig.setParentIdKey("parentId");
        treeNodeConfig.setWeightKey("seq");
        return TreeUtil.build(regions, null, treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getId());
            tree.setName(treeNode.getName());
            tree.setParentId(treeNode.getParentId());
            tree.setWeight(treeNode.getSeq());
            tree.putExtra("code", treeNode.getCode());
            tree.putExtra("state", treeNode.getState());
        });
    }

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
    @Override
    public List<CustomerRegionResponse> childRegions(Long regionId) {
        return Processor.prepare()
                .start(() -> {
                    String tenantId = SecurityUtils.getTenantId();
                    return this.findByParentId(tenantId, regionId);
                })
                .next(regions -> regions.stream().map(i -> (CustomerRegionResponse) new CustomerRegionResponse().transform(i)).collect(Collectors.toList()))
                .complete(regionResponses -> {
                    // 加入自身
                    if (regionId != null) {
                        CustomerRegion currentRegion = getById(regionId);
                        regionResponses.add(0, (CustomerRegionResponse) new CustomerRegionResponse().transform(currentRegion));
                    }
                    return regionResponses;
                });
    }

    /**
     * 创建客户区域
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/30 11:38
     */
    @Override
    public void create(CustomerRegionCreateRequest createRequest) {
        String tenantId = createRequest.getTenantIdWithException();
        Processor.prepare()
                .start(() -> this.findByCode(tenantId, createRequest.getCode()))
                .validate(sameCodes -> {
                    if (StringUtils.isNotBlank(createRequest.getCode()) && !sameCodes.isEmpty()) {
                        throw new DataAlreadyExistsException("区域编码重复");
                    }
                })
                .next(() -> {
                    String code = createRequest.getCode();
                    if (StringUtils.isBlank(code)) {
                        code = seedService.getAndIncrement(tenantId, SeedType.CUSTOMER_REGION_CODE);
                        createRequest.setCode(code);
                    }
                    return createRequest.transform();
                })
                .complete(this::save);
    }

    /**
     * 更新客户区域
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/30 14:50
     */
    @Override
    public void update(CustomerRegionUpdateRequest updateRequest) {
        String tenantId = updateRequest.getTenantIdWithException();
        Processor.prepare()
                .start(() -> this.findByCode(tenantId, updateRequest.getCode()))
                .validate(sameCodes -> {
                    if (sameCodes.isEmpty()) {
                        return;
                    }
                    if (sameCodes.size() == 1) {
                        CustomerRegion maybeSelf = sameCodes.get(0);
                        if (maybeSelf.getId().equals(updateRequest.getId())) {
                            return;
                        }
                    }
                    throw new DataAlreadyExistsException("区域编码重复");
                })
                .next(() -> getByTenantAndId(tenantId, updateRequest.getId()))
                .validate(relationAlarm -> {
                    if (relationAlarm == null) {
                        throw new DataNotFoundException("区域不存在");
                    }
                })
                .next(customerRegion -> {
                    String requestCode = updateRequest.getCode();
                    if (StringUtils.isBlank(requestCode)) {
                        String databaseCode = customerRegion.getCode();
                        if (StringUtils.isBlank(databaseCode)) {
                            databaseCode = seedService.getAndIncrement(tenantId, SeedType.CUSTOMER_REGION_CODE);
                        }
                        updateRequest.setCode(databaseCode);
                    }
                    return updateRequest.merge(customerRegion);
                })
                .complete(this::updateById);
    }

    /**
     * 删除客户区域
     *
     * @param regionId 区域 ID
     *
     * @author wangweijun
     * @since 2024/12/30 17:02
     */
    @Override
    public void delete(Long regionId) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        Processor.prepare()
                .start(() -> this.findByParentId(tenantId, regionId))
                .validate(children -> {
                    if (!children.isEmpty()) {
                        throw new DataAlreadyExistsException("该区域下存在下级区域，无法删除");
                    }
                })
                .validate(() -> {
                    List<Customer> regionUsers = regionUserService.findByRegionId(tenantId, regionId);
                    if (!regionUsers.isEmpty()) {
                        throw new DataAlreadyExistsException("该区域下存在客户，无法删除");
                    }
                })
                .complete(() -> this.deleteByTenantAndId(tenantId, regionId));
    }

    /**
     * 根据 parentId 查询客户区域
     *
     * @param tenantId 租户 ID
     * @param regionId 区域 ID
     *
     * @return List<CustomerRegion>
     *
     * @author wangweijun
     * @since 2024/12/30 17:12
     */
    private List<CustomerRegion> findByParentId(String tenantId, Long regionId) {
        return this.lambdaQuery().eq(CustomerRegion::getTenantId, tenantId)
                .isNull(regionId == null, CustomerRegion::getParentId)
                .eq(regionId != null, CustomerRegion::getParentId, regionId)
                .orderByAsc(CustomerRegion::getSeq, CustomerRegion::getCreatedAt)
                .list();
    }

    /**
     * 根据 name 和 code 查询客户区域
     *
     * @param tenantId 租户 ID
     * @param code     区域编码
     *
     * @return List<CustomerRegion>
     *
     * @author wangweijun
     * @since 2024/12/30 14:52
     */
    private List<CustomerRegion> findByCode(String tenantId, String code) {
        return this.lambdaQuery().eq(CustomerRegion::getTenantId, tenantId)
                .eq(CustomerRegion::getCode, code)
                .list();
    }
}
