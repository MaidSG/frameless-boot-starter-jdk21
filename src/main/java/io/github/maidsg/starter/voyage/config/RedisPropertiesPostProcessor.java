package io.github.maidsg.starter.voyage.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/*******************************************************************
 * <pre></pre>
 * @文件名称： RedisPropertiesPostProcessor.java
 * @包 路  径： io.github.maidsg.starter.voyage.config
 * @Copyright：wy (C) 2024 *
 * @Description:
 *  实现EnvironmentPostProcessor接口来在Spring Boot加载application.properties
 *  或application.yml之后，但在ApplicationContext创建之前修改环境属性。
 *  简单来说就是将原redis配置参数的值替换为自定义的配置参数
 * @Version: V1.0
 * @Author： wy
 * @Date： 2024/6/13 12:23
 * @Modify：
 */
@Configuration
public class RedisPropertiesPostProcessor implements EnvironmentPostProcessor {
    private static final String PROPERTY_SOURCE_NAME = "defaultProperties";
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> propertyMap = new HashMap<>();
        // 从你的自定义配置中获取属性
        String redisHost = environment.getProperty("frameless.redisson.address");
        String port = environment.getProperty("frameless.redisson.port");
        String redisDatabase = environment.getProperty("frameless.redisson.database");
        String redisPassword = environment.getProperty("frameless.redisson.password");

        // 将自定义配置的属性设置为Spring Data Redis需要的属性
        propertyMap.put("spring.data.redis.host", redisHost);
        propertyMap.put("spring.data.redis.port", port);
        propertyMap.put("spring.data.redis.database", redisDatabase);
        propertyMap.put("spring.data.redis.password", redisPassword);

        environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, propertyMap));
    }

}
