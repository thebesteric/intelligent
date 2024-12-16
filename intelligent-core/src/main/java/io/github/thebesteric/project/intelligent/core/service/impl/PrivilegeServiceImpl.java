package io.github.thebesteric.project.intelligent.core.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.constant.PrivilegeCode;
import io.github.thebesteric.project.intelligent.core.constant.PrivilegeType;
import io.github.thebesteric.project.intelligent.core.mapper.PrivilegeMapper;
import io.github.thebesteric.project.intelligent.core.model.entity.core.Privilege;
import io.github.thebesteric.project.intelligent.core.service.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * PrivilegeServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-04 16:22:31
 */
@Service
@RequiredArgsConstructor
@DS(ApplicationConstants.DataSource.INTELLIGENT_CORE_API)
public class PrivilegeServiceImpl extends ServiceImpl<PrivilegeMapper, Privilege> implements PrivilegeService {

    /**
     * 初始化
     *
     * @author wangweijun
     * @since 2024/12/5 11:12
     */
    @Transactional
    @Override
    public void init() {
        // 获取当前所有权限
        List<Privilege> existsPrivileges = this.list();

        Privilege p_1 = Privilege.of(PrivilegeCode.CUSTOMER_MANAGEMENT, existsPrivileges);
        this.saveIfNotNull(p_1);

        Privilege p_1_1 = Privilege.of(p_1, PrivilegeCode.CUSTOMER_DATUM, existsPrivileges);
        this.saveIfNotNull(p_1_1);
        Privilege p_1_1_1 = Privilege.of(p_1_1, PrivilegeCode.CUSTOMER_DATUM_LIST, existsPrivileges);
        Privilege p_1_1_2 = Privilege.of(p_1_1, PrivilegeCode.CUSTOMER_DATUM_TYPE, existsPrivileges);
        Privilege p_1_1_3 = Privilege.of(p_1_1, PrivilegeCode.CUSTOMER_DATUM_AUDIT, existsPrivileges);
        Privilege p_1_1_4 = Privilege.of(p_1_1, PrivilegeCode.CUSTOMER_DATUM_RELATION, existsPrivileges);
        Privilege p_1_1_5 = Privilege.of(p_1_1, PrivilegeCode.CUSTOMER_DATUM_TAG, existsPrivileges);
        Privilege p_1_1_6 = Privilege.of(p_1_1, PrivilegeCode.CUSTOMER_DATUM_REGION, existsPrivileges);
        Privilege p_1_1_7 = Privilege.of(p_1_1, PrivilegeCode.CUSTOMER_DATUM_LOCATED, existsPrivileges);
        this.saveBatchIfNotNull(Arrays.asList(p_1_1_1, p_1_1_2, p_1_1_3, p_1_1_4, p_1_1_5, p_1_1_6, p_1_1_7));

        Privilege p_1_2 = Privilege.of(p_1, PrivilegeCode.CUSTOMER_LIFECYCLE, existsPrivileges);
        this.saveIfNotNull(p_1_2);
        Privilege p_1_2_1 = Privilege.of(p_1_2, PrivilegeCode.CUSTOMER_LIFECYCLE_RENEWAL, existsPrivileges);
        Privilege p_1_2_2 = Privilege.of(p_1_2, PrivilegeCode.CUSTOMER_LIFECYCLE_ACTIVATE, existsPrivileges);
        Privilege p_1_2_3 = Privilege.of(p_1_2, PrivilegeCode.CUSTOMER_LIFECYCLE_REMAIN, existsPrivileges);
        Privilege p_1_2_4 = Privilege.of(p_1_2, PrivilegeCode.CUSTOMER_LIFECYCLE_INCREASE, existsPrivileges);
        this.saveBatchIfNotNull(Arrays.asList(p_1_2_1, p_1_2_2, p_1_2_3, p_1_2_4));

        Privilege p_1_3 = Privilege.of(p_1, PrivilegeCode.CUSTOMER_FINANCE, existsPrivileges);
        this.saveIfNotNull(p_1_3);
        Privilege p_1_3_1 = Privilege.of(p_1_3, PrivilegeCode.CUSTOMER_FINANCE_SCORE, existsPrivileges);
        Privilege p_1_3_2 = Privilege.of(p_1_3, PrivilegeCode.CUSTOMER_FINANCE_SETTING, existsPrivileges);
        Privilege p_1_3_3 = Privilege.of(p_1_3, PrivilegeCode.CUSTOMER_FINANCE_PREPAID, existsPrivileges);
        Privilege p_1_3_4 = Privilege.of(p_1_3, PrivilegeCode.CUSTOMER_FINANCE_AUTH, existsPrivileges);
        this.saveBatchIfNotNull(Arrays.asList(p_1_3_1, p_1_3_2, p_1_3_3, p_1_3_4));

        Privilege p_1_4 = Privilege.of(p_1, PrivilegeCode.CUSTOMER_SETTING, existsPrivileges);
        this.saveIfNotNull(p_1_4);
        Privilege p_1_4_1 = Privilege.of(p_1_4, PrivilegeCode.CUSTOMER_SETTING_CUSTOMER, existsPrivileges);
        Privilege p_1_4_2 = Privilege.of(p_1_4, PrivilegeCode.CUSTOMER_SETTING_ACCOUNT, existsPrivileges);
        Privilege p_1_4_3 = Privilege.of(p_1_4, PrivilegeCode.CUSTOMER_SETTING_PRIVACY, existsPrivileges);
        Privilege p_1_4_4 = Privilege.of(p_1_4, PrivilegeCode.CUSTOMER_SETTING_SERVICE, existsPrivileges);
        this.saveBatchIfNotNull(Arrays.asList(p_1_4_1, p_1_4_2, p_1_4_3, p_1_4_4));
    }

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
    @Override
    public Privilege getByCode(String code) {
        return this.lambdaQuery().eq(Privilege::getCode, code).one();
    }

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
    @Override
    public Privilege getByNameAndType(String name, PrivilegeType type) {
        return this.lambdaQuery().eq(Privilege::getName, name).eq(Privilege::getType, type).one();
    }
}
