package io.github.thebesteric.project.intelligent.core.constant;

import cn.hutool.core.comparator.VersionComparator;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public enum PrivilegeCode {

    CUSTOMER_MANAGEMENT("客户管理", "customer:management", "1", PrivilegeType.CATALOG, null),

    CUSTOMER_DATUM("客户资料", "customer:datum", "1.1", PrivilegeType.CATALOG, PrivilegeCode.CUSTOMER_MANAGEMENT),
    CUSTOMER_DATUM_LIST("客户列表", "customer:datum:list", "1.1.1", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_DATUM),
    CUSTOMER_DATUM_TYPE("客户类型", "customer:datum:type", "1.1.2", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_DATUM),
    CUSTOMER_DATUM_AUDIT("客户审核", "customer:datum:audit", "1.1.3", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_DATUM),
    CUSTOMER_DATUM_RELATION("客勤预警", "customer:datum:relation", "1.1.4", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_DATUM),
    CUSTOMER_DATUM_TAG("客户标签", "customer:datum:tag", "1.1.5", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_DATUM),
    CUSTOMER_DATUM_REGION("客户标签", "customer:datum:region", "1.1.6", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_DATUM),
    CUSTOMER_DATUM_LOCATED("客户分布", "customer:datum:located", "1.1.7", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_DATUM),

    CUSTOMER_LIFECYCLE("生命周期", "customer:lifecycle", "1.2", PrivilegeType.CATALOG, PrivilegeCode.CUSTOMER_MANAGEMENT),
    CUSTOMER_LIFECYCLE_RENEWAL("客户拉新", "customer:lifecycle:renewal", "1.2.1", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_LIFECYCLE),
    CUSTOMER_LIFECYCLE_ACTIVATE("客户激活", "customer:lifecycle:activate", "1.2.2", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_LIFECYCLE),
    CUSTOMER_LIFECYCLE_REMAIN("客户留存", "customer:lifecycle:remain", "1.2.3", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_LIFECYCLE),
    CUSTOMER_LIFECYCLE_INCREASE("增长分析", "customer:lifecycle:increase", "1.2.4", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_LIFECYCLE),

    CUSTOMER_FINANCE("财务管理", "customer:finance", "1.3", PrivilegeType.CATALOG, PrivilegeCode.CUSTOMER_MANAGEMENT),
    CUSTOMER_FINANCE_SCORE("积分查询", "customer:finance:score", "1.3.1", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_FINANCE),
    CUSTOMER_FINANCE_SETTING("积分设置", "customer:finance:setting", "1.3.2", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_FINANCE),
    CUSTOMER_FINANCE_PREPAID("预存款", "customer:finance:prepaid", "1.3.3", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_FINANCE),
    CUSTOMER_FINANCE_AUTH("授信管理", "customer:finance:auth", "1.3.4", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_FINANCE),

    CUSTOMER_SETTING("相关设置", "customer:setting", "1.4", PrivilegeType.CATALOG, PrivilegeCode.CUSTOMER_MANAGEMENT),
    CUSTOMER_SETTING_CUSTOMER("客户设置","customer:setting:customer", "1.4.1",  PrivilegeType.MENU, PrivilegeCode.CUSTOMER_SETTING),
    CUSTOMER_SETTING_ACCOUNT("账号管理","customer:setting:account", "1.4.2",  PrivilegeType.MENU, PrivilegeCode.CUSTOMER_SETTING),
    CUSTOMER_SETTING_PRIVACY("隐私协议","customer:setting:privacy", "1.4.3",  PrivilegeType.MENU, PrivilegeCode.CUSTOMER_SETTING),
    CUSTOMER_SETTING_SERVICE("服务协议", "customer:setting:service", "1.4.4", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_SETTING),

    ;

    private final String name;
    @EnumValue
    @JsonValue
    private final String code;
    private final String seq;
    private final PrivilegeType type;
    private final PrivilegeCode parent;


    public static PrivilegeCode of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equals(code)).findFirst().orElse(null);
    }

    public static List<PrivilegeCode> list() {
        Map<String, PrivilegeCode> sortMap = new TreeMap<>(new VersionComparator());
        Arrays.stream(values()).forEach(i -> sortMap.put(i.seq, i));
        return new ArrayList<>(sortMap.values());
    }

}
