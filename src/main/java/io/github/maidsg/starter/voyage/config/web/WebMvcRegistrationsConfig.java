package io.github.maidsg.starter.voyage.config.web;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/*******************************************************************
 * <pre></pre>
 * @文件名称： WebMvcRegistrationsConfig.java
 * @包 路  径： io.github.maidsg.starter.voyage.config.web
 * @Copyright：wy (C) 2024 *
 * @Description:
 * @Version: V1.0
 * @Author： wy
 * @Date： 2024/6/21 17:48
 * @Modify：
 */
@Configuration
@Slf4j
@ConditionalOnProperty(prefix = "frameless.api", name = "enableVersion", havingValue = "true")
public class WebMvcRegistrationsConfig implements WebMvcRegistrations {


    @PostConstruct
    public void init() {
        log.info("=====> WebMvcRegistrationsConfig init api版本控制");
    }

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiRequestMappingHandlerMapping();
    }

}
