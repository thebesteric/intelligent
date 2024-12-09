package io.github.thebesteric.project.intelligent.core.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.api.generator.SimpleDictShortCodeGenerator;
import io.github.thebesteric.project.intelligent.core.api.mapper.PrivilegeMapper;
import io.github.thebesteric.project.intelligent.core.api.model.constant.PrivilegeType;
import io.github.thebesteric.project.intelligent.core.api.model.entity.Privilege;
import io.github.thebesteric.project.intelligent.core.api.service.PrivilegeService;
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
public class PrivilegeServiceImpl extends ServiceImpl<PrivilegeMapper, Privilege> implements PrivilegeService {

    private final SimpleDictShortCodeGenerator codeGenerator;

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

        Privilege p_1 = Privilege.of("客户管理", PrivilegeType.CATALOG, codeGenerator, existsPrivileges);
        this.saveIfNotNull(p_1);

        Privilege p_1_1 = Privilege.of(p_1, "客户资料", PrivilegeType.CATALOG, codeGenerator, existsPrivileges);
        this.saveIfNotNull(p_1_1);
        Privilege p_1_1_1 = Privilege.of(p_1_1, "客户列表", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        Privilege p_1_1_2 = Privilege.of(p_1_1, "客户类型", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        Privilege p_1_1_3 = Privilege.of(p_1_1, "客户审核", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        Privilege p_1_1_4 = Privilege.of(p_1_1, "客情预警", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        Privilege p_1_1_5 = Privilege.of(p_1_1, "客户标签", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        Privilege p_1_1_6 = Privilege.of(p_1_1, "客户区域", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        Privilege p_1_1_7 = Privilege.of(p_1_1, "客户分布", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        this.saveBatchIfNotNull(Arrays.asList(p_1_1_1, p_1_1_2, p_1_1_3, p_1_1_4, p_1_1_5, p_1_1_6, p_1_1_7));

        Privilege p_1_2 = Privilege.of(p_1,"生命周期", PrivilegeType.CATALOG, codeGenerator, existsPrivileges);
        this.saveIfNotNull(p_1_2);
        Privilege p_1_2_1 = Privilege.of(p_1_2, "客户拉新", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        Privilege p_1_2_2 = Privilege.of(p_1_2, "客户激活", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        Privilege p_1_2_3 = Privilege.of(p_1_2, "客户留存", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        Privilege p_1_2_4 = Privilege.of(p_1_2, "增长分析", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        this.saveBatchIfNotNull(Arrays.asList(p_1_2_1, p_1_2_2, p_1_2_3, p_1_2_4));

        Privilege p_1_3 = Privilege.of(p_1,"财务管理", PrivilegeType.CATALOG, codeGenerator, existsPrivileges);
        this.saveIfNotNull(p_1_3);
        Privilege p_1_3_1 = Privilege.of(p_1_3, "积分查询", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        Privilege p_1_3_2 = Privilege.of(p_1_3, "积分设置", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        Privilege p_1_3_3 = Privilege.of(p_1_3, "预存款", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        Privilege p_1_3_4 = Privilege.of(p_1_3, "授信管理", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        this.saveBatchIfNotNull(Arrays.asList(p_1_3_1, p_1_3_2, p_1_3_3, p_1_3_4));

        Privilege p_1_4 = Privilege.of(p_1,"相关设置", PrivilegeType.CATALOG, codeGenerator, existsPrivileges);
        this.saveIfNotNull(p_1_4);
        Privilege p_1_4_1 = Privilege.of(p_1_4, "客户设置", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        Privilege p_1_4_2 = Privilege.of(p_1_4, "账号管理", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        Privilege p_1_4_3 = Privilege.of(p_1_4, "隐私协议", PrivilegeType.MENU, codeGenerator, existsPrivileges);
        Privilege p_1_4_4 = Privilege.of(p_1_4, "服务协议", PrivilegeType.MENU, codeGenerator, existsPrivileges);
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
