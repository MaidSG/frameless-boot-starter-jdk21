package io.github.maidsg.starter.voyage.config;

import io.github.maidsg.starter.voyage.model.settings.BootStarterProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.util.RandomUtil;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/*******************************************************************
 * <pre></pre>
 * @文件名称： OpenApiConfig.java
 * @包 路  径： io.github.maidsg.starter.voyage.config
 * @Copyright：wy (C) 2024 *
 * @Description:
 * @Version: V1.0
 * @Author： wy
 * @Date： 2024/6/12 22:31
 * @Modify：
 */
@EnableConfigurationProperties(value = {BootStarterProperties.OpenApiProperties.class})
@Configuration
public class OpenApiConfig {

    @Autowired
    private BootStarterProperties.OpenApiProperties openApiProperties;


    @Bean
    public OpenAPI openApi() {
        Info info = new Info();
        Contact contact = new Contact();
        if (openApiProperties != null) {
            if (StrUtil.isNotEmpty(openApiProperties.getTitle())) {
                info.setTitle(openApiProperties.getTitle());
            }
            if (StrUtil.isNotEmpty(openApiProperties.getDescription())) {
                info.setTitle(openApiProperties.getDescription());
            }

            if (openApiProperties.getContact() != null) {

                if (StrUtil.isNotEmpty(openApiProperties.getContact().getName())) {
                    contact.setName(openApiProperties.getContact().getName());
                }
                if (StrUtil.isNotEmpty(openApiProperties.getContact().getEmail())) {
                    contact.setEmail(openApiProperties.getContact().getEmail());
                }
                if (StrUtil.isNotEmpty(openApiProperties.getContact().getUrl())) {
                    contact.setUrl(openApiProperties.getContact().getUrl());
                }
            }
        }
        return new OpenAPI()
                .info(info
                        .contact(contact)
                        .version(openApiProperties.getVersion()));
    }

    @Bean
    @ConditionalOnMissingBean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("rest")
                .packagesToScan("io.github.maidsg.sp3_knif_4j.demo.controller")
                .build();
    }

    /**
     * 根据@Tag 上的排序，写入x-order
     *
     * @return the global open api customizer
     */
    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getTags()!=null){
                openApi.getTags().forEach(tag -> {
                    Map<String,Object> map=new HashMap<>();
                    map.put("x-order", RandomUtil.randomInt(0,100));
                    tag.setExtensions(map);
                });
            }
            if(openApi.getPaths()!=null){
                openApi.addExtension("x-test123","333");
                openApi.getPaths().addExtension("x-abb",RandomUtil.randomInt(1,100));
            }

        };
    }
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("赛博助手系统API")
//                        .version("1.0")
//                        .description( "赛博助手后台管理系统API")
//                        .termsOfService("http://doc.com")
//                        .license(new License().name("Apache 2.0")
//                                .url("http://doc.com")));
//    }

}
