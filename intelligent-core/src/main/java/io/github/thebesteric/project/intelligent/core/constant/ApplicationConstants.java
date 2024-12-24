package io.github.thebesteric.project.intelligent.core.constant;

import io.github.thebesteric.framework.agile.commons.util.AbstractUtils;

/**
 * ApplicationConstants
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-25 21:02:33
 */
public final class ApplicationConstants extends AbstractUtils {

    /** 全局扫描路径 */
    public static final String COMPONENT_PACKAGE_PATH = "io.github.thebesteric.project.intelligent";

    /** MAPPER 文件扫描路径 */
    public static final String MAPPER_PACKAGE_PATH = COMPONENT_PACKAGE_PATH + ".**.mapper";

    /** 系统租户 ID */
    public static final String SYSTEM_TENANT_ID = "0";

    /** 主数据源（默认数据源） */
    public static final String DATASOURCE_MASTER = "master";

    /** 系统应用 */
    public static final class Application extends AbstractUtils {

        /** 服务 */
        public static final class Server extends AbstractUtils {
            /** 认证服务 */
            public static final class OAuth extends AbstractUtils {
                /** intelligent-auth-server 库 */
                public static final String DATASOURCE_INTELLIGENT_AUTH_SERVER = "intelligent-oauth-server";
            }

            /** 网关服务 */
            public static final class Gateway extends AbstractUtils {

            }

            /** 核心服务 */
            public static final class CoreApi extends AbstractUtils {
                /** 数据库 */
                public static final String DATASOURCE_INTELLIGENT_CORE_API = "intelligent-core-api";
                public static final String TABLE_NAME_PREFIX = "t_core_";
            }
        }

        /** 模块 */
        public static final class Module extends AbstractUtils {

            /** 客户管理 */
            public static final class CRM extends AbstractUtils {
                /** 数据库 */
                public static final String DATASOURCE_INTELLIGENT_MODULE_CRM = "intelligent-module-crm";
                /** 表前缀 */
                public static final String TABLE_NAME_PREFIX = "t_crm_";
            }

            /** 商品管理 */
            public static final class Product extends AbstractUtils {
                /** 数据库 */
                public static final String DATASOURCE_INTELLIGENT_MODULE_PRODUCT = "intelligent-module-product";
                /** 表前缀 */
                public static final String TABLE_NAME_PREFIX = "t_product_";
            }

            /** 库存管理 */
            public static final class Stock extends AbstractUtils {
                /** 数据库 */
                public static final String DATASOURCE_INTELLIGENT_MODULE_STOCK = "intelligent-module-stock";
                /** 表前缀 */
                public static final String TABLE_NAME_PREFIX = "t_stock_";
            }
        }

    }


}
