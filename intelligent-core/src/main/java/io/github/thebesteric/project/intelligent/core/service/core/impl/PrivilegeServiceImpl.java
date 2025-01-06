package io.github.thebesteric.project.intelligent.core.service.core.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.constant.core.PrivilegeCode;
import io.github.thebesteric.project.intelligent.core.constant.core.PrivilegeType;
import io.github.thebesteric.project.intelligent.core.mapper.core.PrivilegeMapper;
import io.github.thebesteric.project.intelligent.core.model.entity.core.Privilege;
import io.github.thebesteric.project.intelligent.core.service.core.PrivilegeService;
import io.github.thebesteric.project.intelligent.core.util.TransactionalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
@DS(ApplicationConstants.Application.Module.OpenApi.DATASOURCE_INTELLIGENT_MODULE_OPEN_API)
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
        // 获取代理类
        PrivilegeServiceImpl serviceProxy = TransactionalUtils.currentProxy(PrivilegeServiceImpl.class);

        // 创建权限
        List<PrivilegeCode> firstLevelPrivilegeCodes = PrivilegeCode.listByParent(null);
        for (PrivilegeCode firstLevelPrivilegeCode : firstLevelPrivilegeCodes) {
            Privilege firstLevelPrivilege = Privilege.of(firstLevelPrivilegeCode, existsPrivileges);
            serviceProxy.saveIfNotNull(firstLevelPrivilege);
            if (firstLevelPrivilege == null) {
                continue;
            }
            List<PrivilegeCode> secondLevelPrivilegeCodes = PrivilegeCode.listByParent(PrivilegeCode.of(firstLevelPrivilege.getCode()));
            for (PrivilegeCode secondLevelPrivilegeCode : secondLevelPrivilegeCodes) {
                Privilege secondLevelPrivilege = Privilege.of(firstLevelPrivilege, secondLevelPrivilegeCode, existsPrivileges);
                serviceProxy.saveIfNotNull(secondLevelPrivilege);
                List<Privilege> thirdLevelPrivileges = new ArrayList<>();
                for (PrivilegeCode thirdLevelPrivilegeCode : PrivilegeCode.listByParent(secondLevelPrivilegeCode)) {
                    thirdLevelPrivileges.add(Privilege.of(secondLevelPrivilege, thirdLevelPrivilegeCode, existsPrivileges));
                }
                serviceProxy.saveBatchIfNotNull(thirdLevelPrivileges);
            }
        }

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
