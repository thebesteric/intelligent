package io.github.thebesteric.project.intelligent.oauth.detail.crm;

import io.github.thebesteric.framework.agile.commons.util.DataValidator;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthSource;
import io.github.thebesteric.project.intelligent.core.constant.security.AuthType;
import io.github.thebesteric.project.intelligent.core.exception.BizException;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Customer;
import io.github.thebesteric.project.intelligent.oauth.model.domain.CustomUserDetails;
import io.github.thebesteric.project.intelligent.oauth.detail.CustomUserDetailsService;
import io.github.thebesteric.project.intelligent.oauth.service.crm.CrmUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * CrmUserDetailService
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-01-03 16:43:10
 */
@Service
@RequiredArgsConstructor
public class CrmUserDetailService implements CustomUserDetailsService {

    private final CrmUserService crmUserService;

    @Override
    public CustomUserDetails loadUserByIdentity(String identity) {
        Customer customer = crmUserService.getByUsername(identity);
        // 校验用户
        checkUser(customer);
        // 数据封装
        return CustomUserDetails.builder()
                .id(String.valueOf(customer.getId()))
                .tenantId(customer.getTenantId())
                .username(customer.getUsername())
                .name(customer.getName())
                .password(customer.getPassword())
                .email(null)
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .enabled(true)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .authSource(getAuthSource().getSource())
                .authType(getAuthType().getType())
                .roles(null)
                .privileges(null)
                .extra(null)
                .build();
    }

    @Override
    public AuthSource getAuthSource() {
        return AuthSource.MODULE_CRM;
    }

    @Override
    public AuthType getAuthType() {
        return AuthType.PASSWORD;
    }

    /**
     * 校验用户
     *
     * @param user user
     *
     * @author wangweijun
     * @since 2024/12/16 14:12
     */
    private void checkUser(Customer customer) {
        DataValidator.create()
                .validate(customer == null, new UsernameNotFoundException("用户或密码错误"))
                .validate(Objects.requireNonNull(customer).getState() == 0, new BizException(BizException.BizCode.DATA_VALID_ERROR, "用户已禁用"));
    }
}
