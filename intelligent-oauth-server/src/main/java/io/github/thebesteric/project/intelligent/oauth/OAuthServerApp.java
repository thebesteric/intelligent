package io.github.thebesteric.project.intelligent.oauth;

import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = ApplicationConstants.COMPONENT_PACKAGE_PATH)
@ComponentScan(basePackages = ApplicationConstants.COMPONENT_PACKAGE_PATH)
@ConfigurationPropertiesScan(basePackages = ApplicationConstants.COMPONENT_PACKAGE_PATH)
public class OAuthServerApp {
    public static void main(String[] args) {
        SpringApplication.run(OAuthServerApp.class, args);
    }
}
