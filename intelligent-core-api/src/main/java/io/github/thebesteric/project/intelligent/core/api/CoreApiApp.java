package io.github.thebesteric.project.intelligent.core.api;

import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * CoreApiApp
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-29 14:43:26
 */
@SpringBootApplication
@MapperScan(basePackages = ApplicationConstants.MAPPER_PACKAGE_PATH)
@ComponentScan(basePackages = ApplicationConstants.COMPONENT_PACKAGE_PATH)
@ConfigurationPropertiesScan(basePackages = ApplicationConstants.COMPONENT_PACKAGE_PATH)
public class CoreApiApp {
    public static void main(String[] args) {
        SpringApplication.run(CoreApiApp.class, args);
    }
}
