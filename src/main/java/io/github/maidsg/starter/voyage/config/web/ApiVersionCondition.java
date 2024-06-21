package io.github.maidsg.starter.voyage.config.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNullApi;
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

public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile(".*v(\\d+).*");

    private final int apiVersion;

    ApiVersionCondition(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    private int getApiVersion() {
        return apiVersion;
    }


    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        return new ApiVersionCondition(other.getApiVersion());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        Matcher m = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());
        if (m.find()) {
            int version = Integer.parseInt(m.group(1));
            if (version >= this.apiVersion) {
                return this;
            }
        }
        return null;
    }

    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        // 方法级别和类级别都有ApiVersion注解时，二者将进行合并
        return other.getApiVersion() - this.apiVersion;
    }
}
