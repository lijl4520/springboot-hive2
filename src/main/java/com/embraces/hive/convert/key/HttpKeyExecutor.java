//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.embraces.hive.convert.key;

import com.embraces.hive.convert.util.HttpRequester;
import com.embraces.hive.convert.util.HttpRespons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpKeyExecutor implements IKeyExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpKeyExecutor.class);
    private String adapterUrl;

    public HttpKeyExecutor(String adapterUrl) {
        this.adapterUrl = adapterUrl;
    }

    @Override
    public String getHttpKey(String requestTime) {
        HttpRequester request = new HttpRequester();
        String key = "";
        Map<String, String> params = new HashMap();
        params.put("requestTime", requestTime);
        try {
            HttpRespons hr = request.sendGet(this.adapterUrl, params);
            String content = hr.getContent();
            return content;
        } catch (IOException var7) {
            LOGGER.error("connect adapter failed!!");
            LOGGER.error(var7.getMessage(), var7);
            return key;
        }
    }
}
