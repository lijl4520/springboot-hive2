package com.embraces.hive.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Lijl
 * @ClassName ReqTimeOutConfig
 * @Description TODO
 * @Date 2020/11/11 10:37
 * @Version 1.0
 */

@Configuration
public class ReqTimeOutConfig {

    @Value("${req.time_out}")
    public long time_out;
}
