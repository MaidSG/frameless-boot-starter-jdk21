package io.github.maidsg.starter.voyage.annotation;

import java.lang.annotation.*;

/*******************************************************************
 * <pre></pre>
 * @文件名称： ApiVersion.java
 * @包 路  径： io.github.maidsg.starter.voyage.annotation
 * @Copyright：wy (C) 2024 *
 * @Description: 版本控制注解
 * @Version: V1.0
 * @Author： wy
 * @Date： 2024/6/21 17:36
 * @Modify：
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {
    /**
     * @return 版本号
     */
    String value() default "1.0.0";
}
