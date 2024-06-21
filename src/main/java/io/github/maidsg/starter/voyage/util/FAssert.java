package io.github.maidsg.starter.voyage.util;

import io.github.maidsg.starter.voyage.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/*******************************************************************
 * <pre></pre>
 * @文件名称： FAssert.java
 * @包 路  径： io.github.maidsg.starter.voyage.util
 * @Copyright：wy (C) 2024 *
 * @Description:
 * @Version: V1.0
 * @Author： wy
 * @Date： 2024/6/14 17:05
 * @Modify：
 */
public class FAssert {

    /**
     * 校验是否为空
     *
     * @param o 对象
     * @param e 异常
     */
    public static <T> T notNull(T o, BusinessException e) throws BusinessException {
        isTrue(o != null, e);
        return o;
    }

    public static <T> T notNullWithException(T o, Exception e) throws Exception {
        isTrueWithException(o != null, e);
        return o;
    }


    /**
     * 校验是否为空
     *
     * @param o 对象
     * @param msg 提示信息
     */
    public static <T> T notNull(T o, String msg) {
        isTrue(o != null, msg);
        return o;
    }

    /**
     * 是否为真
     *
     * @param b 是否为真
     * @param msg 提示信息
     */
    public static void isTrue(boolean b, String msg) {
        if (!b) {
            throw newCodeException(msg);
        }
    }


    /**
     * 是否为真
     *
     * @param b 是否为真
     * @param e 异常
     */
    public static void isTrue(boolean b, BusinessException e) throws BusinessException {
        if (!b) {
            throw e;
        }
    }

    /**
     * 是否为真 抛出自定义异常
     * @param b 是否为真
     * @param e 异常
     * @throws Throwable
     */
    public static void isTrueWithException(boolean b, Exception e) throws Exception {
        if (!b) {
            throw e;
        }
    }

    /**
     * 校验是否为空
     *
     * @param s 字符串
     * @param msg 提示信息
     */
    public static void notBlank(String s, String msg) {
        isTrue(StringUtils.isNotBlank(s), msg);
    }



    /**
     * 校验是否为空
     *
     * @param s 字符串
     * @param ie 异常
     */
    public static void notBlank(String s, BusinessException ie) throws BusinessException {
        isTrue(StringUtils.isNotBlank(s), ie);
    }

    /**
     * 校验是否为空
     *
     * @param cols 集合
     * @param msg 提示信息
     */
    public static <T> void notEmpty(Collection<T> cols, String msg) {
        isTrue(cols != null && !cols.isEmpty(), msg);
    }


    /**
     * 校验是否为空
     *
     * @param cols 集合
     * @param ie 异常
     */
    public static <T> void notEmpty(Collection<T> cols, BusinessException ie) throws BusinessException {
        isTrue(cols != null && !cols.isEmpty(), ie);
    }

    private static BusinessException newCodeException(String msg) {
        return new BusinessException(msg);
    }



}
