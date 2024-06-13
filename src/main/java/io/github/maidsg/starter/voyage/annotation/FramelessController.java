package io.github.maidsg.starter.voyage.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/*******************************************************************
 * <pre></pre>
 * @文件名称： FramelessController.java
 * @包 路  径： io.github.maidsg.starter.voyage.annotation
 * @Copyright：wy (C) 2024 *
 * @Description:
 *  使用该类注解的controller控制advice的扫描范围
 * @Version: V1.0
 * @Author： wy
 * @Date： 2024/6/13 14:50
 * @Modify：
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
public @interface FramelessController {
    // 该注解的value属性是RestController的value属性
    @AliasFor(annotation = RestController.class)
    String value() default "";
}
