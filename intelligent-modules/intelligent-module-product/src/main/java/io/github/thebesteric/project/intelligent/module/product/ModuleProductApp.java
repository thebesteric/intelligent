package io.github.thebesteric.project.intelligent.module.product;

import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 商品服务
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 13:54:42
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(ApplicationConstants.MAPPER_PACKAGE_PATH)
@ComponentScan(ApplicationConstants.COMPONENT_PACKAGE_PATH)
@ConfigurationPropertiesScan(ApplicationConstants.COMPONENT_PACKAGE_PATH)
@EnableAspectJAutoProxy(exposeProxy = true)
public class ModuleProductApp {
    public static void main(String[] args) {
        SpringApplication.run(ModuleProductApp.class, args);
    }
}
