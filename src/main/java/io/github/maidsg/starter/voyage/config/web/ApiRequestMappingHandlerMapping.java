package io.github.maidsg.starter.voyage.config.web;

import io.github.maidsg.starter.voyage.annotation.ApiVersion;
import io.github.maidsg.starter.voyage.constant.ApiVersionConstant;
import io.github.maidsg.starter.voyage.model.base.ApiObject;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/*******************************************************************
 * <pre></pre>
 * @文件名称： ApiRequestMappingHandlerMapping.java
 * @包 路  径： io.github.maidsg.starter.voyage.config.web
 * @Copyright：wy (C) 2024 *
 * @Description:
 * @Version: V1.0
 * @Author： wy
 * @Date： 2024/6/21 17:46
 * @Modify：
 */
public class ApiRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    private static final String VERSION_FLAG = "{version}";

    @Override
    protected RequestCondition<ApiVersionCondition> getCustomMethodCondition(Method method) {
        return buildFrom(AnnotationUtils.findAnnotation(method, ApiVersion.class));
    }

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        return buildFrom(AnnotationUtils.findAnnotation(handlerType, ApiVersion.class));
    }



    private ApiVersionCondition buildFrom(ApiVersion platform) {
        return platform == null ? getDefaultCondition() :
                new ApiVersionCondition(ApiObject.ApiConverter.convert(platform.value()));
    }

    private ApiVersionCondition getDefaultCondition(){
        return new ApiVersionCondition(ApiObject.ApiConverter.convert(ApiVersionConstant.DEFAULT_VERSION),true);
    }

}
