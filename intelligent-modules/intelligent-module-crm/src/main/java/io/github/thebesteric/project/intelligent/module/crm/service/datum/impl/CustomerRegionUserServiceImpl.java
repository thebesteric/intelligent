package io.github.thebesteric.project.intelligent.module.crm.service.datum.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.framework.agile.core.domain.page.PagingResponse;
import io.github.thebesteric.project.intelligent.core.model.entity.core.User;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import io.github.thebesteric.project.intelligent.core.service.core.UserService;
import io.github.thebesteric.project.intelligent.module.crm.mapper.datum.CustomerRegionUserMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRegionUserSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerRegionUserResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerRegionUser;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerRegionUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CustomerRegionUserServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-30 17:27:35
 */
@Service
@RequiredArgsConstructor
public class CustomerRegionUserServiceImpl extends ServiceImpl<CustomerRegionUserMapper, CustomerRegionUser> implements CustomerRegionUserService {

    private final UserService userService;

    /**
     * 查询
     *
     * @param searchRequest 请求
     *
     * @return PagingResponse<CustomerRegionUserResponse>
     *
     * @author wangweijun
     * @since 2024/12/30 18:20
     */
    @Override
    public PagingResponse<CustomerRegionUserResponse> page(CustomerRegionUserSearchRequest searchRequest) {
        String tenantId = SecurityUtils.getTenantIdWithException();
        IPage<CustomerRegionUserResponse> page = searchRequest.getPage(CustomerRegionUserResponse.class);
        page = this.getBaseMapper().page(tenantId, searchRequest, page);

        // 处理关联业务员
        List<CustomerRegionUserResponse> records = page.getRecords();
        for (CustomerRegionUserResponse item : records) {
            CustomerRegionUserResponse.CustomerSimpleResponse customer = item.getCustomer();
            List<String> salesUserIds = customer.getSalesUserIds();
            for (String salesUserId : salesUserIds) {
                User salesUser = userService.getById(Long.valueOf(salesUserId));
                customer.getSalesUsers().add(CustomerRegionUserResponse.SalesUserInfo.of(salesUser));
            }
        }

        return PagingResponse.of(page);
    }

    /**
     * 根据区域 ID 查询区域用户
     *
     * @param tenantId 租户 ID
     * @param regionId 区域 ID
     *
     * @return List<CustomerRegionUser>
     *
     * @author wangweijun
     * @since 2024/12/30 17:30
     */
    @Override
    public List<CustomerRegionUser> findByRegionId(String tenantId, Long regionId) {
        return this.lambdaQuery().eq(CustomerRegionUser::getTenantId, tenantId)
                .eq(CustomerRegionUser::getRegionId, regionId)
                .list();
    }
}
