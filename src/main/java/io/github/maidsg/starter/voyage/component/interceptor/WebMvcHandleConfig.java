package io.github.maidsg.starter.voyage.component.interceptor;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*******************************************************************
 * <pre></pre>
 * @文件名称： WebMvcHandleConfig.java
 * @包 路  径： io.github.maidsg.starter.voyage.component.interceptor
 * @Copyright：wy (C) 2024 *
 * @Description:
 * @Version: V1.0
 * @Author： wy
 * @Date： 2024/6/12 22:27
 * @Modify：
 */
@Configuration
public class WebMvcHandleConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Bean
    public FastJsonConfig fastJsonConfig(){
        // 1. 自定义配置
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");

        // 2.1 配置序列化的行为
        config.setWriterFeatures(JSONWriter.Feature.PrettyFormat);

        // 2.2 配置反序列化的行为
        config.setReaderFeatures(JSONReader.Feature.FieldBased, JSONReader.Feature.SupportArrayToBean);

        return config;
    }

    /**
     * 使用 FastJsonHttpMessageConverter 来替换 Spring MVC 默认的 HttpMessageConverter
     * 以提高 @RestController @ResponseBody @RequestBody 注解的 JSON序列化和反序列化速度。
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 1. 转换器 使用 fastjson 序列化，会导致 @JsonIgnore 失效，可以使用 @JSONField(serialize = false) 替换
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> supportMediaTypeList = new ArrayList<>();
        supportMediaTypeList.add(MediaType.APPLICATION_JSON);
        supportMediaTypeList.add(MediaType.TEXT_PLAIN);
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setWriterFeatures(JSONWriter.Feature.WriteEnumsUsingName,JSONWriter.Feature.NotWriteRootClassName);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        converter.setFastJsonConfig(fastJsonConfig);
        converter.setSupportedMediaTypes(supportMediaTypeList);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        // 解决OpenAPI 返回数组时，swagger-ui.html 无法解析的问题
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(converter);
    }

}
