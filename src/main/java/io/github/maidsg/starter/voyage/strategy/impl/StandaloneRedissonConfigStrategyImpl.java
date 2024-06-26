package io.github.maidsg.starter.voyage.strategy.impl;

import com.alibaba.fastjson2.JSONFactory;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.redission.JSONCodec;
import io.github.maidsg.starter.voyage.component.serializers.RedissonFastJsonCodec;
import io.github.maidsg.starter.voyage.constant.RedisConstant;
import io.github.maidsg.starter.voyage.model.settings.BootStarterProperties;
import io.github.maidsg.starter.voyage.strategy.RedissonConfigStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;

/*******************************************************************
 * <pre></pre>
 * @文件名称： StandaloneRedissonConfigStrategyImpl.java
 * @包 路  径： io.github.maidsg.starter.redisson.strategy.impl
 * @Copyright：wy (C) 2024 *
 * @Description:
 * @Version: V1.0
 * @Author： wy
 * @Date：2024/3/21 17:03
 * @Modify：
 */
@Slf4j
public class StandaloneRedissonConfigStrategyImpl implements RedissonConfigStrategy {
    @Override
    public Config createRedissonConfig(BootStarterProperties.RedissonProperties redissonProperties) {

        Config config = new Config();
        try {
            String address = redissonProperties.getAddress() + ":" + redissonProperties.getPort();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String redisAddr = RedisConstant.REDIS_CONNECTION_PREFIX + address;
            config.useSingleServer().setAddress(redisAddr);
            config.useSingleServer().setDatabase(database);
            if (StringUtils.isNotBlank(password)) {
                config.useSingleServer().setPassword(password);
            }
//            config.setCodec(RedissonFastJsonCodec.INSTANCE);

//            config.setCodec(new JSONCodec();
            log.info("初始化Redisson单机配置,连接地址:" + address);
        } catch (Exception e) {
            log.error("单机Redisson初始化错误", e);
            e.printStackTrace();
        }
        return config;
    }
}
