package io.github.maidsg.starter.voyage.model.settings;

import io.github.maidsg.starter.voyage.constant.StarterConstant;
import io.github.maidsg.starter.voyage.enums.RedissonConnectionTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/*******************************************************************
 * <pre></pre>
 * @文件名称： BootStarterProperties.java
 * @包 路  径： io.github.maidsg.framelessbootstarter.model.settings
 * @Copyright：wy (C) 2024 *
 * @Description:
 * @Version: V1.0
 * @Author： wy
 * @Date：2024/3/1 14:54
 * @Modify：
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "frameless.api")
public class BootStarterProperties {

    private boolean enabled = Boolean.FALSE;

    private String success = "success";

    private String code = "code";

    private String codeSuccessValue = "OK";

    private String msg = "msg";

    private String timestamp = "timestamp";

    private String data = "data";

    /**
     * logback配置
     * 使用@Configuration 创建bean，同时在BeanAutoConfiguration中也创建的原因是
     *
     */
    @ConfigurationProperties(prefix = "frameless.logback")
    @Configuration
    @Data
    public static class LogBackProperties{
        // 日志路径
        private String logPath ;
        // 日志级别
        private String globalLevel;
        // 日志数据源
        private String fileDataSource = StarterConstant.FILE_SOURCE_PATH;
        // 最大保存天数
        private String maxMaintainDays;
        // 单个文件大小
        private String maxFileSize;

        // 总文件大小超过多少时压缩
        private String totalSizeCap;


    }

    /**
     * redisson配置
     * 不使用@Configuration 创建bean，因为在starter中，会导致redisson的bean被重复创建
     * 在BeanAutoConfiguration中已经创建了
     */
    @Data
    @ConfigurationProperties(prefix = "frameless.redisson")
    public static class RedissonProperties{

        private String address;
        private String port;
        private RedissonConnectionTypeEnum type = RedissonConnectionTypeEnum.SINGLE;
        private String password;
        private int database;
        private Boolean enabled = false;

    }

    @Data
    @ConfigurationProperties(prefix = "frameless.redis")
    public static class StarterRedisProperties{
        private Boolean enableCache = Boolean.FALSE;
        private Boolean enableMessage = Boolean.FALSE;
        // hours
        private Integer cacheExpireTime = 1;
    }


    @Configuration
    @ConfigurationProperties(prefix = "frameless.openapi")
    @Data
    public static class OpenApiProperties {
        /**
         * 是否开启swagger
         */
        private Boolean enabled = true;

        /**
         * 分组名称
         */
        private String groupName;

        /**
         * 文档版本，默认使用 2.0
         */
        private String documentationType = "v2.0";

        /**
         * swagger会解析的包路径
         **/
        private String basePackage = "";

        /**
         * swagger会解析的url规则
         **/
        private List<String> basePath = new ArrayList<>();

        /**
         * 在basePath基础上需要排除的url规则
         **/
        private List<String> excludePath = new ArrayList<>();

        /**
         * 标题
         **/
        private String title = "RESTFul";

        /**
         * 描述
         **/
        private String description = "SpringBoot Web Easy RESTFul API";

        /**
         * 版本
         **/
        private String version = "1.0.0";

        /**
         * 许可证
         **/
        private String license = "";

        /**
         * 许可证URL
         **/
        private String licenseUrl = "";

        /**
         * 服务条款URL
         **/
        private String termsOfServiceUrl = "";

        /**
         * host信息
         **/
        private String host = "https://127.0.0.1/";

        /**
         * 联系人信息
         */
        private Contact contact = new Contact();
    }

    @Data
    public static class Contact {

        /**
         * 联系人
         **/
        private String name = "developer";

        /**
         * 联系人url
         **/
        private String url = "https://github.com";

        /**
         * 联系人email
         **/
        private String email = "ifinetoo@qq.com";

    }




}
