package io.github.maidsg.starter.voyage.annotation;

import io.github.maidsg.starter.voyage.util.SpELAssertValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.*;

/*******************************************************************
 * <pre></pre>
 * @文件名称： SpELAssert.java
 * @包 路  径： io.github.maidsg.starter.start.annotation
 * @Copyright：wy (C) 2024 *
 * @Description:
 * @Version: V1.0
 * @Author： wy
 * @Date：2024/3/23 10:03
 * @Modify：
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SpELAssertValidator.class)
@Documented
public @interface SpELAssert {
    String message() default "{SpELAssert.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value();

}
