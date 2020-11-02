//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.embraces.hive.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpKeyExecutor {
    private String adapterUrl;
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpKeyExecutor.class);

    public HttpKeyExecutor(String adapterUrl) {
        this.adapterUrl = adapterUrl;
    }

    public String getHttpKey(String requestTime) {
        HttpRequester request = new HttpRequester();
        String key = "";
        Map<String, String> params = new HashMap();
        params.put("requestTime", requestTime);

        try {
            key = request.send(this.adapterUrl + "?requestTime=" + requestTime + "01000000").split(",")[0];
            return key;
        } catch (IOException var6) {
            LOGGER.error(var6.toString());
            return key;
        }
    }
}
