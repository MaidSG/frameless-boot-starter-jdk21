package io.github.maidsg.starter.voyage.config.web;

import io.github.maidsg.starter.voyage.constant.ApiVersionConstant;
import io.github.maidsg.starter.voyage.model.base.ApiObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNullApi;
import org.springframework.util.ObjectUtils;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*******************************************************************
 * <pre></pre>
 * @文件名称： ApiVersionCondition.java
 * @包 路  径： io.github.maidsg.starter.voyage.config.web
 * @Copyright：wy (C) 2024 *
 * @Description:
 * @Version: V1.0
 * @Author： wy
 * @Date： 2024/6/21 17:41
 * @Modify：
 */
@Slf4j
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    public static ApiVersionCondition empty = new ApiVersionCondition(ApiObject.ApiConverter.convert(ApiVersionConstant.DEFAULT_VERSION));

    private  ApiObject apiVersion;

    private boolean NULL;

    ApiVersionCondition(ApiObject apiVersion) {
        this.apiVersion = apiVersion;
    }

    public ApiVersionCondition(ApiObject item, boolean NULL) {
        this.apiVersion = item;
        this.NULL = NULL;
    }


    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        // 选择版本最大的接口
        if (other.NULL) {
            return this;
        }
        return other;
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return empty;
        }
        String version = request.getHeader(ApiVersionConstant.API_VERSION);
        // 获取所有小于等于版本的接口;如果前端不指定版本号，则默认请求1.0.0版本的接口
        if (ObjectUtils.isEmpty(version)) {
            version = ApiVersionConstant.DEFAULT_VERSION;
        }
        ApiObject item = ApiObject.ApiConverter.convert(version);
        if (item.compareTo(ApiObject.API_ITEM_DEFAULT) < 0) {
            throw new IllegalArgumentException(String.format("API版本[%s]错误，最低版本[%s]", version, ApiVersionConstant.DEFAULT_VERSION));
        }
        if (item.compareTo(this.apiVersion) >= 0) {
            return this;
        }
        return null;
    }

    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        // 获取到多个符合条件的接口后，会按照这个排序，然后get(0)获取最大版本对应的接口.自定义条件会最后比较
        int compare = other.apiVersion.compareTo(this.apiVersion);
        if (compare == 0) {
            log.warn("RequestMappingInfo相同，请检查！version:{}", other.apiVersion);
        }
        return compare;


    }
}
