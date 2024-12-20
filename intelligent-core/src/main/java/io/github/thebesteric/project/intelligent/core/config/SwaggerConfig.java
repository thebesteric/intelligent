package io.github.thebesteric.project.intelligent.core.config;

import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jSetting;
import com.github.xiaoymin.knife4j.spring.extension.Knife4jOpenApiCustomizer;
import com.github.xiaoymin.knife4j.spring.filter.JakartaProductionSecurityFilter;
import io.github.thebesteric.project.intelligent.core.properties.ApplicationProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 配置类
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-29 00:47:09
 */
@Configuration
@EnableConfigurationProperties({Knife4jProperties.class, ApplicationProperties.class})
@Slf4j
public class SwaggerConfig {

    @Value("${spring.application.name:}")
    private String applicationName;

    @Value("${spring.profiles.active:dev}")
    private String env;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "application.components.swagger", name = "enable", havingValue = "true")
    public OpenAPI customOpenAPI(ApplicationProperties applicationProperties) {
        ApplicationProperties.Components.SwaggerComponent swagger = applicationProperties.getComponents().getSwagger();

        // 联系方式
        ApplicationProperties.Components.SwaggerComponent.Contact contactProp = swagger.getContact();
        Contact contact = new Contact()
                .name(applicationName)
                .email(contactProp.getEmail())
                .url(contactProp.getUrl());

        // 展示信息
        ApplicationProperties.Components.SwaggerComponent.Info infoProp = swagger.getInfo();
        Info info = new Info()
                .contact(contact)
                .title(infoProp.getTitle())
                .version(infoProp.getVersion())
                .termsOfService(infoProp.getTermsOfService());
        if (infoProp.getDescription() != null) {
            info.description(infoProp.getDescription());
        } else {
            info.description(applicationName + " 服务 API 接口文档");
        }

        // 协议信息
        ApplicationProperties.Components.SwaggerComponent.License licenseProp = infoProp.getLicense();
        License license;
        if (licenseProp != null) {
            license = new License().name(licenseProp.getName()).url(licenseProp.getUrl());
        } else {
            license = new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html");
        }
        info.license(license);

        return new OpenAPI().info(info);
    }

    @Bean
    public Knife4jOpenApiCustomizer knife4jOpenApiCustomizer(ApplicationProperties applicationProperties,
                                                             Knife4jProperties knife4jProperties,
                                                             SpringDocConfigProperties docProperties) {
        ApplicationProperties.Components.SwaggerComponent swagger = applicationProperties.getComponents().getSwagger();
        knife4jProperties.setEnable(swagger.isEnable());
        Boolean production = swagger.getProduction();
        knife4jProperties.setProduction(production == null ? "prod".equals(env) : production);
        Knife4jSetting setting = knife4jProperties.getSetting();
        if (setting == null) {
            setting = new Knife4jSetting();
            setting.setSwaggerModelName("模型");
            knife4jProperties.setSetting(setting);
        }
        return new Knife4jOpenApiCustomizer(knife4jProperties, docProperties);
    }

    @Bean
    @ConditionalOnMissingBean({JakartaProductionSecurityFilter.class})
    public JakartaProductionSecurityFilter productionSecurityFilter(ApplicationProperties applicationProperties) {
        boolean prod = false;
        ApplicationProperties.Components.SwaggerComponent swagger = applicationProperties.getComponents().getSwagger();
        JakartaProductionSecurityFilter p;
        if (swagger.getProduction() == null) {
            if ("prod".equals(env)) {
                if (log.isDebugEnabled()) {
                    log.debug("swagger.production:{}", env);
                }
                prod = true;
            }
            p = new JakartaProductionSecurityFilter(prod);
        } else {
            p = new JakartaProductionSecurityFilter(swagger.getProduction());
        }
        swagger.setProduction(prod);
        return p;
    }


}
