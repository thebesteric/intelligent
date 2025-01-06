package io.github.thebesteric.project.intelligent.module.stock;

import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 库存服务
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 13:50:10
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(ApplicationConstants.MAPPER_PACKAGE_PATH)
@ComponentScan(ApplicationConstants.COMPONENT_PACKAGE_PATH)
@EnableFeignClients(ApplicationConstants.COMPONENT_PACKAGE_PATH)
@ConfigurationPropertiesScan(ApplicationConstants.COMPONENT_PACKAGE_PATH)
@EnableAspectJAutoProxy(exposeProxy = true)
public class ModuleStockApp {
    public static void main(String[] args) {
        SpringApplication.run(ModuleStockApp.class, args);
    }
}
