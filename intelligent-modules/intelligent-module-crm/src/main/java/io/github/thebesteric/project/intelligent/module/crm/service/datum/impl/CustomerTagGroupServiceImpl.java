package io.github.thebesteric.project.intelligent.module.crm.service.datum.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.Processor;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.exception.DataAlreadyExistsException;
import io.github.thebesteric.project.intelligent.core.exception.DataNotFoundException;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.module.crm.mapper.datum.CustomerTagGroupMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagGroupCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagGroupSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagGroupUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerTagGroupResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerTagGroup;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerTagGroupService;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CustomerTagGroupServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-27 12:41:29
 */
@Service
@RequiredArgsConstructor
public class CustomerTagGroupServiceImpl extends ServiceImpl<CustomerTagGroupMapper, CustomerTagGroup> implements CustomerTagGroupService {

    private final CustomerTagService tagService;

    /**
     * 查询
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<CustomerTagGroupResponse>
     *
     * @author wangweijun
     * @since 2024/12/27 13:30
     */
    @Override
    public PagingResponse<CustomerTagGroupResponse> page(CustomerTagGroupSearchRequest searchRequest) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        IPage<CustomerTagGroupResponse> page = searchRequest.getPage(CustomerTagGroupResponse.class);
        page = this.getBaseMapper().page(tenantId, searchRequest.getName(), page);
        return PagingResponse.of(page);
    }

    /**
     * 创建标签组
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/27 14:20
     */
    @Override
    public void create(CustomerTagGroupCreateRequest createRequest) {
        Processor.prepare()
                .start(() -> {
                    String tenantId = createRequest.getTenantIdWithException();
                    return this.findByName(tenantId, createRequest.getName());
                })
                .validate(sameNames -> {
                    if (!sameNames.isEmpty()) {
                        throw new DataAlreadyExistsException("标签组名称重复");
                    }
                })
                .next(() -> createRequest.transform())
                .complete(this::save);
    }

    /**
     * 更新标签组
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/27 15:44
     */
    @Override
    public void update(CustomerTagGroupUpdateRequest updateRequest) {
        Processor.prepare()
                .start(() -> this.findByName(updateRequest.getTenantId(), updateRequest.getName()))
                .validate(sameNames -> {
                    if (sameNames.isEmpty()) {
                        return;
                    }
                    if (sameNames.size() == 1) {
                        CustomerTagGroup maybeSelf = sameNames.get(0);
                        if (maybeSelf.getId().equals(updateRequest.getId())) {
                            return;
                        }
                    }
                    throw new DataAlreadyExistsException("标签组名称重复");
                })
                .next(() -> getByTenantAndId(updateRequest.getTenantId(), updateRequest.getId()))
                .validate(relationAlarm -> {
                    if (relationAlarm == null) {
                        throw new DataNotFoundException("标签组不存在");
                    }
                })
                .next(updateRequest::merge)
                .complete(this::updateById);
    }

    /**
     * 删除标签组
     *
     * @param id ID
     *
     * @author wangweijun
     * @since 2024/12/27 15:45
     */
    @Override
    public void delete(Long id) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        Processor.prepare()
                .start(() -> tagService.findByGroupId(tenantId, id))
                .validate(tags -> {
                    if (!tags.isEmpty()) {
                        throw new DataAlreadyExistsException("该标签组下存在标签，无法删除");
                    }
                })
                .complete(() -> this.deleteByTenantAndId(tenantId, id));
    }

    /**
     * 根据名称获取标签组
     *
     * @param tenantId 租户 ID
     * @param name     名称
     *
     * @return List<CustomerTagGroup>
     *
     * @author wangweijun
     * @since 2024/12/26 13:38
     */
    private List<CustomerTagGroup> findByName(String tenantId, String name) {
        return this.lambdaQuery().eq(CustomerTagGroup::getTenantId, tenantId)
                .eq(CustomerTagGroup::getName, name)
                .list();
    }
}
