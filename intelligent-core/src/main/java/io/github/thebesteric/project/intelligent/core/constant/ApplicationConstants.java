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
    public static final String MAPPER_PACKAGE_PATH = COMPONENT_PACKAGE_PATH + ".**.mapper";

    /** 系统租户 ID */
    public static final String SYSTEM_TENANT_ID = "0";

    /** 数据源 */
    public static final class DataSource extends AbstractUtils {
        /** 主数据源（默认数据源） */
        public static final String MASTER = "master";

        /** intelligent-auth-server 库 */
        public static final String INTELLIGENT_AUTH_SERVER = "intelligent-oauth-server";

        /** intelligent-core-api 库 */
        public static final String INTELLIGENT_CORE_API = "intelligent-core-api";

        /** 对应 intelligent-module-crm 库 */
        public static final String INTELLIGENT_MODULE_CRM = "intelligent-module-crm";

        /** 对应 intelligent-module-crm 库 */
        public static final String INTELLIGENT_MODULE_PRODUCT = "intelligent-module-product";
    }

    /** 系统应用 */
    public static final class Application extends AbstractUtils {

        /** 服务 */
        public static final class Server extends AbstractUtils {
            /** 认证服务 */
            public static final class OAuth extends AbstractUtils {

            }

            /** 网关服务 */
            public static final class Gateway extends AbstractUtils {

            }

            /** 核心服务 */
            public static final class CoreApi extends AbstractUtils {
                public static final String TABLE_NAME_PREFIX = "t_core_";
            }
        }

        /** 模块 */
        public static final class Module extends AbstractUtils {

            /** 客户管理 */
            public static final class CRM extends AbstractUtils {
                public static final String TABLE_NAME_PREFIX = "t_crm_";
            }

            /** 商品管理 */
            public static final class Product extends AbstractUtils {
                public static final String TABLE_NAME_PREFIX = "t_product_";
            }
        }

    }


}
