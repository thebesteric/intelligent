package io.github.thebesteric.project.intelligent.gateway;

import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = ApplicationConstants.COMPONENT_PACKAGE_PATH)
@ConfigurationPropertiesScan(basePackages = ApplicationConstants.COMPONENT_PACKAGE_PATH)
@EnableTransactionManagement
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }
}