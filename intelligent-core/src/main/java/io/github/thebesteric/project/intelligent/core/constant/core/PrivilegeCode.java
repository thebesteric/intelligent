package io.github.thebesteric.project.intelligent.core.constant.core;

import cn.hutool.core.comparator.VersionComparator;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public enum PrivilegeCode {

    // ======================= 客户管理 =======================

    CUSTOMER_MANAGEMENT("客户管理", "customer:management", "1", PrivilegeType.CATALOG, null),

    CUSTOMER_DATUM("客户资料", "customer:management:datum", "1.1", PrivilegeType.CATALOG, PrivilegeCode.CUSTOMER_MANAGEMENT),
    CUSTOMER_DATUM_LIST("客户列表", "customer:management:datum:list", "1.1.1", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_DATUM),
    CUSTOMER_DATUM_TYPE("客户类型", "customer:management:datum:type", "1.1.2", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_DATUM),
    CUSTOMER_DATUM_AUDIT("客户审核", "customer:management:datum:audit", "1.1.3", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_DATUM),
    CUSTOMER_DATUM_RELATION("客勤预警", "customer:management:datum:relation-alarm", "1.1.4", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_DATUM),
    CUSTOMER_DATUM_TAG("客户标签", "customer:management:datum:tag", "1.1.5", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_DATUM),
    CUSTOMER_DATUM_REGION("客户区域", "customer:management:datum:region", "1.1.6", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_DATUM),
    CUSTOMER_DATUM_LOCATED("客户分布", "customer:management:datum:located", "1.1.7", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_DATUM),
    CUSTOMER_DATUM_LEVEL("客户等级", "customer:management:datum:level", "1.1.8", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_DATUM),

    CUSTOMER_LIFECYCLE("生命周期", "customer:management:lifecycle", "1.2", PrivilegeType.CATALOG, PrivilegeCode.CUSTOMER_MANAGEMENT),
    CUSTOMER_LIFECYCLE_RENEWAL("客户拉新", "customer:management:lifecycle:renewal", "1.2.1", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_LIFECYCLE),
    CUSTOMER_LIFECYCLE_ACTIVATE("客户激活", "customer:management:lifecycle:activate", "1.2.2", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_LIFECYCLE),
    CUSTOMER_LIFECYCLE_REMAIN("客户留存", "customer:management:lifecycle:remain", "1.2.3", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_LIFECYCLE),
    CUSTOMER_LIFECYCLE_INCREASE("增长分析", "customer:management:lifecycle:increase", "1.2.4", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_LIFECYCLE),

    CUSTOMER_FINANCE("财务管理", "customer:management:finance", "1.3", PrivilegeType.CATALOG, PrivilegeCode.CUSTOMER_MANAGEMENT),
    CUSTOMER_FINANCE_SCORE("积分查询", "customer:management:finance:score", "1.3.1", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_FINANCE),
    CUSTOMER_FINANCE_SETTINGS("积分设置", "customer:management:finance:settings", "1.3.2", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_FINANCE),
    CUSTOMER_FINANCE_PREPAID("预存款", "customer:management:finance:prepaid", "1.3.3", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_FINANCE),
    CUSTOMER_FINANCE_AUTH("授信管理", "customer:management:finance:auth", "1.3.4", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_FINANCE),

    CUSTOMER_SETTINGS("相关设置", "customer:management:settings", "1.4", PrivilegeType.CATALOG, PrivilegeCode.CUSTOMER_MANAGEMENT),
    CUSTOMER_SETTINGS_CUSTOMER("客户设置", "customer:management:settings:customer", "1.4.1", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_SETTINGS),
    CUSTOMER_SETTINGS_ACCOUNT("账号管理", "customer:management:settings:account", "1.4.2", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_SETTINGS),
    CUSTOMER_SETTINGS_PRIVACY("隐私协议", "customer:management:settings:privacy", "1.4.3", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_SETTINGS),
    CUSTOMER_SETTINGS_SERVICE("服务协议", "customer:management:settings:service", "1.4.4", PrivilegeType.MENU, PrivilegeCode.CUSTOMER_SETTINGS),

    // ======================= 商品管理 =======================

    PRODUCT_MANAGEMENT("商品管理", "product:management", "2", PrivilegeType.CATALOG, null),

    PRODUCT_CONTROL("商品管理", "product:management:control", "2.1", PrivilegeType.CATALOG, PrivilegeCode.PRODUCT_MANAGEMENT),
    PRODUCT_CONTROL_LIST("商品列表", "product:management:control:list", "2.1.1", PrivilegeType.MENU, PrivilegeCode.PRODUCT_CONTROL),
    PRODUCT_CONTROL_ADD("添加商品", "product:management:control:add", "2.1。2", PrivilegeType.MENU, PrivilegeCode.PRODUCT_CONTROL),
    PRODUCT_CONTROL_PRICE("价格管理", "product:management:control:price", "2.1。3", PrivilegeType.MENU, PrivilegeCode.PRODUCT_CONTROL),
    PRODUCT_CONTROL_MERGE("合并展示", "product:management:control:merge", "2.1。4", PrivilegeType.MENU, PrivilegeCode.PRODUCT_CONTROL),

    PRODUCT_ATTRS("商品属性", "product:management:attrs", "2.2", PrivilegeType.CATALOG, PrivilegeCode.PRODUCT_MANAGEMENT),
    PRODUCT_ATTRS_CATALOG("目录", "product:management:attrs:catalog", "2.2.1", PrivilegeType.MENU, PrivilegeCode.PRODUCT_ATTRS),
    PRODUCT_ATTRS_SPEC("规格", "product:management:attrs:spec", "2.2.2", PrivilegeType.MENU, PrivilegeCode.PRODUCT_ATTRS),
    PRODUCT_ATTRS_BRAND("品牌", "product:management:attrs:brand", "2.2.3", PrivilegeType.MENU, PrivilegeCode.PRODUCT_ATTRS),
    PRODUCT_ATTRS_TYPE("类型", "product:management:attrs:type", "2.2.4", PrivilegeType.MENU, PrivilegeCode.PRODUCT_ATTRS),
    PRODUCT_ATTRS_TAG("标签", "product:management:attrs:tag", "2.2.5", PrivilegeType.MENU, PrivilegeCode.PRODUCT_ATTRS),
    PRODUCT_ATTRS_CUSTOM("自定义字段", "product:management:attrs:custom", "2.2.6", PrivilegeType.MENU, PrivilegeCode.PRODUCT_ATTRS),

    PRODUCT_GIFT("赠品管理", "product:management:gift", "2.3", PrivilegeType.CATALOG, PrivilegeCode.PRODUCT_MANAGEMENT),
    PRODUCT_GIFT_LIST("赠品列表", "product:management:gift:list", "2.3.1", PrivilegeType.MENU, PrivilegeCode.PRODUCT_GIFT),
    PRODUCT_GIFT_LACK("缺货登记", "product:management:gift:lack", "2.3.2", PrivilegeType.MENU, PrivilegeCode.PRODUCT_GIFT),
    PRODUCT_GIFT_COMMENT("缺货登记", "product:management:gift:comment", "2.3.3", PrivilegeType.MENU, PrivilegeCode.PRODUCT_GIFT),

    // ======================= 库存管理 =======================

    STOCK_MANAGEMENT("库存管理", "stock:management", "3", PrivilegeType.CATALOG, null),

    STOCK_BILLS("单据", "stock:management:bills", "3.1", PrivilegeType.CATALOG, PrivilegeCode.STOCK_MANAGEMENT),
    STOCK_BILLS_INBOUND("其他入库单", "stock:management:bills:inbound", "3.1.1", PrivilegeType.MENU, PrivilegeCode.STOCK_BILLS),
    STOCK_BILLS_OUTBOUND("其他出库单", "stock:management:bills:outbound", "3.1.2", PrivilegeType.MENU, PrivilegeCode.STOCK_BILLS),
    STOCK_BILLS_TRANSFER("调拨单", "stock:management:bills:transfer", "3.1.3", PrivilegeType.MENU, PrivilegeCode.STOCK_BILLS),

    STOCK_WAREHOUSE("仓库", "stock:management:warehouse", "3.2", PrivilegeType.CATALOG, PrivilegeCode.STOCK_MANAGEMENT),
    STOCK_WAREHOUSE_STATUS("库存状态", "stock:management:warehouse:status", "3.2.1", PrivilegeType.MENU, PrivilegeCode.STOCK_WAREHOUSE),
    STOCK_WAREHOUSE_DISTRIBUTION("库存分布", "stock:management:warehouse:distribution", "3.2.2", PrivilegeType.MENU, PrivilegeCode.STOCK_WAREHOUSE),
    STOCK_WAREHOUSE_WARNING("库存预警", "stock:management:warehouse:warning", "3.2.3", PrivilegeType.MENU, PrivilegeCode.STOCK_WAREHOUSE),
    STOCK_WAREHOUSE_INBOUND("入库单", "stock:management:warehouse:inbound", "3.2.4", PrivilegeType.MENU, PrivilegeCode.STOCK_WAREHOUSE),
    STOCK_WAREHOUSE_OUTBOUND("出库单", "stock:management:warehouse:outbound", "3.2.5", PrivilegeType.MENU, PrivilegeCode.STOCK_WAREHOUSE),
    STOCK_WAREHOUSE_MANAGEMENT("仓库管理", "stock:management:warehouse:management", "3.2.6", PrivilegeType.MENU, PrivilegeCode.STOCK_WAREHOUSE),

    STOCK_STOCKTAKING("盘点", "stock:management:stocktaking", "3.3", PrivilegeType.CATALOG, PrivilegeCode.STOCK_MANAGEMENT),
    STOCK_STOCKTAKING_BILLS("盘点单", "stock:management:stocktaking:bills", "3.3.1", PrivilegeType.MENU, PrivilegeCode.STOCK_STOCKTAKING),
    STOCK_STOCKTAKING_LOSE("报溢单", "stock:management:stocktaking:lose", "3.3.2", PrivilegeType.MENU, PrivilegeCode.STOCK_STOCKTAKING),
    STOCK_STOCKTAKING_GAIN("报损单", "stock:management:stocktaking:gain", "3.3.3", PrivilegeType.MENU, PrivilegeCode.STOCK_STOCKTAKING),

    // ======================= xx管理 =======================
    ;

    private final String name;
    @EnumValue
    @JsonValue
    private final String code;
    private final String seq;
    private final PrivilegeType type;
    private final PrivilegeCode parent;


    public static PrivilegeCode of(String code) {
        return Arrays.stream(values()).filter(i -> i.code.equalsIgnoreCase(code)).findFirst().orElse(null);
    }

    public static List<PrivilegeCode> list() {
        Map<String, PrivilegeCode> sortMap = new TreeMap<>(new VersionComparator());
        Arrays.stream(values()).forEach(i -> sortMap.put(i.seq, i));
        return new ArrayList<>(sortMap.values());
    }

    public static List<PrivilegeCode> listByParent(PrivilegeCode parent) {
        return list().stream().filter(i -> i.parent == parent).toList();
    }

}
