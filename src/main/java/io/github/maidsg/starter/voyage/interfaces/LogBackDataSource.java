package io.github.maidsg.starter.voyage.interfaces;

import io.github.maidsg.starter.voyage.model.base.LogInfo;
import org.springframework.scheduling.annotation.Async;

/*******************************************************************
 * <pre></pre>
 * @文件名称： LogBackDataSource.java
 * @包 路  径： io.github.maidsg.framelessbootstarter.interfaces
 * @Copyright：wy (C) 2024 *
 * @Description:
 * @Version: V1.0
 * @Author： wy
 * @Date：2024/3/4 17:26
 * @Modify：
 */
public interface LogBackDataSource {

    /**
     * 保存链路日志信息
     * @return
     * @param logInfo
     */
    @Async
    void  save(LogInfo logInfo);


}
