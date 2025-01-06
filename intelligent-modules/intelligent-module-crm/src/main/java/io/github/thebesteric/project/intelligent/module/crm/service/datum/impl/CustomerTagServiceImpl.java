package io.github.thebesteric.project.intelligent.module.crm.service.datum.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.commons.util.Processor;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.exception.DataAlreadyExistsException;
import io.github.thebesteric.project.intelligent.core.exception.DataNotFoundException;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.module.crm.mapper.datum.CustomerTagMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagCreateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerTagUpdateRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerTagResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerTag;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CustomerTagServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-27 12:41:19
 */
@Service
@RequiredArgsConstructor
public class CustomerTagServiceImpl extends ServiceImpl<CustomerTagMapper, CustomerTag> implements CustomerTagService {

    /**
     * 查询
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<CustomerTagResponse>
     *
     * @author wangweijun
     * @since 2024/12/27 13:30
     */
    @Override
    public PagingResponse<CustomerTagResponse> page(CustomerTagSearchRequest searchRequest) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        IPage<CustomerTagResponse> page = searchRequest.getPage(CustomerTagResponse.class);
        page = this.getBaseMapper().page(tenantId, searchRequest.getKeyword(), page);
        return PagingResponse.of(page);
    }

    /**
     * 创建标签
     *
     * @param createRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/27 14:20
     */
    @Override
    public void create(CustomerTagCreateRequest createRequest) {
        Processor.prepare()
                .start(() -> {
                    String tenantId = createRequest.getTenantIdWithException();
                    return this.findByName(tenantId, createRequest.getName());
                })
                .validate(sameNames -> {
                    if (!sameNames.isEmpty()) {
                        throw new DataAlreadyExistsException("标签名称重复");
                    }
                })
                .next(() -> createRequest.transform())
                .complete(this::save);
    }

    /**
     * 更新标签
     *
     * @param updateRequest 请求
     *
     * @author wangweijun
     * @since 2024/12/27 15:44
     */
    @Override
    public void update(CustomerTagUpdateRequest updateRequest) {
        Processor.prepare()
                .start(() -> this.findByName(updateRequest.getTenantId(), updateRequest.getName()))
                .validate(sameNames -> {
                    if (sameNames.isEmpty()) {
                        return;
                    }
                    if (sameNames.size() == 1) {
                        CustomerTag maybeSelf = sameNames.get(0);
                        if (maybeSelf.getId().equals(updateRequest.getId())) {
                            return;
                        }
                    }
                    throw new DataAlreadyExistsException("标签名称重复");
                })
                .next(() -> getByTenantAndId(updateRequest.getTenantId(), updateRequest.getId()))
                .validate(relationAlarm -> {
                    if (relationAlarm == null) {
                        throw new DataNotFoundException("标签不存在");
                    }
                })
                .next(updateRequest::merge)
                .complete(this::updateById);
    }

    /**
     * 删除标签
     *
     * @param id ID
     *
     * @author wangweijun
     * @since 2024/12/27 15:45
     */
    @Override
    public void delete(Long id) {
        this.deleteByTenantAndId(SecurityUtils.getTenantIdWithException(), id);
    }

    /**
     * 根据标签组 ID 获取标签
     *
     * @param tenantId 租户 ID
     * @param groupId  标签组 ID
     *
     * @return List<CustomerTag>
     *
     * @author wangweijun
     * @since 2024/12/27 17:36
     */
    @Override
    public List<CustomerTag> findByGroupId(String tenantId, Long groupId) {
        return this.lambdaQuery().eq(CustomerTag::getTenantId, tenantId).eq(CustomerTag::getGroupId, groupId).list();
    }

    /**
     * 根据名称获取标签
     *
     * @param tenantId 租户 ID
     * @param name     名称
     *
     * @return List<CustomerTag>
     *
     * @author wangweijun
     * @since 2024/12/26 13:38
     */
    private List<CustomerTag> findByName(String tenantId, String name) {
        return this.lambdaQuery().eq(CustomerTag::getTenantId, tenantId)
                .eq(CustomerTag::getName, name)
                .list();
    }
}
