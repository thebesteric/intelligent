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
        /** 主数据源 */
        public static final String MASTER = "master";

        /** INTELLIGENT_AUTH_SERVER 数据源，对应 intelligent-auth-server 库 */
        public static final String INTELLIGENT_AUTH_SERVER = "intelligent-oauth-server";

        /** INTELLIGENT_CORE_API 数据源，对应 intelligent-core-api 库 */
        public static final String INTELLIGENT_CORE_API = "intelligent-core-api";

        /** INTELLIGENT_MODULE_CRM 数据源，对应 intelligent-module-crm 库 */
        public static final String INTELLIGENT_MODULE_CRM = "intelligent-module-crm";
    }

}
